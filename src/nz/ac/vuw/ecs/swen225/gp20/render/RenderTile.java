package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The RenderTile is the superclass of all Tiles that are Rendered onto the RenderPanel display.
 * @author John Eleigio Cecilio (cecilijohn - 300485264)
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

/**
 * The wall tile to render in
 */
class Wall extends RenderTile {

    public Wall(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

/**
 * The floor tile to render in
 */
class Floor extends RenderTile {

    public Floor(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

/**
 * The red door tile to render in
 */
class RedDoor extends RenderTile {
    BufferedImage unlocked;

    public RedDoor(int x, int y, BufferedImage i, BufferedImage u) {
        super(x, y, i);
        this.unlocked = u;
    }

    @Override
    public void drawTile(Graphics2D g, int x, int y) {
        if (!open) g.drawImage(img, null, x, y);
        else g.drawImage(unlocked, null, x, y);
    }
}

/**
 * The green door tile to render in
 */
class GreenDoor extends RenderTile {
    BufferedImage unlocked;

    public GreenDoor(int x, int y, BufferedImage i, BufferedImage u) {
        super(x, y, i);
        this.unlocked = u;
    }

    @Override
    public void drawTile(Graphics2D g, int x, int y) {
        if (!open) g.drawImage(img, null, x, y);
        else g.drawImage(unlocked, null, x, y);
    }
}

/**
 * The exit tile to render in
 */
class ExitTile extends RenderTile {
    Image gif = null;
    JComponent display = null;

    public ExitTile(int x, int y, Image g, JComponent display) {
        super(x, y, null);
        this.display = display;
        this.gif = g;
    }

    @Override
    public void drawTile(Graphics2D g, int x, int y) {
        g.drawImage(gif, x, y, display);
    }
}

class ChipDoorTile extends RenderTile {
    Image gif = null;
    JComponent display = null;

    public ChipDoorTile(int x, int y, Image g, JComponent display) {
        super(x, y, null);
        this.display = display;
        this.gif = g;
    }

    @Override
    public void drawTile(Graphics2D g, int x, int y) {
        g.drawImage(gif, x, y, display);
    }
}
