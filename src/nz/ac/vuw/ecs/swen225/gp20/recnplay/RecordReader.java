package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nz.ac.vuw.ecs.swen225.gp20.application.GUI;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
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
     */
    public RecordReader(GUI gui, File file, Player player, Object bugObj) {
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

//            System.out.println(jsonMoves);
//            System.out.println(jsonMoves.get(0));
//            System.out.println(jsonMoves.get(0).getAsJsonObject().get("movement"));

//            System.out.println(jsonMoves.get(1));

            for (JsonElement jsonMove: jsonMoves){
                boolean isPlayerMove;
                isPlayerMove = jsonMove.getAsJsonObject().has("P");

                Move move;
                //switch to move type
                String typeMove;

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
                            //should never happen
                            //todo error
                            throw new NullPointerException("Move is not recognised");
                    }
                }
                else {
                    typeMove = jsonMove.getAsJsonObject().get("B").getAsString();
                    switch (typeMove.toLowerCase()){
                        case "left":
                            move = new moveLeft(bug);
                            break;
                        case "right":
                            move = new moveRight(bug);
                            break;
                        case "up":
                            move = new moveUp(bug);
                            break;
                        case "down":
                            move = new moveDown(bug);
                            break;
                        default:
                            //should never happen
                            //todo error
                            throw new NullPointerException("Move is not recognised");
                    }
                }

                //add move - made by whoever - to moves list to preserve order
                moves.add(move);
            }
        } catch (Exception e) {
            GUI.notifyError("Unsupported file format.");
            System.out.println(e.toString()); //todo remove?
            return;
        }

        if (moves.isEmpty() && !replayFile.getName().equals("lastgame.json"))
            //let user know file is empty
            //based on https://stackoverflow.com/questions/7993000/need-to-use-joptionpane-error-message-type-of-jdialog-in-a-jframe
            JOptionPane.showMessageDialog(null, "Please try again and select a different file. ", "Your file was empty", JOptionPane.ERROR_MESSAGE);

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
        gui.setDisplayInfoTile(false);
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
        gui.movePlayer(moves.get(lastMovePos));
        lastMovePos++;
    }

    /**
     * New level, new Player - update moves with new Player.
     */
    public void updateMovesForActors(){
        for (Move move: moves)
            move.setMover(gui.getPlayer());
    }

    /*
    public StringBuilder getMovesAsString(){
        StringBuilder movesString = new StringBuilder();
        for (Move m: moves)
            movesString.append(m).append(",");
        return movesString;
    }
     */

    /**
     * @return the moves read from the file initialised with
     */
    public ArrayList<Move> getMoves() {
        return moves;
    }

    public double getTime() {
        return time;
    }
}
