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
    public Key(Tile tile, String color){
        super(tile);
        this.color = color;
    }

     @Override
     public String toString() {
         return "K" + color;
     }

     /**
     * Return the color of the Key.
     * @return -- color of the key.
     */
    public String getColor() {
        return color;
    }

}

