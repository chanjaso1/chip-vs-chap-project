package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.maze.Move;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class RecordSaver {
    private final ArrayList<Move> moves;
    private final double time;
    private boolean notForUser = false;

    public RecordSaver(ArrayList<Move> moves, double time, boolean notForUser) {
        this.notForUser = notForUser;
        this.moves = moves;
        this.time = time;
        save("lastgame");
    }

    public RecordSaver(ArrayList<Move> moves, double time) {
        this.moves = moves;
        this.time = time;
        save(null);
    }

    public void save(String fileName){
        //get name of new file if not provided
        if (fileName == null || fileName.isBlank() || fileName.isEmpty()){
            fileName = getFileName("Please enter a name for the recording file (without extension):");
            if (fileName == null) return; //cancel
        }
        fileName += ".json";
        StringBuilder jsonRecording = new StringBuilder();

        //make text to store in json format
        jsonRecording.append("{\n\t\"Header\": {\n\t\t\"time\": ").append(time).append("\n\t},");
        jsonRecording.append("\n\t\"Actions\": [\n");
        for (int i = 0; i < moves.size(); i++) {
            jsonRecording.append("\t\t{\n");

            //player or bug
            jsonRecording.append("\t\t\t\"").append(moves.get(i).getMover()).append("\": \"").append(moves.get(i)).append("\"\n");

            jsonRecording.append(i < moves.size()-1 ? "\t\t},\n":"\t\t}\n");
        }
        jsonRecording.append("\t]\n}");

        //save to file

        File savingFile = new File("Recordings/"+(notForUser ? "UserData/":"")+fileName);
        if (savingFile.exists() && !notForUser){
            //check if file exists and ask user if they want
            //to override existing file based on https://stackoverflow.com/questions/1816673/how-do-i-check-if-a-file-exists-in-java
            int wantTo = JOptionPane.showConfirmDialog(null, "Are you sure you want to override "+fileName+"?");
            if (wantTo != JOptionPane.YES_OPTION){
                return;
            }
        }
        try {
            //write contents to file or make new file
            // - based on https://stackoverflow.com/questions/52581404/java-create-a-new-file-or-override-the-existing-file
            BufferedWriter newFile = new BufferedWriter(new FileWriter(savingFile));
            newFile.write(jsonRecording.toString());
            newFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if (notForUser)
            JOptionPane.showMessageDialog(null, "Your progress has been saved. Next time you open the game, you will start from this point.");
        else
            JOptionPane.showMessageDialog(null, "File saved to Recordings folder.");
    }

    public static String getFileName(String message){
        String fileName = "";
        while (fileName.isEmpty() || fileName.isBlank()){
            try {
                fileName = JOptionPane.showInputDialog(message); //todo error checking
            } catch (NullPointerException ignored){
                JOptionPane.showMessageDialog(null, "File save cancelled.");
                return null;
            }

            message = "Filename is empty. Please enter a valid filename.";
        }

        return fileName;
    }

    public double getTime() {
        return time;
    }
}
