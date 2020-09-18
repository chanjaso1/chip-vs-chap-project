package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This interface represent the movement being done in the game.
 */
public interface Move {

    /**
     * Apply the movement to the player.
     * @param player -- the current player in the game.
     */
    public void apply(Player player);

}
