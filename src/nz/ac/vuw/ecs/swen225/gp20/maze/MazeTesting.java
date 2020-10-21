package nz.ac.vuw.ecs.swen225.gp20.maze;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class is the test class that test things in Maze class.
 */
public class MazeTesting {

    /**
     * Test that they player is able to pick up the key.
     *  By checking the player keys map after the key is picked up.
     */
    @Test
    public void checkKeyIsPickedUp(){
        Game game = new Game(1);
        Player player = game.getPlayer();

        //picking up the keys
        new moveDown(player).apply();

        //checked that the left tile has a key
        assertNotNull(((floorTile) game.getMap()[player.getRow()][player.getCol()-1]).getItem());

        new moveLeft(player).apply();

        //checked that the key is picked up
        assertEquals(1,player.getKeys().size());
    }

    /**
     * Check that player is able to walk through the same color door as the key that they picked up.
     */
    @Test
    public void checkKeyDoorUnLocked(){
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
        //door is unlocked
        assertTrue(door.checkValidMove(player));

        //door is the same color as key
        assertEquals(door.getColor(),player.getKeys().get(door.getColor()).getColor());
    }

    /**
     * Check that the player cannot walk through wall.
     */
    @Test
    public void checkPlayerCannotWalkThroughWall(){
        Game game = new Game(1);
        int currentRow,currentCol;
        Player player = game.getPlayer();

        for(int i = 0; i < 3; i++)
            new moveRight(player).apply();

        currentRow = player.getRow();
        currentCol = player.getCol();

        new moveRight(player).apply(); // player won't move through the door

        //stayed at the same position
        assertEquals(player.getCol(),currentCol);
        assertEquals(player.getRow(),currentRow);

        assertFalse(game.getMap()[currentRow][currentCol+1].checkValidMove(player));
    }

    /**
     * Check that the player cannot walk through the door when they don't have the key.
     */
    @Test
    public void testDoorKeyLocked(){
        Game game = new Game(1);
        int currentRow,currentCol;
        Player player = game.getPlayer();

        //picking up the keys
        new moveDown(player).apply();

        for(int i = 0; i < 3; i++)
            new moveRight(player).apply();

        currentRow = player.getRow();
        currentCol = player.getCol();

        new moveRight(player).apply(); // player won't move through the door

        //stayed at the same position
        assertEquals(player.getCol(),currentCol);
        assertEquals(player.getRow(),currentRow);
        //door is locked
        assertFalse(game.getMap()[currentRow][currentCol+1].checkValidMove(player));
    }

    /**
     * Check that the numbers of treasure that is left decreased when player picked up the treasure.
     */
    @Test
    public void checkTreasureIsPickedUp(){
        Game game = new Game(1);
        Player player = game.getPlayer();

        //picking up the keys
        new moveDown(player).apply();
        new moveLeft(player).apply();

        for(int i = 0; i < 7; i++) {
            new moveRight(player).apply();
        }

        int currentTreasure = player.getNumberTreasures();

        //pick up chip
        new moveUp(player).apply();
        assertEquals(currentTreasure - 1 ,player.getNumberTreasures());
    }

    /**
     * Checked that the player has picked up all the treasures and the treasure door is unlocked.
     * Checked that the player is able to move onto the win tile.
     */
    @Test
    public void checkTreasureDoorAndWinTile(){
        Game game = new Game(1);
        Player player = game.getPlayer();

        //picking up the keys
        new moveDown(player).apply();
        new moveLeft(player).apply();

        for(int i = 0; i < 7; i++) {
            new moveRight(player).apply();
        }
        //pick up chip
        new moveUp(player).apply();

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
        assertTrue(game.getMap()[player.getRow()][player.getCol()].checkValidMove(player));

        //move to win tile
        new moveDown(player).apply();
        assertTrue(game.getMap()[player.getRow()][player.getCol()].checkValidMove(player));
    }

    /**
     * Checked that player is able to move to the next level and the level us.
     */
    @Test
    public void playerMovingToNextLevel(){
        Game game = new Game(1);
        Player player = game.getPlayer();
        int lv1Treasure = game.getTreasure();

        player.moveToNextLevel();
        //player is now in level 2
        assertEquals(game.getLevel(),2);

        player.getGame().loadLevel();
        //the should be a bug object in the next level if moving to the next level is successful
        assertNotNull(game.getBug());

        //number of treasure that's is left is not 0
        assertNotEquals(player.getNumberTreasures(),0);

        //number of total treasure changed
        assertNotEquals(lv1Treasure,game.getTreasure());
    }

    /**
     * When the player is attacked by bug it's position should be reset back to the start position.
     */
    @Test
    public void checkPlayerPositionRestartWhenAttackedByBug(){
        Game game = new Game(2);
        Player player = game.getPlayer();
        int startRow,startCol;
        startRow = player.getRow();
        startCol = player.getCol();

        ArrayList<Move> playersMovement = new ArrayList<>();
        //moving towards the enemies
        for (int i = 0; i < 7; i++) {
            playersMovement.add(new moveLeft(player));
        }

        //move bug by it set path and user by the input path
        for(int i = 0; i < playersMovement.size(); i++) {
            try {
                game.getParser().aClass.getMethod("moveBugSequence").invoke(game.getBug());
                game.updatePlayerBugStatus();
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                ex.printStackTrace();
            }

            playersMovement.get(i).apply();
        }

        //player is set back to the start position after getting attack by bug
        assertEquals(player.getRow(),startRow);
        assertEquals(player.getCol(),startCol-1); //one extra movement is been made
    }

    /**
     * When the player is on the recharge tile the state will change to recharge.
     */
    @Test
    public void TestRechargeTile(){
        Game game = new Game(2);
        Player player = game.getPlayer();

        new moveDown(player).apply();
        new moveDown(player).apply();

        for(int i =0; i < 10; i++){
            new moveLeft(player).apply();
        }

        rechargeTile tile = (rechargeTile) game.getMap()[player.getRow()][player.getCol()];
        tile.checkValidMove(player);

        assertTrue(player.playerIsRecharge());

    }


}
