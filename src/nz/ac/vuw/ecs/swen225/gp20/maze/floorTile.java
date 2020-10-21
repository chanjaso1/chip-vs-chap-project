package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.internal.$Gson$Preconditions;

/**
 * floorTile is a normal tile that the user in able to move on to it.
 */
public class floorTile implements Tile{
    private int row,col;
    private Item item;
    private boolean bugPath = false;

    /**
     * wallTile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     * @param item -- some tiles hold an item such as "Key" or "Treasure"
     */
    public floorTile(int row, int col, Item item){
        $Gson$Preconditions.checkArgument(col >= 0 && col < 30 && row >=0 && row < 30);
        this.row = row;
        this.col = col;
        this.item = item;

    }

    /**
     * Return the name of the tile.
     * @return the string of the tile.
     */
    @Override
    public String toString() {
        return item != null ? item.toString() : "_";
    }

    @Override
    public boolean checkValidMove(Player player) {
        $Gson$Preconditions.checkNotNull(player);
        if(item != null){
            player.pickUp(item);

            //removed the object from the door
            item = null;
        }

        //players can always move on the floor
        return true;
    }

    /**
     * Set this tile to the bug path.
     */
    public void setBugTile(){
        bugPath = true;
    }

    /**
     * Return true if this tile is the bug path.
     * @return true -- this tile is the bug path
     *          false -- this tile is not the bug path
     */
    public boolean isBugPath() {
        return bugPath;
    }

    /**
     * Return the item that is on the tile.
     * @return -- item that's on the tile.
     */
    public Item getItem() {
        return item;
    }
}
