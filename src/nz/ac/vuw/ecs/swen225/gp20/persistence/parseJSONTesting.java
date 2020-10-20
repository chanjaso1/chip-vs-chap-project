package nz.ac.vuw.ecs.swen225.gp20.persistence;

import org.junit.Test;

public class parseJSONTesting {

    /**
     * test1 will be defining if all the tiles and checking if they are an instance of that class.
     * It will test all tiles except the bug and marked floor tile.
     */
    @Test
    public void checkLevel1Tiles() {
        parseJSON test = new parseJSON("tests/test1.json");

    }

    /**
     * Check that JSONparseer can parse JAR files and return fields for the bug, and the aClass field.
     */
    @Test
    public void checkLevel2Tiles() {
        parseJSON test = new parseJSON("tests/test2.json");
    }

    /**
     * Check that the player is correctly parsed. The test will fail if it is still null.
     */
    @Test
    public void checkPlayerGetter() {

        parseJSON test = new parseJSON("tests/test1.json");
        assert test.getPlayer() != null;
    }

    /**
     * Check that the treasure chips are correctly parsed. The test will fail if it is still 0.
     */
    @Test
    public void checkTreasureGetter() {
        parseJSON test = new parseJSON("tests/test1.json");
        assert test.getTreasures() != 0;
    }

    /**
     * Check that the map is correctly parsed. The test will fail if it is still null.
     */
    @Test
    public void checkMapGetter() {
        parseJSON test = new parseJSON("tests/test1.json");
        assert test.getMap() != null;
    }

    /**
     * Check that the keys are correctly parsed. The test will fail if it is still 0.
     */
    @Test
    public void checkNumberOfKeys() {
        parseJSON test = new parseJSON("tests/test1.json");
        assert test.getNumberOfKeys() != 0;
    }

    /**
     * Check that the keys are correctly parsed. The test will fail if it is still 0.
     */
    @Test
    public void checkBug() {
        parseJSON test = new parseJSON("tests/test2.json");
        assert test.getBug() != null;
         test = new parseJSON("tests/test1.json");
        assert test.getBug() == null;
    }



}
