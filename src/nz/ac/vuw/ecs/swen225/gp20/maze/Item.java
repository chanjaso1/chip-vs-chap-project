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

/**
 * Key object used to open the door that has the same color as the key.
 */
class Key extends Item{
    private Color color;

    /**
     * Key Constructor.
     * @param tile -- tile that the key is on.
     * @param color -- color of the key.
     */
    Key(Tile tile, Color color){
        super(tile);
        this.color = color;
    }

    /**
     * Return the color of the Key.
     * @return -- color of the key.
     */
    public Color getColor() {
        return color;
    }
}

/**
 * Treasure object used to enter the exit lock.
 */
class Treasure extends Item {

    /**
     * Treasure Constructor.
     * @param tile -- tile that the treasure is on.
     */
    public Treasure(Tile tile) {
        super(tile);
    }
}
