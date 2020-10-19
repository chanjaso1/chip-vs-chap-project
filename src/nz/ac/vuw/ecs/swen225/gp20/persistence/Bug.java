package nz.ac.vuw.ecs.swen225.gp20.persistence;


import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Move;

/**
 * The bug acts as a secondary actor to obstruct the player.
 */
public class Bug extends Actor {

    public Bug(int row, int col) {
        super(row, col);
    }

    /**
     * Move the bug to the next tile
     */
    @Override
    public void move(Move move) {

    }
}
