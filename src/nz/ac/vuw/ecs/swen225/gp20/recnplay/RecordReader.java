package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nz.ac.vuw.ecs.swen225.gp20.application.GUI;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RecordReader {
    private final ArrayList<Move> moves = new ArrayList<>();
    private int lastMovePos;
    private GUI gui;
    private Timer timer = null;

    /**
     * This class reads a JSON file from file's path and stores the moves
     * that are read.
     * They are read as Actions > movement > *a move*
     */
    public RecordReader(GUI gui, Player player, Bug bug) {
        this.gui = gui;
        //based on https://stackoverflow.com/questions/15571496/how-to-check-if-a-folder-exists
        File replayFile = GUI.getFile();

        //read path
        try {
            JsonObject jo = new Gson().fromJson(new FileReader(replayFile), JsonObject.class);
//            jo.get("Header").get

            JsonArray jsonMoves = jo.getAsJsonArray("Actions");

//            System.out.println(jsonMoves);
//            System.out.println(jsonMoves.get(0));
//            System.out.println(jsonMoves.get(0).getAsJsonObject().get("movement"));

//            System.out.println(jsonMoves.get(1));

            for (JsonElement jsonMove: jsonMoves){
                boolean isPlayerMove;
                isPlayerMove = jsonMove.getAsJsonObject().has("P");
//                moveType = jsonMove.getAsJsonObject().get("movement").getAsString();

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

        if (moves.isEmpty())
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
        timer = new Timer((int) secsToWait * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playPerFrame();
            }
        });

        timer.start();

//        for (Move move: moves){
//
//            gui.movePlayer(move);
//            System.out.println("did move:" + move);
//
//            long time = System.currentTimeMillis() + (long)(secsToWait*1000);
//            while (System.currentTimeMillis() < time){
////                System.out.println("waiting");
//                gui.getBoard().repaint();
//                gui.getBoard().revalidate();
//                System.out.flush();
//            }
//        }
    }

    /**
     * Runs one move per click. Button to be clicked is on GUI.
     */
    public void playPerFrame(){
        //do move
        gui.movePlayer(moves.get(lastMovePos));
//        System.out.println("move: "+moves.get(lastMovePos++));
        lastMovePos++;

        //last move
        if (moves.size() == lastMovePos){
            JOptionPane.showMessageDialog(null, "That was the last move, starting over!");
            lastMovePos = 0;
            timer.stop();
        }


        // todo need to call move.apply() but it needs a player
        //  and this method is called from gui which does not have
        //  a player object
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

    public static void main(String[] args) {
//        RecordReader rr = new RecordReader();
//        for (Move move : rr.moves) {
//            System.out.println(move.getMover() + " moved " + move); //testing
//        }
    }
}
