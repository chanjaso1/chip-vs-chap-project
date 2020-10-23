package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.internal.$Gson$Preconditions;

/**
 * Exit locked door tile is a door that can only be unlocked when all the treasures are picked up, to pass through the door.
 */
public class TreasureDoor implements Tile {

    /**
     * Exit Locked Tile constructor.
     * @param row -- The tile row position.
     * @param col -- The tile column position.
     */
    public TreasureDoor(int row, int col){
        $Gson$Preconditions.checkArgument(col >= 0 && col < 30 && row >=0 && row < 30);
    }
    
    @Override
    public boolean checkValidMove(Player player) {
        $Gson$Preconditions.checkNotNull(player);
        //this door can only open when there're no treasure left

        return player.getNumberTreasures() == 0;
    }
}
