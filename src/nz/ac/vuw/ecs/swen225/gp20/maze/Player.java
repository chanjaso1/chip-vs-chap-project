package nz.ac.vuw.ecs.swen225.gp20.maze;

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
    private HashSet<Key> keys = new HashSet<>();
    private int treasures = 0;

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
    public HashSet<Key> getKeys() {
        return keys;
    }

    /**
     * This function is called to pick up treasures.
     * @param item -- item that the player picked up.
     */
    public void pickUp(Item item){
        if(item instanceof Key){
            keys.add(((Key) item));
        } else {
            treasures++;
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
     * move the player to the next level
     */
    public void moveToNextLevel(){
        this.getGame().setLevel(this.getGame().getLevel()+1);
    }

}

