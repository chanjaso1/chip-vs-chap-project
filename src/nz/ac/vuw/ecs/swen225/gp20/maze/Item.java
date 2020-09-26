package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.*;

/**
 * Item is a class the represent the objects on the map to complete the task,
 * such as, Key and Treasure.
 */
public class Item {
    private Tile tile;

    /**
     * Item Constructor requires tile that the item is on.
     * @param tile -- tile that the item is on.
     */
    public Item(Tile tile){
        this.tile = tile;
    }
}


