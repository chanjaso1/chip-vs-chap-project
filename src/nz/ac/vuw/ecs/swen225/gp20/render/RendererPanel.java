package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.swing.*;
import java.awt.*;

public class RendererPanel extends JComponent {

    public int[][] board = new int[100][100];
    public Tile[][] tileMap = new Tile[100][100];
    int xPos, yPos;
    int frameWidth, frameHeight;

    int direction = 2;

    Image north, south, east, west;

    public RendererPanel() {
        frameHeight = (1000 /2) - 50;
        frameWidth = (1000 /2) - 50;

        xPos = 25;
        yPos = 25;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        renderTiles(g2d);
        drawPlayer(g2d, this);
        // if greater than 0-24 or 0-25 don't accept.
    }

    public void renderTiles(Graphics2D g) {
        for (int i = 0; i < 5; i++) {
            int invert = i*-1;
            drawRow(g, i);
            drawRow(g, invert);
        }
    }

    public void drawRow(Graphics2D g, int y) {
        // Centre Tile

        tileMap[y + yPos][xPos].drawTile(g, frameWidth, frameHeight + (y * 100));

        for (int i = 1; i < 5; i++) {
            tileMap[y + yPos][xPos - i].drawTile(g, frameWidth - (i * 100), frameHeight + (y * 100));
            tileMap[y + yPos][xPos + i].drawTile(g, frameWidth + (i * 100), frameHeight + (y * 100));
        }
    }

    public void drawPlayer(Graphics2D g, JComponent display) {
        switch (direction) {
            case 0: // North
                g.drawImage(north, frameWidth, frameHeight, display);
                break;
            case 1: //East
                g.drawImage(east, frameWidth, frameHeight, display);
                break;
            case 2: // South
                g.drawImage(south, frameWidth, frameHeight, display);
                break;
            case 3: // West
                g.drawImage(west, frameWidth, frameHeight, display);
                break;
        }
    }

}
