package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The RenderTile is the superclass of all Tiles that are Rendered onto the RenderPanel display.
 */
public class RenderTile {
    BufferedImage img = null;
    private int x, y;
    boolean open = false;

    public RenderTile(int x, int y, BufferedImage i) {
        this.x = x;
        this.y = y;
        this.img = i;
    }

    /**
     * Draws the tile image onto a specific position on the render display.
     * @param g the graphics object that draws on the RenderPanel
     * @param x x Position on RenderPanel
     * @param y y Position on RenderPanel
     */
    public void drawTile(Graphics2D g, int x, int y) {
        g.drawImage(img, null, x, y);
    }

    public void setOpen() {
        this.open = true;
    }
}

class Wall extends RenderTile {

    public Wall(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

class Floor extends RenderTile {

    public Floor(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

class RedDoor extends RenderTile {
    BufferedImage unlocked;

    public RedDoor(int x, int y, BufferedImage i) {
        super(x, y, i);
    }

    @Override
    public void drawTile(Graphics2D g, int x, int y) {
        if (!open) g.drawImage(img, null, x, y);
        else g.drawImage(unlocked, null, x, y);
    }
}

class GreenDoor extends RenderTile {
    BufferedImage unlocked;

    public GreenDoor(int x, int y, BufferedImage i) {
        super(x, y, i);
    }

    @Override
    public void drawTile(Graphics2D g, int x, int y) {
        if (!open) g.drawImage(img, null, x, y);
        else g.drawImage(unlocked, null, x, y);
    }
}

class Yellow extends RenderTile {

    public Yellow(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}
