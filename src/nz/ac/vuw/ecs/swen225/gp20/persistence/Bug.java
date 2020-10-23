package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;

/**
 * The bug acts as a second actor which patrols along path and takes out the character if there is a collision.
 * NOTE: This class is not directly used. The jar files need this class in the directory to be from, and the JAR files
 * are used to access this class through reflection. The game works fine if the bug class is deleted as long as the JAR file 
 * is not built again. The project may be rebuilt which will not affect the game.
 */
public class Bug extends Actor {
    boolean moveDownFirst = true;

    public static void main(String[] args) {

    }

    public Bug(int row, int col) {
        super(row, col);
    }

    /**
     * This method moves the bug to the next valid tile
     *
     * @return the movement of the bug.
     */
    public Move moveBugSequence() {
        //check if next move path of the bug, then either move it or change direction.
        if (moveDownFirst && ((FloorTile) this.getGame().getMap()[getRow() + 1][getCol()]).isBugPath()) {
            MoveDown down = new MoveDown(this);
            down.apply();
            return down;

        } else if (!moveDownFirst && ((FloorTile) this.getGame().getMap()[getRow() - 1][getCol()]).isBugPath()) {
            MoveUp up = new MoveUp(this);
            up.apply();
            return up;
        } else {
            moveDownFirst = !moveDownFirst;
        }
        return null;
    }

    @Override
    public String toString() {
        return "B";
    }
}

