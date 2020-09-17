package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nz.ac.vuw.ecs.swen225.gp20.maze.Move;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class RecordReader {
    private ArrayList<Move> moves = new ArrayList<>();

    /**
     * This class reads a JSON file.
     */
    public RecordReader() {
        try {
            JsonObject jo = new Gson().fromJson(new FileReader("Recordings/testRecording.json"), JsonObject.class);
            JsonArray jsonMoves = jo.getAsJsonArray("Actions");

//            System.out.println(jsonMoves);
//            System.out.println(jsonMoves.get(0));
//            System.out.println(jsonMoves.get(0).getAsJsonObject().get("movement"));

//            System.out.println(jsonMoves.get(1));
 
            for (JsonElement jsonMove: jsonMoves){
                //todo switch to whatever movetype
                moves.add(new Move(jsonMove.getAsJsonObject().get("movement").getAsString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        play();
        new RecordSaver(moves);
    }

    public void play(){
        for (Move move: moves){
            move.apply();
//            System.out.println(move);
        }
    }

    public static void main(String[] args) {
//        JFileChooser jfc = new JFileChooser(); //based off https://stackoverflow.com/questions/8402889/working-with-jfilechooser-getting-access-to-the-selected-file
//        int returnValue = jfc.showOpenDialog(new JFrame("Select a file"));
//
//        if (returnValue == JFileChooser.APPROVE_OPTION){
//            new RecordReader(jfc.getSelectedFile());
//        }File recordFileSystem.out.println("File:"+recordFile.getName());
        new RecordReader();
    }
}
