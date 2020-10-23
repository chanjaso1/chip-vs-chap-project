package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;

/**
 * The bug acts as a second actor which patrols along path and takes out the character if there is a collision.
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

