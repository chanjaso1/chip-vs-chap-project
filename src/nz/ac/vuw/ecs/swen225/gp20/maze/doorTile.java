package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Door Tile is a door that can only be unlocked with the right element to pass through the door.
 * For example, same color keys.
 */
public class doorTile implements Tile{
    private int row,col;
    private String color;

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
    }


    @Override
    public boolean checkValidMove(Player player) {
        //player can only move on to this tile if they have the correct color key
        for(Key key : player.getKeys()){
            if(key.getColor().equalsIgnoreCase(color)){
                //remove the door
                player.getGame().getMap()[row][col] = new floorTile(row,col,null);

                return true;
            }
        }
        return false;
    }
}
