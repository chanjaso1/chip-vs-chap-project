package nz.ac.vuw.ecs.swen225.gp20.maze;

public class rechargeTile implements Tile {

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
