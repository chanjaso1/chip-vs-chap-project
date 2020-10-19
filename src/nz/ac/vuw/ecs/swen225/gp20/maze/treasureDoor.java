package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Exit locked door tile is a door that can only be unlocked when all the treasures are picked up, to pass through the door.
 */
public class treasureDoor implements Tile {
    private int row,col;

    /**
     * Exit Locked Tile constructor.
     * @param row -- The tile row position.
     * @param col -- The tile column position.
     */
    public treasureDoor(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    @Override
    public boolean checkValidMove(Player player) {
        if(player.getNumberTreasures() < player.getGame().getParser().getTreasures()){
            return false;
        }
        return true;
    }
}
