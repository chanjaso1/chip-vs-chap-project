package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.internal.$Gson$Preconditions;

/**
 * If the player move on to the win Tile the current level will be complete,
 * and they will move to the next player if there's one.
 */
public class WinTile implements Tile{

    /**
     * WallTile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     */
    public WinTile(int row, int col){
        $Gson$Preconditions.checkArgument(col >= 0 && col < 30 && row >=0 && row < 30);
    }

    /**
     * Return the name of the tile.
     * @return the string of the tile.
     */
    @Override
    public String toString() {
        return "W";
    }

    @Override
    public boolean checkValidMove(Player player) {
        $Gson$Preconditions.checkNotNull(player);
        //all the treasures need to be collected before accessing the win tile
        return player.getNumberTreasures() == 0;
    }
}
