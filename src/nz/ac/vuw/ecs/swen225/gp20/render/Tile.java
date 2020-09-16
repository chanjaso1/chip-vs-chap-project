package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {
    BufferedImage img = null;
    private int x, y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawTile(Graphics2D g, int x, int y) {
        g.drawImage(img, null, x, y);
    }
}

class Blue extends Tile {

    public Blue(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/blue.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }

    public void drawTile(Graphics2D g) {}
}

class Red extends Tile {

    public Red(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/red.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }

    public void drawTile(Graphics2D g) {}
}

class Grey extends Tile {

    public Grey(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/grey.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");
        }
    }

    public void drawTile(Graphics2D g) {}
}

class Yellow extends Tile {

    public Yellow(int x, int y) {
        super(x, y);
        try {
            img = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/yellow.png"));
        } catch (IOException e) {
            System.out.println("Image not found!");

        }
    }
}
