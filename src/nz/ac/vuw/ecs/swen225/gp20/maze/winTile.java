package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * If the player move on to the win Tile the current level will be complete,
 * and they will move to the next player if there's one.
 */
public class winTile implements Tile{
    private int row,col;

    /**
     * wallTile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     */
    public winTile(int row, int col){
        this.row = row;
        this.col = col;
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
        player.moveToNextLevel();

        //player can always move onto this tile
        return true;
    }
}
