package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.internal.$Gson$Preconditions;

/**
 * Information Tile will display information when the player in on it.
 */
public class InfoTile implements Tile{

    /**
     * WallTile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     */
    public InfoTile(int row, int col){
        $Gson$Preconditions.checkArgument(col >= 0 && col < 30 && row >=0 && row < 30);
    }

    @Override
    public boolean checkValidMove(Player player) {
        $Gson$Preconditions.checkNotNull(player);
        //players can always move on info tile
        return true;
    }
}
