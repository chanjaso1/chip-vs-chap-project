package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Door Tile is a door that can only be unlocked with the right element to pass through the door.
 * For example, same color keys.
 */
public class doorTile implements Tile{
    private int row,col;
    private String color;
    private boolean open;

    //@TODO should we have two types or one types of doors ????

    /**
     * WallTile constructor.
     * @param row -- The tile row position.
     * @param col -- The tile column position.
     * @param color -- Color of the door.
     */
    public doorTile(int row, int col, String color){
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
        if(open) return true;

        //player can only move on to this tile if they have the correct color key
        if(player instanceof Player){

        }
        for(Key key : player.getKeys()){
            if(key.getColor().equalsIgnoreCase(color)){
                open = true;
                return true;
            }
        }
        return false;
    }

    public String getColor() {
        return this.color;
    }
}
