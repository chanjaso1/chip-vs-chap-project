package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This interface represent the characters in the game.
 */
public interface Actor {
    /**
     * Move the player in the chosen direction.
     * @param move move class applies according to the players choices in directions
     */
    public void move(Move move);
}
