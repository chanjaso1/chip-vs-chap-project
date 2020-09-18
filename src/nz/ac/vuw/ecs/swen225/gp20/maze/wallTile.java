package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * wallTile represent a blocked tile that the player cannot walk pass.
 */
public class wallTile implements Tile {
    private int row,col;

    /**
     * wallTile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     */
    wallTile(int row, int col){
        this.row = row;
        this.col = col;
    }


    @Override
    public boolean checkValidMove(Player player) {
        //players can never move on to walls
        return false;
    }
}
