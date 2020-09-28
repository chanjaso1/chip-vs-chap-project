package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * floorTile is a normal tile that the user in able to move on to it.
 */
public class floorTile implements Tile{
    private int row,col;
    private Item item;

    /**
     * wallTile constructor.
     * @param row -- the tile row position.
     * @param col -- the tile column position.
     * @param item -- some tiles hold an item such as "Key" or "Treasure"
     */
    public floorTile(int row, int col, Item item){
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
        if(item != null){
            player.pickUp(item);
        }

        //players can always move on the floor
        return true;
    }

    public Item getItem() {
        return item;
    }
}
