package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.HashSet;

public interface Actor {
    /**
     * Move the player in the chosen direction.
     * @param move move class applies according to the players choices in directions
     */
    public void move(Move move);
}

/**
 * Player class to keep track of the player's position, inventory and stages.
 */
class Player implements Actor{
    private HashSet<Item> inventory;
    private int row, col;

    /**
     * Player constructor created by using row and column the player is.
     * @param row -- row that the player is
     * @param col -- column that the player is
     */
    Player(int row , int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Set the player position to the given row and column.
     * @param row -- new row that the player is
     * @param col -- new column that the player is
     */
    public void setPosition(int row , int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Return the player's current column.
     * @return player's current column
     */
    public int getCol() {
        return col;
    }

    /**
     * Return player's current row.
     * @return player's current row.
     */
    public int getRow() {
        return row;
    }

    @Override
    public void move(Move move) {
        move.apply(this);
    }
}

