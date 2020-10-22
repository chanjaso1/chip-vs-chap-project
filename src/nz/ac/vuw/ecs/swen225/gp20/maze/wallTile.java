package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.internal.$Gson$Preconditions;

/**
 * wallTile represent a blocked tile that the player cannot walk pass.
 */
public class wallTile implements Tile {

    /**
     * wallTile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     */
    public wallTile(int row, int col){
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
