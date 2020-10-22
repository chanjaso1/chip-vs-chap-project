package nz.ac.vuw.ecs.swen225.gp20.persistence;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * This class will test every method in the parseJSON class and indirectly, the bug class.
 */
public class ParseJSONTesting {

    /**
     * test1 will be defining if all the tiles and checking if they are an instance of that class.
     * It will test all tiles except the bug and marked floor tile.
     */
    @Test
    public void checkLevel1Tiles() {
        new ParseJSON("tests/test1.json");

    }

    /**
     * Check that JSONparseer can parse JAR files and return fields for the bug, and the aClass field.
     */
    @Test
    public void checkLevel2Tiles() {
        new ParseJSON("tests/test2.json");
    }

    /**
     * Check that the player is correctly parsed. The test will fail if it is still null.
     */
    @Test
    public void checkPlayerGetter() {
        ParseJSON test = new ParseJSON("tests/test1.json");
        assert test.getPlayer() != null;
    }

    /**
     * Check that the treasure chips are correctly parsed. The test will fail if it is still 0.
     */
    @Test
    public void checkTreasureGetter() {
        ParseJSON test = new ParseJSON("tests/test1.json");
        assert test.getTreasures() != 0;
    }

    /**
     * Check that the map is correctly parsed. The test will fail if it is still null.
     */
    @Test
    public void checkMapGetter() {
        ParseJSON test = new ParseJSON("tests/test1.json");
        assert test.getMap() != null;
    }

    /**
     * Check that the keys are correctly parsed. The test will fail if it is still 0.
     */
    @Test
    public void checkNumberOfKeys() {
        ParseJSON test = new ParseJSON("tests/test1.json");
        assert test.getNumberOfKeys() != 0;
    }

    /**
     * Check that the keys are correctly parsed. The test will fail if it is still 0.
     */
    @Test
    public void checkBug() {
        ParseJSON test = new ParseJSON("tests/test2.json");
        assert test.getBug() != null;
        test = new ParseJSON("tests/test1.json");
        assert test.getBug() == null;
    }

    /**
     * Check that the parser failed
     */
    @Test
    public void checkFailedParser() {
        ParseJSON test = new ParseJSON("tests/test-1.json");
        assert test.getPlayer() == null && test.getBug() == null && test.getMap() == null;
    }

    /**
     * Check that the image is not null when there is an image.
     */
    @Test
    public void testLoadImage() {
        ParseJSON test = new ParseJSON("tests/test2.json");
        assert test.loadImage("swarm.gif") != null;
    }

    /**
     *
     */
    @Test
    public void checkBugBoolean() {
        try {
            ParseJSON test = new ParseJSON("tests/test2.json");
            assert test.aClass.getField("moveDownFirst").equals(false);
        } catch (NoSuchFieldException e) {
        }
    }

    /**
     *
     */
    @Test
    public void checkBugToString() {
        ParseJSON test = new ParseJSON("tests/test2.json");
        try {
            assert test.aClass.getMethod("toString").invoke(test.getBug()) == "B";
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.out.println("Calling toString() on the bug");
        }
    }


}
