package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.*;

 /**
 * Key object used to open the door that has the same color as the key.
 */
public class Key extends Item{
    private String color;

    /**
     * Key Constructor.
     * @param tile -- tile that the key is on.
     * @param color -- color of the key.
     */
    Key(Tile tile, String color){
        super(tile);
        this.color = color;
    }

    /**
     * Return the color of the Key.
     * @return -- color of the key.
     */
    public String getColor() {
        return color;
    }

}

