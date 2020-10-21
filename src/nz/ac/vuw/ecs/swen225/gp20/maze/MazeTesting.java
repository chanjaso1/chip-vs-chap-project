package nz.ac.vuw.ecs.swen225.gp20.maze;




import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class is the test class that test things in Maze class.
 */
public class MazeTesting {

    @Test
    public void checkKeyIsPickedUp(){
        Game game = new Game(1);
        Player player = game.getPlayer();

        //picking up the keys
        new moveDown(player).apply();
        new moveLeft(player).apply();

        //checked that the key is picked up
        assertEquals(1,player.getKeys().size());
    }

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

    @Test
    public void checkPlayerCannotWalkThroughDoor(){
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

        //pick up chip
        new moveUp(player).apply();
    }

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

    @Test
    public void playerMovingToNextLevel(){
        Game game = new Game(1);
        Player player = game.getPlayer();

        player.moveToNextLevel();
        //player is now in level 2
        assertEquals(game.getLevel(),2);

        player.getGame().loadLevel();
        //the should be a bug object in the next level if moving to the next level is successful
        assertNotNull(game.getBug());
    }

    @Test
    public void checkPlayerPositionRestartWhenAttackedByBug(){
        Game game = new Game(2);
        Player player = game.getPlayer();
        int startRow,startCol;
        startRow = player.getRow();
        startCol = player.getCol();

        System.out.println("START   " + startRow + "    " + startCol);

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

    @Test
    public void TestRechargeTile(){

    }


}
