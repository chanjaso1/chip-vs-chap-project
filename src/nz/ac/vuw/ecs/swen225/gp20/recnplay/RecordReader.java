package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nz.ac.vuw.ecs.swen225.gp20.application.GUI;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Bug;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class RecordReader {
    private final ArrayList<Move> moves = new ArrayList<>();
    private int lastMovePos;
    private double time;
    private final GUI gui;
    private Timer timer = null;
    private final File replayFile;

    /**
     * This class reads a JSON file from file's path and stores the moves
     * that are read.
     * They are read as Actions > movement > *a move*
     *
     * @param gui - the only gui being used that gives access to game
     * @param file - the file that is being read
     * @param player - the current player of the current game
     * @param bugObj - bug in Object format as it's not possible to use it fully in Bug format
     */
    public RecordReader(GUI gui, File file, Player player, Object bugObj) {
        System.out.println("RecordReader");
        Actor bug = (Actor)bugObj;
        this.gui = gui;
        //based on https://stackoverflow.com/questions/15571496/how-to-check-if-a-folder-exists
        replayFile = file;

        //read path
        try {
            assert replayFile != null;
            JsonObject jo = new Gson().fromJson(new FileReader(replayFile), JsonObject.class);
            time = jo.get("Header").getAsJsonObject().get("time").getAsDouble();

            JsonArray jsonMoves = jo.getAsJsonArray("Actions");

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
                            move = new moveLeft(player);
                            break;
                        case "right":
                            move = new moveRight(player);
                            break;
                        case "up":
                            move = new moveUp(player);
                            break;
                        case "down":
                            move = new moveDown(player);
                            break;
                        default:
                            //not recognised
                            throw new NullPointerException("Move is not recognised");
                    }
                }
                //is a Bug move
                else {
                    typeMove = jsonMove.getAsJsonObject().get("B").getAsString();
                    switch (typeMove.toLowerCase()){
                        case "left":
                            move = new moveLeft(new Bug(0, 0));
                            break;
                        case "right":
                            move = new moveRight(new Bug(0, 0));
                            break;
                        case "up":
                            move = new moveUp(new Bug(0, 0));
                            break;
                        case "down":
                            move = new moveDown(new Bug(0, 0));
                            break;
                        default:
                            //not recognised
                            throw new NullPointerException("Move is not recognised");
                    }
                }

                //add move - made by whoever - to moves list to preserve order
                moves.add(move);
            }
        } catch (Exception e) {
            e.printStackTrace();
            GUI.notifyError("Unsupported file format.");
            return;
        }

        //if there aren't any moves in a regular replay
        if (moves.isEmpty() && !replayFile.getName().equals("lastgame.json"))
            //let user know file is empty
            //based on https://stackoverflow.com/questions/7993000/need-to-use-joptionpane-error-message-type-of-jdialog-in-a-jframe
            JOptionPane.showMessageDialog(null, "Please try again and select a different file. ", "Your file was empty", JOptionPane.ERROR_MESSAGE);

        //start on move 0
        lastMovePos = 0;
    }

    /**
     * Makes a move for player every amount of seconds specified in param.
     *
     * @param secsToWait amount of time to wait per move
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

        //don't play past last replay
        if (moves.size() == lastMovePos){
            //don't notify if playing starting game replay
            if (!replayFile.getName().equals("lastgame.json"))
                JOptionPane.showMessageDialog(null, "That was the last move!");

            //step-by-step dont stop timer that doesnt exist
            if (timer != null)
                timer.stop();

            //gui display the "help" popup on InfoTile after starting game done
            gui.setDisplayInfoTile(true);
            return;
        }

        //do move
        if (moves.get(lastMovePos).getMover().getClass() == Player.class){
            gui.movePlayer(moves.get(lastMovePos));
        }
        //update bug
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

        lastMovePos++;
    }

    /**
     * New levels have new Bugs and Players- update moves with new Player/Bug.
     */
    public void updateMovesForActors(){
        //update all the moves
        for (Move move: moves){
            //update the player
            if (move.getMover().getClass() == Player.class){
                System.out.println("setting player:" + gui.getPlayer());
                move.setMover(gui.getPlayer());
            }
            //update bug
            else{
                move.setMover((Actor) gui.getGame().getBug());
                System.out.println("setting bug:"+((Actor) gui.getGame().getBug()));
            }
        }
    }

    public StringBuilder getMovesAsString(){
        StringBuilder movesString = new StringBuilder();
        for (Move m: moves)
            movesString.append(m).append(",");
        return movesString;
    }

    /**
     * @return the moves read from the file initialised with
     */
    public ArrayList<Move> getMoves() {
        return moves;
    }

    /**
     * @return the time the moves end at
     */
    public double getTime() {
        return time;
    }
}
