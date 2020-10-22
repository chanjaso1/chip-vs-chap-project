package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.internal.$Gson$Preconditions;

/**
 * WallTile represent a blocked tile that the player cannot walk pass.
 */
public class WallTile implements Tile {

    /**
     * WallTile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     */
    public WallTile(int row, int col){
        $Gson$Preconditions.checkArgument(col >= 0 && col < 30 && row >=0 && row < 30);
    }

    /**
     * Return the name of the tile.
     * @return the string of the tile.
     */
    @Override
    public String toString() {
        return "▊";
    }

    @Override
    public boolean checkValidMove(Player player) {
        $Gson$Preconditions.checkNotNull(player);
        //players can never move on to walls
        return false;
    }
}
