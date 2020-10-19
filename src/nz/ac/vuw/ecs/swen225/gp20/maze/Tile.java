package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Tile interface is use to represent each tile oh the map.
 */
public interface Tile {

    /**
     * Check that this tile is valid for the player to move on or not.
     * @return true -- player is allowed to move onto this tile.
     *         false -- player is not allowed to move onto this tile.
     */
    public boolean checkValidMove(Player player);

}
