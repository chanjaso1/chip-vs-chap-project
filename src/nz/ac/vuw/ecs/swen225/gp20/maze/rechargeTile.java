package nz.ac.vuw.ecs.swen225.gp20.maze;

public class rechargeTile implements Tile {
    private int row,col;

    /**
     * Recharge tile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     */
    public rechargeTile(int row, int col){
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean checkValidMove(Player player) {
        //player will only be recharge once
        if(!player.playerIsRecharge()){
            player.rechargePlayer();
        }

        //player can always walk on this tile
        return true;
    }
}
