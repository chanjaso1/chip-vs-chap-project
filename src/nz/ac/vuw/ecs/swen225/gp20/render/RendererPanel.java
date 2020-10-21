package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
    public int xBugLast, yBugLast;
    public int frameWidth, frameHeight;

    public BufferedImage chip = null, redKey = null, greenKey = null;
    public BufferedImage wall = null, gDoor = null, rDoor = null, floor = null, unlocked = null, iTile = null;
    public BufferedImage hitScreen = null;
    Image win, cDoor, cOpen, swarm;
    int cDoorX = -1, cDoorY = -1;

    // ALL SOUND EFFECTS GENERATED/ EDITED/ PRODUCED USING THE WEBSITE:
    // https://jfxr.frozenfractal.com/
    SoundEffect moveSound, blockedSound, pickupSound, doorSound, winSound, hitSound, bugSound;

    // 0 - North
    // 1 - East
    // 2 - South
    // 3 - West
    int direction = 2;
    boolean hit = false;

    Game game;

    Image north, south, east, west;
    BufferedImage key;

    public RendererPanel(Game g) {

        // Load in all item images one time during construction.
        try {
            chip = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/chip.png"));
            greenKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/greenKey.png"));
            redKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/redKey.png"));
            // Enemy/ Swarm/ Bug is different as it is a gif
            swarm = new ImageIcon(getClass().getResource("data/swarm.gif")).getImage();
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
            iTile = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/infoTile.png"));
            // These are different because it's a gif
            win = new ImageIcon(getClass().getResource("data/winTile.gif")).getImage();
            cDoor = new ImageIcon(getClass().getResource("data/chipDoor.gif")).getImage();
            cOpen = new ImageIcon(getClass().getResource("data/chipOpen.gif")).getImage();
            // As well as the hit screen img
            hitScreen = ImageIO.read(new File( "src/nz/ac/vuw/ecs/swen225/gp20/render/data/hitScreen.png"));
        } catch (IOException e) {
            System.out.println("Tile image not found!");
        }

        frameHeight = (906 /2) - 50;
        frameWidth = (940 /2) - 50;

        game = g;
        levelTiles = game.getMap();

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
        hitSound = new SoundEffect();
        hitSound.setFile("src/nz/ac/vuw/ecs/swen225/gp20/render/sounds/ouch.wav");
        bugSound = new SoundEffect();
        bugSound.setFile("src/nz/ac/vuw/ecs/swen225/gp20/render/sounds/bugs.wav");
    }

    public void printMap() {
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
                else if (levelTiles[i][j] instanceof treasureDoor) System.out.print("T");
            }
        }
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

        printMap();

        xPos = game.getPlayer().getCol();
        yPos = game.getPlayer().getRow();

        // Empty out RenderItems
        itemMap = new RenderItem[30][30];

        for (int i = 0; i < levelTiles.length; i++) {
            for (int j = 0; j < levelTiles[0].length; j++) {
                if (levelTiles[i][j] instanceof floorTile) {
                    tile = new FloorRender(i, j, floor);
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
                    tile = new WallRender(i, j, wall);
                } else if (levelTiles[i][j] instanceof doorTile) {
                    doorTile d = (doorTile)levelTiles[i][j];
                    if (d.getColor().equals("R")) tile = new RedDoorRender(i, j, rDoor, unlocked);
                    else if (d.getColor().equals("G")) tile = new GreenDoorRender(i, j, gDoor, unlocked);
                } else if (levelTiles[i][j] instanceof winTile) {
                    tile = new ExitTileRender(i, j, win, this);
                } else if (levelTiles[i][j] instanceof treasureDoor) {
                    tile = new ChipDoorRender(i, j, cDoor, cOpen, this);
                    cDoorY = i;
                    cDoorX = j;
                } else if (levelTiles[i][j] instanceof infoTile) {
                    tile = new InfoRender(i, j, iTile);
                }

                tileMap[i][j] = tile;

                int colB = -1;
                int rowB = -1;

                if(game.getBug() != null) {

                    try {
                        colB = (int) game.getParser().aClass.getMethod("getCol").invoke(game.getBug());
                        rowB = (int) game.getParser().aClass.getMethod("getRow").invoke(game.getBug());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    if (i == rowB && j == colB) {
                        yBugLast = i;
                        xBugLast = j;
                        item = new Enemy(i, j, swarm, this);
                        itemMap[i][j] = item;
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        renderTiles(g2d);
        drawItems(g2d);
        drawPlayer(g2d, this);
        if (hit) {
            g2d.drawImage(hitScreen, null, 0, 0);
        }
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
    public void drawItems(Graphics2D g) {
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
        hit = false;

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

        // Open chip doors once player has moved to pick up last treasure/ chip
        if (game.getPlayer().getNumberTreasures() == 0) {
            if (!tileMap[cDoorY][cDoorX].open) doorSound.playSound();
            tileMap[cDoorY][cDoorX].open = true;
        }

        // Various events occurring after movement
        if (itemMap[yPos][xPos] != null && !(itemMap[yPos][xPos] instanceof Enemy)) {
            // Picked up item
            itemMap[yPos][xPos] = null;
            pickupSound.playSound();
        } else if (itemMap[yPos][xPos] != null && (itemMap[yPos][xPos] instanceof Enemy)) {
            if (!hit) {
                // Prevents sound from spamming
                hitSound.playSound();
                bugSound.playSound();
            }
            hit = true;
        } else if ((tileMap[yPos][xPos] instanceof GreenDoorRender || tileMap[yPos][xPos] instanceof RedDoorRender) && !tileMap[yPos][xPos].open) {
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

        this.repaint();
    }

    /**
     * The process of when the player reaches the winTile
     * This method will set up and load in the next level
     */
    public void winLevel() {
        winSound.playSound();
        updateLevel(game.getMap());
        updateRenderMaps();
        this.repaint();
    }

    /**
     * Method for updating the render bug's location
     */
    public void moveBug() {

        int xBug = 0;
        int yBug = 0;
        try {
            xBug = (int) game.getParser().aClass.getMethod("getCol").invoke(game.getBug());
            yBug = (int) game.getParser().aClass.getMethod("getRow").invoke(game.getBug());
            System.out.println("Bug Pos " + xBug + " " + yBug);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // Move the bug from old pos to new pos
        itemMap[yBug][xBug] = itemMap[yBugLast][xBugLast];

        // Delete bug from old pos
        if (yBugLast != yBug) itemMap[yBugLast][xBugLast] = null;
        yBugLast = yBug;
        xBugLast = xBug;

        if (yBug == yPos && xBug == xPos) {
            if (!hit) {
                // Prevents sound from spamming
                hitSound.playSound();
                bugSound.playSound();
            }
            hit = true;
        }
        this.repaint();
    }

    public void playWinSound() {
        winSound.playSound();
    }
}
