package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class RecordReader {
    private ArrayList<Move> moves = new ArrayList<>();

    /**
     * This class reads a JSON file.
     */
    public RecordReader(String filePath) {
        try {
            JsonObject jo = new Gson().fromJson(new FileReader(filePath), JsonObject.class);
            JsonArray jsonMoves = jo.getAsJsonArray("Actions");

//            System.out.println(jsonMoves);
//            System.out.println(jsonMoves.get(0));
//            System.out.println(jsonMoves.get(0).getAsJsonObject().get("movement"));

//            System.out.println(jsonMoves.get(1));

            for (JsonElement jsonMove: jsonMoves){
                String moveType = jsonMove.getAsJsonObject().get("movement").getAsString();
                Move move;
                //switch to move type
                switch (moveType){
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
                        move = null;
                        break;
                }
                moves.add(move);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        play();
//        new RecordSaver(moves); //testing
    }

    public void play(){
        for (Move move: moves){
//            move.apply();
//            System.out.println(move);
        }
    }

    /*
    public StringBuilder getMovesAsString(){
        StringBuilder movesString = new StringBuilder();
        for (Move m: moves)
            movesString.append(m).append(",");
        return movesString;
    }
     */

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public static void main(String[] args) {
//        JFileChooser jfc = new JFileChooser(); //based off https://stackoverflow.com/questions/8402889/working-with-jfilechooser-getting-access-to-the-selected-file
//        int returnValue = jfc.showOpenDialog(new JFrame("Select a file"));
//
//        if (returnValue == JFileChooser.APPROVE_OPTION){
//            new RecordReader(jfc.getSelectedFile());
//        }File recordFileSystem.out.println("File:"+recordFile.getName());

        new RecordReader("Recordings/testRecording.json");
    }
}
