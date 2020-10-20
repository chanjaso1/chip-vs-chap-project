package nz.ac.vuw.ecs.swen225.gp20.maze;




import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class is the test class that test things in Maze class.
 */
public class MazeTesting {

    @Test
    public void testPickingUpKey(){
        Game game = new Game(1);
        Player player = game.getPlayer();

        //picking up the keys
        new moveDown(player).apply();
        new moveLeft(player).apply();

        //checked that the key is picked up
        assertEquals(1,player.getKeys().size());

        //checked that the player is able to move pass the door
        doorTile door = null;
        for(int i = 0; i < 7; i++) {
            new moveRight(player).apply();
            if(game.getMap()[player.getRow()][player.getCol()] instanceof doorTile)
                door = (doorTile) game.getMap()[player.getRow()][player.getCol()];
        }

        assert door != null;
        assertTrue(door.checkValidMove(player));

        //pick up chip
        new moveUp(player).apply();

        //should be one less treasure
        assertEquals(player.getNumberTreasures(),game.getTreasure()-1);

        new moveLeft(player).apply();
        new moveDown(player).apply();

        for(int i = 0; i < 10; i++)
            new moveLeft(player).apply();

        for(int i = 0; i < 7; i++)
            new moveDown(player).apply();

        new moveUp(player).apply();
        new moveLeft(player).apply();

        //all the treasure should be picked up
        assertEquals(player.getNumberTreasures(),0);

        new moveLeft(player).apply();

        //the player should be able to access the treasure door
        new moveDown(player).apply();

        //move to the next level
        new moveDown(player).apply();

        player.moveToNextLevel();
        assertEquals(player.getGame().getLevel(),2);
    }

    @Test
    public void level2Test(){
        Game game = new Game(2);


    }


}
