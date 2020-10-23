package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.internal.$Gson$Preconditions;

/**
 * Recharge tile on the map will indicate GUI to increased the timer by 10 seconds.
 */
public class RechargeTile implements Tile {
    boolean recharge = false;

    /**
     * Recharge tile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     */
    public RechargeTile(int row, int col){
        $Gson$Preconditions.checkArgument(col >= 0 && col < 30 && row >=0 && row < 30);
    }

    @Override
    public boolean checkValidMove(Player player) {
        $Gson$Preconditions.checkNotNull(player);
        //player will only be recharge once
        if(!recharge){
            recharge = true;
            player.setPlayerRecharge(true);
        }

        //player can always walk on this tile
        return true;
    }
}
