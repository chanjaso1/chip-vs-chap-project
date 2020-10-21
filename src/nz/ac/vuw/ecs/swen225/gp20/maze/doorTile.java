package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.internal.$Gson$Preconditions;

/**
 * Door Tile is a door that can only be unlocked with the right element to pass through the door.
 * For example, same color keys.
 */
public class doorTile implements Tile{
    private int row,col;
    private String color;
    private boolean open;

    /**
     * WallTile constructor.
     * @param row -- The tile row position.
     * @param col -- The tile column position.
     * @param color -- Color of the door.
     */
    public doorTile(int row, int col, String color){
        $Gson$Preconditions.checkArgument(col >= 0 && col < 30 && row >=0 && row < 30);
        $Gson$Preconditions.checkNotNull(color);
        this.row = row;
        this.col = col;
        this.color = color;
        open = false;
    }

    /**
     * Return the name of the tile.
     * @return the string of the tile.
     */
    @Override
    public String toString() {
        return "D" + color;
    }

    @Override
    public boolean checkValidMove(Player player) {
        $Gson$Preconditions.checkNotNull(player);
        if(open) return true;

        //player can only move on to this tile if they have the correct color key
        for(Key key : player.getKeys().values()){
            if(key.getColor().equalsIgnoreCase(color)){
                open = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Return the color of the key.
     * @return -- color of the key.
     */
    public String getColor() {
        return this.color;
    }
}
