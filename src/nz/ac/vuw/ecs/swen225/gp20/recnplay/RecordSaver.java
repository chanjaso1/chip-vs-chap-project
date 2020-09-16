package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.maze.Move;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RecordSaver {
    private ArrayList<Move> moves;

    public RecordSaver(ArrayList<Move> moves) {
        this.moves = moves;
        save();//todo remove
    }

    public void save(){
        System.out.println("Save:");
        //todo do we want this here
        //get name of new file
        StringBuilder fileName = new StringBuilder(JOptionPane.showInputDialog("Name of file (without extension):")); //todo error checking
        fileName.append(".json");
        StringBuilder jsonRecording = new StringBuilder();

        //make text to store in json format
        jsonRecording.append("{\n\t\"Actions\": [\n");
        for (int i = 0; i < moves.size(); i++) {
            jsonRecording.append("\t\t{\n");
            jsonRecording.append("\t\t\t\"movement\": \"" + moves.get(i) + "\"\n");
            jsonRecording.append(i < moves.size()-1 ? "\t\t},\n":"\t\t}\n");
        }
        jsonRecording.append("\t]\n}");


        //testing
//        System.out.println(fileName + ":");
//        System.out.println(jsonRecording);

        //save to file
        try {
            //write contents based on https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
            PrintWriter newFile = new PrintWriter("Recordings/"+fileName);
            newFile.print(jsonRecording);

            if (!newFile.checkError()) {
//                System.out.println("File created. "); //todo remove
                //file successfully created
                newFile.close();
            } else {
//                System.out.println("File already exists.");
                //file save error, try again
                newFile.close();
                save(); //start over
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "File saved to Recordings folder.");
    }
}
