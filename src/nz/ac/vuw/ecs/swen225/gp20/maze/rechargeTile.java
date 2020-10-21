package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.internal.$Gson$Preconditions;

public class rechargeTile implements Tile {
    private int row,col;
    boolean recharge = false;

    /**
     * Recharge tile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     */
    public rechargeTile(int row, int col){
        $Gson$Preconditions.checkArgument(col >= 0 && col < 30 && row >=0 && row < 30);
        this.row = row;
        this.col = col;
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
