package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.HashMap;
import java.util.HashSet;

//public interface Actor {
//    /**
//     * Move the player in the chosen direction.
//     * @param move move class applies according to the players choices in directions
//     */
//    public void move(Move move);
//}

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

    public void setTotalTreasures(int treasures) {
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
     *
     */
    public void rechargePlayer() {
        this.playerRecharge = true;
    }

    /**
     * 
     * @return
     */
    public boolean playerIsRecharge() {
        return playerRecharge;
    }
}

