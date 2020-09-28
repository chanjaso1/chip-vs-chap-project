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

    public RenderTile(int x, int y) {
        this.x = x;
        this.y = y;
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

    public Wall(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/wall.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }
}

class Floor extends RenderTile {

    public Floor(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/floor.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }
}

class RedDoor extends RenderTile {
    BufferedImage unlocked;

    public RedDoor(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/redDoor.png"));
            unlocked = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/unlocked.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }

    @Override
    public void drawTile(Graphics2D g, int x, int y) {
        if (!open) g.drawImage(img, null, x, y);
        else g.drawImage(unlocked, null, x, y);
    }
}

class GreenDoor extends RenderTile {
    BufferedImage unlocked;

    public GreenDoor(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/greenDoor.png"));
            unlocked = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/unlocked.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }

    @Override
    public void drawTile(Graphics2D g, int x, int y) {
        if (!open) g.drawImage(img, null, x, y);
        else g.drawImage(unlocked, null, x, y);
    }
}

class Blue extends RenderTile {

    public Blue(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/blue.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }
}

class Red extends RenderTile {

    public Red(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/red.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }
}

class Grey extends RenderTile {

    public Grey(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/grey.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }
}

class Yellow extends RenderTile {

    public Yellow(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/yellow.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");

        }
    }
}
