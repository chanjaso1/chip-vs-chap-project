package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Treasure object used to enter the exit lock.
 */
public class Treasure implements Item {

    /**
     * Treasure Constructor.
     * @param tile -- tile that the treasure is on.
     */
    public Treasure(Tile tile) {

    }

    /**
     * The string of the treasure.
     * @return the string name of the treasure.
     */
    public String toString() {
        return "T";
    }
}