package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Information Tile will display information when the player in on it.
 */
public class infoTile implements Tile{
    private int row,col;

    /**
     * wallTile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     */
    infoTile(int row, int col){
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean checkValidMove(Player player) {
        //players can always move on info tile
        return true;
    }
}
