package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.application.GUI;
import nz.ac.vuw.ecs.swen225.gp20.maze.Move;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class RecordSaver {
    public static final String EMPTY_RECORDING = "{\n" +
            "\t\"Header\": {\n" +
            "\t\t\"time\": 60.0\n" +
            "\t},\n" +
            "\t\"Level1\": [\n" +
            "\t]\n" +
            "}"; //for an empty recording - used in GUI todo update
    private final ArrayList[] moves;
    private final double time;
    private final boolean notForUser; //if file is not directly used by user

    /**
     * RecordSaver saves the moves in "Action" in JSON file and the time in "Header".
     * The boolean constructor is used for the programmers to decide if the saved file
     * is for back end or for the user.
     *
     * @param moves - to be saved.
     * @param time - time ended on after moves were made.
     * @param notForUser - true for secret file.
     */
    public RecordSaver(ArrayList[] moves, double time, boolean notForUser) {
        this.notForUser = notForUser;
        this.moves = moves;
        this.time = time;

        //if file save does not concern user
        if (notForUser)
            save("lastgame");
        else
            save(null);
    }

    /**
     * Save to Recording or Recording/UserData file using fileName.
     *
     * @param fileName - name of file to be saved.
     */
    public void save(String fileName){
        //get name of new file if not provided
        if (fileName == null || fileName.isBlank() || fileName.isEmpty()){
            fileName = GUI.getFileName("Please enter a name for the recording file (without extension):");
            if (fileName == null) return; //cancel
        }
        fileName += ".json";
        StringBuilder jsonRecording = new StringBuilder();

        //make text to store in json format
        jsonRecording.append("{\n\t\"Header\": {\n\t\t\"time\": ").append(time).append("\n\t},");

        //store each level
        for (int i = 1; i < moves.length; i++) {
            System.out.println("level " + i + " had " + moves[i].size() + " moves");
            jsonRecording.append("\n\t\"Level").append(i).append("\": [\n");
            for (int j = 0; j < moves[i].size(); j++) {
                jsonRecording.append("\t\t{\n");
                //player or bug
                jsonRecording.append("\t\t\t\"").append(((Move)moves[i].get(j)).getMover()).append("\": \"").append(moves[i].get(j)).append("\"\n");
                jsonRecording.append(j < moves[i].size()-1 ? "\t\t},\n":"\t\t}\n");
            }
            jsonRecording.append(i < moves.length-1 ?"\t],":"");
        }
        jsonRecording.append("\t]\n}");

        //save to file
        boolean saved = true;
        File savingFile = new File("Recordings/"+(notForUser ? "UserData/":"")+fileName);
        if (savingFile.exists() && !notForUser){
            //check if file exists and ask user if they want
            //to override existing file based on https://stackoverflow.com/questions/1816673/how-do-i-check-if-a-file-exists-in-java
            int wantTo = GUI.inputDialogue("Are you sure you want to override "+fileName+"?");
            if (wantTo != JOptionPane.YES_OPTION) return;
        }
        try {
            //write contents to file or make new file
            // - based on https://stackoverflow.com/questions/52581404/java-create-a-new-file-or-override-the-existing-file
            BufferedWriter newFile = new BufferedWriter(new FileWriter(savingFile));
            newFile.write(jsonRecording.toString());
            newFile.close();
        } catch (Exception e) {
            saved = false;
            GUI.notifyError("Could not save file due to an error.");
        }

        //say it's saved in the according fashion
        if (saved){
            if (notForUser)
                //user did not select the name
                GUI.showMessage( "Your progress has been saved. Next time you open the game, you will start from this point.");
            else
                //user selected name in recordings folder
                GUI.showMessage("File saved to Recordings folder.");
        }
    }

    /**
     * @return the time that the moves ended up at.
     */
    public double getTime() {
        return time;
    }
}
