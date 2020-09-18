package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.fail;

public class RecordnReplayTesting {
    /**
     * Testing that recording and reading replays works
     * as it should.
     */

    /**
     * Test reading
     */
    //test it can read a correctly formatted file
    @Test
    public void testReadingMovements(){
        //make RecordReader with manually generated file
        RecordReader rr = new RecordReader("Recordings/test1.json");

        //store what the output of the movements should be
        ArrayList<Move> answer = new ArrayList<>();
        answer.add(new moveLeft());
        answer.add(new moveRight());
        answer.add(new moveDown());
        answer.add(new moveUp());

        //compare output with what it should be
        if (!(rr.getMoves().equals(answer)))
            fail("ArrayList \"" + rr.getMoves() + "\" should be \"" + answer + "\"");

        /*
        https://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file
        String content = "";
        try {
            content = Files.readString(Paths.get("Recordings/test1.json"), StandardCharsets.US_ASCII);
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }

    /**
     * Test recording
     */
    //test it can record to a file that is formatted correctly
    @Test
    public void testRecordingMovements(){
        //get arraylist of movements
        //check what should be printed (from RecordReader output)
        //compare output with output of RecordReader
    }
}
