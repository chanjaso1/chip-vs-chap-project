package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;

/**
 * RendererPanel is a class that extends JComponent, so that it may be added ot the GUI JFrame.
 * This class acts as the view display for the game scene, and renders the visuals and sounds of the game.
 */
public class RendererPanel extends JComponent {

    public int[][] board = new int[100][100];
    public RenderTile[][] tileMap = new RenderTile[30][30];
    public Tile[][] levelTiles;
    public int xPos, yPos;
    public int frameWidth, frameHeight;

    // 0 - North
    // 1 - East
    // 2 - South
    // 3 - West
    int direction = 2;

    Game game;

    Image north, south, east, west;

    public RendererPanel(Game g) {
        frameHeight = (906 /2) - 50;
        frameWidth = (1000 /2) - 50;

        // BREAKPOINT HERE - Instantiate game, and then invoking method to access map state.
        game = g;
        levelTiles = game.getMap();

        // Print out formatted level map
        for (int i = 0; i < levelTiles.length; i++) {
            System.out.println();
            for (int j = 0; j < levelTiles[0].length; j++) {
                //System.out.print(" ");
                //System.out.print(levelTiles[i][j]);
                if (levelTiles[i][j] instanceof floorTile) System.out.print("▊");
                else if (levelTiles[i][j] instanceof wallTile) System.out.print("0");
                else if (levelTiles[i][j] instanceof doorTile) System.out.print("K");
                else if (levelTiles[i][j] instanceof winTile) System.out.print("W");

            }
        }

        xPos = game.getPlayer().getCol();
        yPos = game.getPlayer().getRow();

        // Initialize character facing GIFS
        north = new ImageIcon(getClass().getResource("resource/backFacing.gif")).getImage();
        south = new ImageIcon(getClass().getResource("resource/frontFacing.gif")).getImage();
        east = new ImageIcon(getClass().getResource("resource/rightFacing.gif")).getImage();
        west = new ImageIcon(getClass().getResource("resource/leftFacing.gif")).getImage();

        // Make render map based on tiles obtained from Game
        RenderTile tile = null;

        for (int i = 0; i < levelTiles.length; i++) {
            for (int j = 0; j < levelTiles[0].length; j++) {
                if (levelTiles[i][j] instanceof floorTile) {
                    tile = new Blue(i, j);
                } else if (levelTiles[i][j] instanceof wallTile) {
                    tile = new Red(i, j);
                } else if (levelTiles[i][j] instanceof doorTile) {
                    tile = new Grey(i, j);
                } else if (levelTiles[i][j] instanceof winTile) {
                    tile = new Yellow(i, j);
                }
                tileMap[i][j] = tile;
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        renderTiles(g2d);
        drawKeys(g2d);
        drawPlayer(g2d, this);
    }

    /**
     * Draws all the tiles onto the RenderPanel
     * @param g the graphics object that draws on the RenderPanel
     */
    public void renderTiles(Graphics2D g) {
        for (int i = 0; i < 5; i++) {
            int invert = i*-1;
            drawRow(g, i);
            drawRow(g, invert);
        }
    }

    /**
     * Draws an entire line of squares.
     * @param g the graphics object that draws on the RenderPanel
     * @param y the row to draw at
     */
    public void drawRow(Graphics2D g, int y) {
        // Centre Tile
        tileMap[y + yPos][xPos].drawTile(g, frameWidth, frameHeight + (y * 100));

        for (int i = 1; i < 5; i++) {
            tileMap[y + yPos][xPos - i].drawTile(g, frameWidth - (i * 100), frameHeight + (y * 100));
            tileMap[y + yPos][xPos + i].drawTile(g, frameWidth + (i * 100), frameHeight + (y * 100));
        }
    }

    /**
     * Draws all keys/ Treasures visible on the rendering clip
     * @param g the graphics object that draws on the RenderPanel
     */
    public void drawKeys(Graphics2D g) {
        for(int i = -4; i < 5; i++) {
            for(int j = -4; j < 5; j++) {
                if (levelTiles[yPos + i][xPos + j] instanceof floorTile) {
                    floorTile f = (floorTile)levelTiles[yPos + i][xPos + j];
                    if (f.getItem() instanceof Key) {
                        g.setColor(new Color(255, 162, 10));
                        g.fillOval(frameWidth + (j * 100), frameHeight + (i * 100), 100, 100);
                    } else if (f.getItem() instanceof Treasure) {
                        g.setColor(new Color(105, 242, 255));
                        g.fillOval(frameWidth + (j * 100), frameHeight + (i * 100), 100, 100);
                    }
                }
            }
        }
    }

    /**
     * Draws the player gif onto the centre of the display panel, facing the direction
     * of the last movement key press.
     * @param g the graphics object that draws on the RenderPanel
     * @param display the RenderPanel to show the animation
     */
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

    /**
     * Called when a movement key press event occurs, this updates the display to shift along
     * in the direction of the key press.
     * @param dir the direction of key-press to appropriately update player facing direction
     */
    public void renderMove(int dir) {
        if(dir == 1) {
            System.out.println("Right");
            xPos = game.getPlayer().getCol();
            yPos = game.getPlayer().getRow();
            System.out.println(xPos + " " + yPos);
            direction = 1;
        }
        else if(dir == 3) {
            System.out.println("Left");
            xPos = game.getPlayer().getCol();
            yPos = game.getPlayer().getRow();
            System.out.println(xPos + " " + yPos);
            direction = 3;
        }
        else if(dir == 2) {
            System.out.println("Down");
            xPos = game.getPlayer().getCol();
            yPos = game.getPlayer().getRow();
            System.out.println(xPos + " " + yPos);
            direction = 2;
        }
        else if(dir == 0) {
            System.out.println("Up");
            xPos = game.getPlayer().getCol();
            yPos = game.getPlayer().getRow();
            System.out.println(xPos + " " + yPos);
            direction = 0;
        }

        this.repaint();
    }
}
