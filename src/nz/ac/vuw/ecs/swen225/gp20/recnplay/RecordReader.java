package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class RecordReader {
    private final ArrayList<Move> moves = new ArrayList<>();
    private static boolean playNextFrame;

    /**
     * This class reads a JSON file from file's path and stores the moves
     * that are read.
     * They are read as Actions > movement > *a move*
     */
    public RecordReader() {
        //check path exists
        //based on https://stackoverflow.com/questions/15571496/how-to-check-if-a-folder-exists
        File replayFile = getFile();
        playNextFrame = false;

        //read path
        try {
            JsonObject jo = new Gson().fromJson(new FileReader(replayFile), JsonObject.class);
            JsonArray jsonMoves = jo.getAsJsonArray("Actions");

//            System.out.println(jsonMoves);
//            System.out.println(jsonMoves.get(0));
//            System.out.println(jsonMoves.get(0).getAsJsonObject().get("movement"));

//            System.out.println(jsonMoves.get(1));

            for (JsonElement jsonMove: jsonMoves){
                String moveType = jsonMove.getAsJsonObject().get("movement").getAsString();
                Move move;
                //switch to move type
                switch (moveType.toLowerCase()){
                    case "left":
                        move = new moveLeft();
                        break;
                    case "right":
                        move = new moveRight();
                        break;
                    case "up":
                        move = new moveUp();
                        break;
                    case "down":
                        move = new moveDown();
                        break;
                    default:
                        //should never happen
                        //todo error
                        throw new NullPointerException("Move is not recognised");
                }
                moves.add(move);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Makes a move for player every amount of seconds specified in param.
     *
     * @param secsToWait amount of time to wait per move
     * @param player making the move
     */
    public void playAtSpeed(double secsToWait, Player player){
        for (Move move: moves){
//            move.apply(new Player(0, 0));
            System.out.println("did move:" + move);
            try {
                Thread.sleep((long) (secsToWait*1000));
            } catch (InterruptedException e) {
                System.out.println("Could not wait");
                e.printStackTrace();
            }
        }
    }

    /**
     * Runs one move per click. Button to be clicked is on GUI.
     *
     * @param player the player doing the move
     */
    public void playPerFrame(Player player){
        for (Move move: moves){
            //wait for signal
            while (!playNextFrame){}

            //do move
//            move.apply(player);
            System.out.println("move");

            //reset playNextFrame
            playNextFrame = false;
        }
    }

    /**
     * Used for playing the next frame in playPerFrame method.
     * This is done by setting playNextFrame to TRUE.
     *
     * @param playNextFrame boolean value that private playPerFrame will be set to.
     */
    public static void setPlayNextFrame(boolean playNextFrame) {
        RecordReader.playNextFrame = playNextFrame;
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

    public static File getFile(){
        JFileChooser fileChooser = new JFileChooser("Recordings/");
        if (fileChooser.showOpenDialog(new JButton("Open")) == JFileChooser.APPROVE_OPTION)
            return fileChooser.getSelectedFile();
        return getFile();
    }
}
