package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.*;
import nz.ac.vuw.ecs.swen225.gp20.application.GUI;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Bug;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class RecordReader {
    private final ArrayList[] moves = new ArrayList[3];
    private int lastMovePos, //move counter that ends at last move
    currentLevel; //level currently being played
    private double time; //time that replay ends at
    private final GUI gui;
    private Timer timer = null; //timer for auto replay
    private final File replayFile;

    /**
     * This class reads a JSON file from file's path and stores the moves
     * that are read.
     * They are read as Actions > movement > *a move*
     *
     * @param gui - the only gui being used that gives access to game
     * @param file - the file that is being read
     * @param player - the current player of the current game
     */
    public RecordReader(GUI gui, File file, Player player) {
        this.gui = gui;
        //based on https://stackoverflow.com/questions/15571496/how-to-check-if-a-folder-exists
        replayFile = file;
        moves[1] = new ArrayList<Move>();
        moves[2] = new ArrayList<Move>();
        currentLevel = 1;

        //read path
        try {
            assert replayFile != null;
            JsonObject jo = new Gson().fromJson(new FileReader(replayFile), JsonObject.class);
            time = jo.get("Header").getAsJsonObject().get("time").getAsDouble();

            for (int level = 1; level < 3; level++) {
                if (!jo.has("Level"+level))
                    continue;

                JsonArray jsonMoves = jo.getAsJsonArray("Level"+level);

                //for every move in actions
                for (JsonElement jsonMove: jsonMoves){
                    boolean isPlayerMove = jsonMove.getAsJsonObject().has("P"); //is player or bug
                    Move move = null; //move to be added

                    //switch to move type
                    String typeMove;

                    //is a Player move
                    if (isPlayerMove){
                        typeMove = jsonMove.getAsJsonObject().get("P").getAsString();
                        switch (typeMove.toLowerCase()){
                            case "left":
                                move = new MoveLeft(player);
                                break;
                            case "right":
                                move = new MoveRight(player);
                                break;
                            case "up":
                                move = new MoveUp(player);
                                break;
                            case "down":
                                move = new MoveDown(player);
                                break;
                            default:
                                //not recognised
                                throw new NullPointerException("Move is not recognised");
                        }
                    }
                    //is a Bug move
                    else {
                        typeMove = jsonMove.getAsJsonObject().get("B").getAsString();
                        Bug dummyBug = new Bug(0, 0); //moves don't allow null actors
                        switch (typeMove.toLowerCase()){
                            case "left":
                                move = new MoveLeft(dummyBug);
                                break;
                            case "right":
                                move = new MoveRight(dummyBug);
                                break;
                            case "up":
                                move = new MoveUp(dummyBug);
                                break;
                            case "down":
                                move = new MoveDown(dummyBug);
                                break;
                            default:
                                //not recognised
                                throw new NullPointerException("Move is not recognised");
                        }
                    }

                    //add move - made by whoever - to moves list to preserve order
                    moves[level].add(move);
                }
            }
        } catch (Exception e) {
            GUI.notifyError("Unsupported file format.");
            return;
        }

        //start on move 0
        lastMovePos = 0;

        //if there aren't any moves in a regular replay
        if (moves[1].isEmpty() && moves[2].isEmpty() && !replayFile.getName().equals("lastgame.json")){
            //let user know file is empty
            //based on https://stackoverflow.com/questions/7993000/need-to-use-joptionpane-error-message-type-of-jdialog-in-a-jframe
            GUI.notifyError("Your file was empty. Please try again and select a different file.");
            return;
        }


        //do level 1 moves if there are any, otherwise, level 2
        if (moves[1].isEmpty())
            currentLevel = 2;
    }

    /**
     * Makes a move for player every amount of seconds specified in param.
     *
     * @param secsToWait - amount of time to wait per move
     */
    public void playAtSpeed(double secsToWait){
        //execute playNextFrame every secsToWait
        timer = new Timer((int) (secsToWait * 1000), e -> playNextFrame());
        timer.start();
    }

    /**
     * Runs one move per click. Button to be clicked is on GUI.
     */
    public void playNextFrame(){
        gui.setDisplayInfoTile(false); //don't display the popups during replay

        //play
        //do Player move
        if (lastMovePos < moves[currentLevel].size()){
            //do Player move
            if (((Move)moves[currentLevel].get(lastMovePos)).getMover().getClass() == Player.class){
                gui.movePlayer((Move)moves[currentLevel].get(lastMovePos));
            }
            //do Bug move
            else{
                //play using reflection
                try {
                    gui.getGame().getParser().aClass.getMethod("moveBugSequence").invoke(gui.getGame().getBug());
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                gui.getGame().updatePlayerBugStatus();
                gui.getBoard().moveBug();
            }

            //increment
            lastMovePos++;
        }
        else {
            //don't notify if playing starting game replay
            if (!replayFile.getName().equals("lastgame.json")){
                GUI.showMessage("That was the last move!");
            }

            //step-by-step dont stop timer that doesnt exist
            if (timer != null)
                timer.stop();

            //gui display the "help" popup on InfoTile after starting game done
            gui.setDisplayInfoTile(true);
        }
    }

    /**
     * New levels have new Bugs and Players - update moves with new Player/Bug.
     */
    public void updateMovesForActors(int level){
        currentLevel = level;
        lastMovePos = 0;

        //update all the moves
        for (Object obj: moves[level]){
            Move move = (Move) obj;
            //update the player
            if (move.getMover().getClass() == Player.class)
                move.setMover(gui.getPlayer());
            //update bug
            else
                move.setMover((Actor) gui.getGame().getBug());
        }
    }

    /**
     * @return the moves that this RecordReader has read.
     */
    public ArrayList[] getMoves() {
        return moves;
    }

    /**
     * @return the time the moves end at.
     */
    public double getTime() {
        return time;
    }

    /**
     * Initialised as 1, then if level 1 has no moves, is changed to two
     *
     * @return the level that the record is playing
     */
    public int getCurrentLevel() {
        return currentLevel;
    }
}
