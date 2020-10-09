package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

class redKey extends RenderItem {

    public redKey(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

class greenKey extends RenderItem {

    public greenKey(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

class chip extends RenderItem {

    public chip(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}
