package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;


public class Bug extends Actor {
    boolean moveDownFirst = true;

    public Bug(int row, int col) {
        super(row, col);
    }

    @Override
    public void move(Move move) {

        //check if next move path of the bug, then either move it or change direction.
        if(moveDownFirst && ((floorTile) this.getGame().getMap()[getRow()+1][getCol()]).isBugPath()){
            moveDown down = new moveDown(this);
        }else if(!moveDownFirst && ((floorTile) this.getGame().getMap()[getRow()-1][getCol()]).isBugPath()){
            moveUp up = new moveUp(this);
        }else {
            moveDownFirst = !moveDownFirst;
        }
    }

    @Override
    public String toString() {
        return "B";
    }
}
