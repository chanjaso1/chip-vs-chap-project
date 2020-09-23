package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;
import org.junit.Test;

import java.io.FileNotFoundException;
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
    public void testCanReadMovements(){
        //make RecordReader with manually generated file
        RecordReader rr = null;
        try {
            rr = new RecordReader("Recordings/testCanReadMovements.json");
        } catch (FileNotFoundException e) {
            fail("File not found, but should have been.");
        }

        //store what the output of the movements should be
        ArrayList<Move> answer = new ArrayList<>();
        answer.add(new moveLeft());
        answer.add(new moveRight());
        answer.add(new moveDown());
        answer.add(new moveUp());

        //compare output with what it should be
        if (!(rr.getMoves().equals(answer)))
            fail("ArrayList \"" + rr.getMoves() + "\" should be \"" + answer + "\"");

        //manually check time
        rr.playAtSpeed(2.5, new Player(0, 0));

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
    //test it can record by saving to a file that is formatted correctly
    //NOTE: IF TESTED ONCE, AND TEST IS RUN AGAIN, CLICK YES FOR OVERWRITING THE OUTPUT FILE
    @Test
    public void testRecordingMovementsOutput(){
        //get arraylist of movements and put into RecordSaver
        ArrayList<Move> answerMoves = new ArrayList<>();
        answerMoves.add(new moveUp()); //different order from reading testing
        answerMoves.add(new moveDown());
        answerMoves.add(new moveLeft());
        answerMoves.add(new moveRight());

        //save
        RecordSaver rs = new RecordSaver(answerMoves);
        rs.save("testRecordingMovementsOutput");

        //compare moves from RecordReader with output file
        try {
            RecordReader outputReader = new RecordReader("Recordings/testRecordingMovementsOutput.json");
            if (!outputReader.getMoves().equals(answerMoves))
                fail("ArrayList \"" + outputReader.getMoves() + "\" should be \"" + answerMoves + "\"");
        } catch (FileNotFoundException e) {
            fail("File not found, but should have been.");
        }
    }
}
