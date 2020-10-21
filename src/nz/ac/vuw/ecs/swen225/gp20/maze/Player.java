package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.internal.$Gson$Preconditions;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Player class to keep track of the player's position, inventory and stages.
 */
public class Player extends Actor {
    private HashMap<String,Key> keys = new HashMap<>();
    private int treasures = 0;
    private int startRow, startCol;
    private boolean playerRecharge = false;

    /**
     * Player constructor created by using row and column the player is.
     * @param row -- row that the player is
     * @param col -- column that the player is
     */
    public Player(int row, int col){
        super(row, col);
    }

    @Override
    public String toString() {
        return "P";
    }

    /**
     * Return the  keys that has been picked up.
     * @return -- player's keys.
     */
    public HashMap<String,Key> getKeys() {
        return keys;
    }

    /**
     * This function is called to pick up treasures.
     * @param item -- item that the player picked up.
     */
    public void pickUp(Item item){
        $Gson$Preconditions.checkNotNull(item);
        if(item instanceof Key){
            keys.put(((Key) item).getColor(), ((Key) item));
        } else {
            treasures--;
            System.out.println("treasures: " + treasures);
        }
    }

    /**
     * Return the numbers of the treasures that's been picked.
     * @return -- number of treasures that's been picked.
     */
    public int getNumberTreasures() {
        return treasures;
    }

    /**
     * Set total treasure to the total treasure in the game.
     * @param treasures -- total treasures in the game.
     */
    public void setTotalTreasures(int treasures) {
        $Gson$Preconditions.checkArgument(treasures > 0);
        this.treasures = treasures;
    }

    /**
     * move the player to the next level
     */
    public void moveToNextLevel(){
        this.getGame().setLevel(this.getGame().getLevel()+1);
    }

    /**
     * Save the player start position, so when the player has to restart this position can be used.
     * @param row -- the first row player start at.
     * @param col -- the first column player start at.
     */
    public void setStartPosition(int row, int col) {
        $Gson$Preconditions.checkArgument(col >= 0 && col < 30 && row >=0 && row < 30);
        this.startRow = row;
        this.startCol = col;
    }

    /**
     * If the player is attack by a bug player would be set back to the start position.
     */
    public void setPlayerBackToStartPosition(){
        this.setPosition(startRow,startCol);
    }

    /**
     * Set the player recharge field to true.
     * When the player is recharge is means that player will get some extra time to finish the task.
     */
    public void setPlayerRecharge(boolean state) {
        this.playerRecharge = state;
    }

    /**
     * Return the player recharge field.
     * @return true -- player has been recharged.
     *         false -- player has not been recharge.
     */
    public boolean playerIsRecharge() {
        return playerRecharge;
    }
}

