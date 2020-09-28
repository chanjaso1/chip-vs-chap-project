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

    public RenderItem(int x, int y) {
        this.x = x;
        this.y = y;
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

    public redKey(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/redKey.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }
}

class greenKey extends RenderItem {

    public greenKey(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/greenKey.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }
}

class chip extends RenderItem {

    public chip(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/chip.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }
}
