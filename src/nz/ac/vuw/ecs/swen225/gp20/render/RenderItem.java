package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * RenderItem is a class that allows for the drawing of objects onto the canvas
 * @author John Eleigio Cecilio (cecilijohn - 300485264)
 */
public class RenderItem {
    BufferedImage img = null;
    private int x, y;

    public RenderItem(int x, int y, BufferedImage i) {
        this.x = x;
        this.y = y;
        this.img = i;
    }

    /**
     * Draws the item image onto a specific position on the render display.
     * @param g the graphics object that draws on the RenderPanel
     * @param x x Position on RenderPanel
     * @param y y Position on RenderPanel
     */
    public void drawItem(Graphics2D g, int x, int y) {
        g.drawImage(img, null, x, y);
    }
}

/**
 * The red key item to render in
 */
class RedKey extends RenderItem {

    public RedKey(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

/**
 * The green key item to render in
 */
class GreenKey extends RenderItem {

    public GreenKey(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

/**
 * The chip (treasure) item to render in
 */
class Chip extends RenderItem {

    public Chip(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

/**
 * The enemy to render in
 */
class Enemy extends RenderItem {
    Image gif = null;
    JComponent display = null;

    public Enemy(int x, int y, Image i,JComponent d) {
        super(x, y, null);
        this.gif = i;
        this.display = d;
    }

    public void drawItem(Graphics2D g, int x, int y) {
        g.drawImage(gif, x, y, display);
    }

}
