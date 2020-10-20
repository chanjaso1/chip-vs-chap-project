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
        RecordReader rr = new RecordReader(null, null, null, null); //todo fix null null //open playerMoveswithBugsReading.json

        //store what the output of the movements should be
        ArrayList<Move> answer = new ArrayList<>();
        Player player = new Player(0, 0);
//        Bug bug = new Bug();
//        answer.add(new moveLeft(player));
//        answer.add(new moveRight(bug));
//        answer.add(new moveDown(player));
//        answer.add(new moveUp(bug));

        //compare output with what it should be
        if (!(rr.getMoves().equals(answer)))
            fail("ArrayList \"" + rr.getMoves() + "\" should be \"" + answer + "\"");

        //manually check time
        rr.playAtSpeed(2.5);

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
        Player player = new Player(0, 0);
//        Bug bug = new Bug();
//        answerMoves.add(new moveUp(player)); //different order from reading testing
//        answerMoves.add(new moveDown(bug));
//        answerMoves.add(new moveLeft(player));
//        answerMoves.add(new moveRight(bug));

        //save
        RecordSaver rs = new RecordSaver(answerMoves, 1); //save to playerMoveswithBugsReading.json - yes, override every time
        rs.save("testRecordingMovementsOutput");

        //compare moves from RecordReader with output file
        RecordReader outputReader = new RecordReader(null, null, null, null); //todo fix null null//open playerMoveswithBugsReading.json
        //check file exists
        if (!outputReader.getMoves().equals(answerMoves))
            fail("ArrayList \"" + outputReader.getMoves() + "\" should be \"" + answerMoves + "\"");

    }
}
