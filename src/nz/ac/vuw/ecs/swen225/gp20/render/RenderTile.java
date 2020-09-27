package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RenderTile {
    BufferedImage img = null;
    private int x, y;

    public RenderTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawTile(Graphics2D g, int x, int y) {
        g.drawImage(img, null, x, y);
    }
}

class Blue extends RenderTile {

    public Blue(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("chip-vs-chap-project/src/nz/ac/vuw/ecs/swen225/gp20/render/data/blue.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }

    public void drawTile(Graphics2D g) {}
}

class Red extends RenderTile {

    public Red(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("chip-vs-chap-project/src/nz/ac/vuw/ecs/swen225/gp20/render/data/red.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }

    public void drawTile(Graphics2D g) {}
}

class Grey extends RenderTile {

    public Grey(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("chip-vs-chap-project/src/nz/ac/vuw/ecs/swen225/gp20/render/data/grey.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }

    public void drawTile(Graphics2D g) {}
}

class Yellow extends RenderTile {

    public Yellow(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("chip-vs-chap-project/src/nz/ac/vuw/ecs/swen225/gp20/render/data/yellow.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");

        }
    }
}
