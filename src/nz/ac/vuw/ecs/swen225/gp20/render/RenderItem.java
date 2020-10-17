package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
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

class RedKey extends RenderItem {

    public RedKey(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

class GreenKey extends RenderItem {

    public GreenKey(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

class Chip extends RenderItem {

    public Chip(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

class Enemy extends RenderItem {
    Image gif = null;
    JComponent display = null;

    public Enemy(int x, int y, Image i,JComponent d) {
        super(x, y, null);
        this.gif = i;
        this.display = d;
    }

    public void drawEnemy(Graphics2D g, int x, int y) {
        g.drawImage(gif, x, y, display);
    }

}
