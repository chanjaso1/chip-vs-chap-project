package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Locked door Tile is a door that can only be unlocked with the right color key, to pass through the door.
 */
public class lockedDoorTile implements Tile{
    private int row,col;
    private String color;

    /**
     * locked Door Tile constructor.
     * @param row -- The tile row position.
     * @param col -- The tile column position.
     * @param color -- Color of the door.
     */
    lockedDoorTile(int row, int col, String color){
        this.row = row;
        this.col = col;
        this.color = color;
    }


    @Override
    public boolean checkValidMove(Player player) {
        //player can only move on to this tile if they have the correct color key


        //@TODO check valid
        return false;
    }
}
