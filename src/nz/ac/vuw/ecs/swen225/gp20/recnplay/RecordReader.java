package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;

public class RecordReader {
    /**
     * This interface is for players' moves.
     */
    public RecordReader(File recordFile) {
        System.out.println("File:"+recordFile.getName());
    }

    public static void main(String[] args) {
//        JFileChooser jfc = new JFileChooser(); //based off https://stackoverflow.com/questions/8402889/working-with-jfilechooser-getting-access-to-the-selected-file
//        int returnValue = jfc.showOpenDialog(new JFrame("Select a file"));
//
//        if (returnValue == JFileChooser.APPROVE_OPTION){
//            new RecordReader(jfc.getSelectedFile());
//        }

        try {
            JsonObject jo = new Gson().fromJson(new FileReader("Recordings/testRecording.json"), JsonObject.class);
            JsonArray moves = jo.getAsJsonArray("Actions");

            System.out.println(moves);
            System.out.println(moves.get(0));
            System.out.println(moves.get(0).getAsJsonObject().get("movement"));
            
            System.out.println(moves.get(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
