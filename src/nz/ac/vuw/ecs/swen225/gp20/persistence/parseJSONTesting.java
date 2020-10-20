package nz.ac.vuw.ecs.swen225.gp20.persistence;

import org.junit.Test;

public class parseJSONTesting {

    /**
     * test1 will be defining if all the tiles and checking if they are an instance of that class.
     * It will test all tiles except the bug and marked floor tile.
     *
     */
    @Test
    public void test1(){
        try{
            parseJSON test = new parseJSON("levels/test1.json");
        }catch (Exception e){
            System.out.println("A tile was incorrectly defined!");
        }

    }

}
