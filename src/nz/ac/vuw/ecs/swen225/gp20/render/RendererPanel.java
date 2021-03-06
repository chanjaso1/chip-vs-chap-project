package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;

/**
 * RendererPanel is a class that extends JComponent, so that it may be added ot the GUI JFrame.
 * This class acts as the view display for the game scene, and renders the visuals and sounds of the game.
 * @author John Eleigio Cecilio (cecilijohn - 300485264)
 */
public class RendererPanel extends JComponent {

    public int[][] board = new int[100][100];
    public RenderTile[][] tileMap = new RenderTile[30][30];
    public RenderItem[][] itemMap = new RenderItem[30][30];
    public Tile[][] levelTiles;
    public int xPos, yPos;
    public int frameWidth, frameHeight;

    public BufferedImage chip = null, redKey = null, greenKey = null;
    public BufferedImage wall = null, gDoor = null, rDoor = null, floor = null, unlocked = null;
    Image win;

    // ALL SOUND EFFECTS GENERATED/ EDITED/ PRODUCED USING THE WEBSITE:
    // https://jfxr.frozenfractal.com/
    SoundEffect moveSound, blockedSound, pickupSound, doorSound, winSound;

    // 0 - North
    // 1 - East
    // 2 - South
    // 3 - West
    int direction = 2;

    Game game;

    Image north, south, east, west;
    BufferedImage key;

    public RendererPanel(Game g) {

        // Load in all item images one time during construction.
        try {
            chip = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/chip.png"));
            greenKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/greenKey.png"));
            redKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/redKey.png"));
        } catch (IOException e) {
            System.out.println("Item image not found!");
        }

        // Load in all tile images one time during construction.
        try {
            wall = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/wall.png"));
            gDoor = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/greenDoor.png"));
            rDoor = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/redDoor.png"));
            floor = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/floor.png"));
            unlocked = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/unlocked.png"));
            // winTile different because it's a gif
            win = new ImageIcon(getClass().getResource("data/winTile.gif")).getImage();
        } catch (IOException e) {
            System.out.println("Tile image not found!");
        }

        frameHeight = (906 /2) - 50;
        frameWidth = (940 /2) - 50;

        game = g;
        levelTiles = game.getMap();

        // Print out formatted level map
        for (int i = 0; i < levelTiles.length; i++) {
            System.out.println();
            for (int j = 0; j < levelTiles[0].length; j++) {
                //System.out.print(" ");
                //System.out.print(levelTiles[i][j]);
                if (levelTiles[i][j] instanceof floorTile) System.out.print("???");
                else if (levelTiles[i][j] instanceof wallTile) System.out.print("0");
                else if (levelTiles[i][j] instanceof doorTile) System.out.print("K");
                else if (levelTiles[i][j] instanceof winTile) System.out.print("W");
            }
        }

        // Initialize character facing GIFS
        north = new ImageIcon(getClass().getResource("resource/backFacing.gif")).getImage();
        south = new ImageIcon(getClass().getResource("resource/frontFacing.gif")).getImage();
        east = new ImageIcon(getClass().getResource("resource/rightFacing.gif")).getImage();
        west = new ImageIcon(getClass().getResource("resource/leftFacing.gif")).getImage();

        updateRenderMaps();

        // Initialise sound effects
        moveSound = new SoundEffect();
        moveSound.setFile("src/nz/ac/vuw/ecs/swen225/gp20/render/sounds/movesound.wav");
        pickupSound = new SoundEffect();
        pickupSound.setFile("src/nz/ac/vuw/ecs/swen225/gp20/render/sounds/pickup.wav");
        blockedSound = new SoundEffect();
        blockedSound.setFile("src/nz/ac/vuw/ecs/swen225/gp20/render/sounds/stuck.wav");
        doorSound = new SoundEffect();
        doorSound.setFile("src/nz/ac/vuw/ecs/swen225/gp20/render/sounds/openDoor.wav");
        winSound = new SoundEffect();
        winSound.setFile("src/nz/ac/vuw/ecs/swen225/gp20/render/sounds/endTeleport.wav");
    }

    /**
     * method to call when loading in new level, to update the level data
     * @param t The 2D Array to update level data loaded
     */
    public void updateLevel(Tile[][] t) {
        levelTiles = t;
    }

    /**
     * This method converts the obtained level data from Game, into appropriate 2D arrays of the render objects.
     * Make render map based on tiles obtained from Game
     * Make item render map based on floorTiles that  contain items
     */
    public void updateRenderMaps() {
        RenderTile tile = null;
        RenderItem item = null;

        xPos = game.getPlayer().getCol();
        yPos = game.getPlayer().getRow();

        for (int i = 0; i < levelTiles.length; i++) {
            for (int j = 0; j < levelTiles[0].length; j++) {
                if (levelTiles[i][j] instanceof floorTile) {
                    tile = new Floor(i, j, floor);
                    floorTile f = (floorTile)levelTiles[i][j];
                    if (f.getItem() instanceof Key) {
                        if (((Key) f.getItem()).getColor().equals("R")) {
                            item = new RedKey(i, j, redKey);
                        } else if (((Key) f.getItem()).getColor().equals("G")) {
                            item = new GreenKey(i, j, greenKey);
                        }
                        itemMap[i][j] = item;
                    } else if (f.getItem() instanceof Treasure) {
                        item = new Chip(i, j, chip);
                        itemMap[i][j] = item;
                    }
                } else if (levelTiles[i][j] instanceof wallTile) {
                    tile = new Wall(i, j, wall);
                } else if (levelTiles[i][j] instanceof doorTile) {
                    doorTile d = (doorTile)levelTiles[i][j];
                    if (d.getColor().equals("R")) tile = new RedDoor(i, j, rDoor, unlocked);
                    else if (d.getColor().equals("G")) tile = new GreenDoor(i, j, gDoor, unlocked);
                } else if (levelTiles[i][j] instanceof winTile) {
                    tile = new ExitTile(i, j, win, this);
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
                if (itemMap[yPos + i][xPos + j] != null) {
                    itemMap[yPos + i][xPos + j].drawItem(g,frameWidth + (j * 100), frameHeight + (i * 100));
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
        // Establish temporary position variables to check for lack of movement
        // (In event of invalid movement)
        int xTemp = xPos;
        int yTemp = yPos;

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

        // Various events occurring after movement
        if (itemMap[yPos][xPos] != null) {
            // Picked up item
            itemMap[yPos][xPos] = null;
            pickupSound.playSound();
        } else if ((tileMap[yPos][xPos] instanceof GreenDoor || tileMap[yPos][xPos] instanceof RedDoor) && !tileMap[yPos][xPos].open) {
            // Unlocked door
            tileMap[yPos][xPos].setOpen();
            doorSound.playSound();
        } else if (yTemp == yPos && xTemp == xPos) {
            // Player did not move due to invalid movement
            blockedSound.playSound();
        } else {
            // No event (Normal movement)
            moveSound.playSound();
        }

        // Todo: Temporary placeholder for executing set up for next level
        // Todo: STANDARD MIGHT DO THIS METHOD CALL HERSELF, FROM APPLICATION
        if (tileMap[yPos][xPos] instanceof ExitTile) {
            this.winLevel();
        }

        this.repaint();
    }

    /**
     * The process of when the player reaches the winTile
     * This method will set up and load in the next level
     */
    public void winLevel() {
        winSound.playSound();
        //updateLevel(game.getMap()); // Todo: need to do actual next level loading
        //updateRenderMaps();
        this.repaint();
    }
}
