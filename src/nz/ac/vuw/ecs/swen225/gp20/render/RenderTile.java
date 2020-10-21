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
class WallRender extends RenderTile {

    public WallRender(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

/**
 * The floor tile to render in
 */
class FloorRender extends RenderTile {

    public FloorRender(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}

/**
 * The red door tile to render in
 */
class RedDoorRender extends RenderTile {
    BufferedImage unlocked;

    public RedDoorRender(int x, int y, BufferedImage i, BufferedImage u) {
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
class GreenDoorRender extends RenderTile {
    BufferedImage unlocked;

    public GreenDoorRender(int x, int y, BufferedImage i, BufferedImage u) {
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
class ExitTileRender extends RenderTile {
    Image gif = null;
    JComponent display = null;

    public ExitTileRender(int x, int y, Image g, JComponent display) {
        super(x, y, null);
        this.display = display;
        this.gif = g;
    }

    @Override
    public void drawTile(Graphics2D g, int x, int y) {
        g.drawImage(gif, x, y, display);
    }
}

class ChipDoorRender extends RenderTile {
    Image gif = null;
    Image unlocked = null;
    JComponent display = null;

    public ChipDoorRender(int x, int y, Image g, Image o, JComponent display) {
        super(x, y, null);
        this.display = display;
        this.gif = g;
        this.unlocked = o;
    }

    @Override
    public void drawTile(Graphics2D g, int x, int y) {

        if (!open) g.drawImage(gif, x, y, display);
        else g.drawImage(unlocked, x, y, display);
    }
}

class InfoRender extends RenderTile {

    public InfoRender(int x, int y, BufferedImage i) {
        super(x, y, i);
    }
}