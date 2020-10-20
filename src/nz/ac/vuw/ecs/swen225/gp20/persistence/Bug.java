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
     */
    public void moveBugSequence() {

        //check if next move path of the bug, then either move it or change direction.
        if (moveDownFirst && ((floorTile) this.getGame().getMap()[getRow() + 1][getCol()]).isBugPath()) {
            moveDown down = new moveDown(this);
            down.apply();
            System.out.println("BU  GUP!! row: " + this.getRow() + "  col:  " + this.getCol());
        }else if(!moveDownFirst && ((floorTile) this.getGame().getMap()[getRow()-1][getCol()]).isBugPath()){
            moveUp up = new moveUp(this);
            up.apply();
            System.out.println("BUG DOWN!! row: " + this.getRow() + "  col:  " + this.getCol());
        }else {
            moveDownFirst = !moveDownFirst;
            System.out.println("else condition true");
        }
    }

    @Override
    public String toString() {
        return "B";
    }
}

