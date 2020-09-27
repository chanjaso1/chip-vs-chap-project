package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;


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

    public RendererPanel() {
        frameHeight = (1000 /2) - 50;
        frameWidth = (1000 /2) - 50;

        // BREAKPOINT HERE - Instantiate game, and then invoking method to access map state.
        game = new Game();
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

        xPos = 11;
        yPos = 13;

        // Initialize character facing GIFS
        north = new ImageIcon(getClass().getResource("resource/backFacing.gif")).getImage();
        south = new ImageIcon(getClass().getResource("resource/frontFacing.gif")).getImage();
        east = new ImageIcon(getClass().getResource("resource/rightFacing.gif")).getImage();
        west = new ImageIcon(getClass().getResource("resource/leftFacing.gif")).getImage();

//        int num;
//        // Initialize 2D Array with random SHIT.
//        for (int i = 0; i < 100; i++) {
//            for (int j = 0; j < 100; j++) {
//                num = ThreadLocalRandom.current().nextInt(0, 3 + 1);
//                board[i][j] = num;
//            }
//        }

        // Make render map based on tiles obtained from Game
        int tileType;
        RenderTile tile = null;

        for (int i = 0; i < levelTiles.length; i++) {
            System.out.println();
            for (int j = 0; j < levelTiles[0].length; j++) {
                //System.out.print(" ");
                //System.out.print(levelTiles[i][j]);
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

//        for (int i = 0; i < 100; i++) {
//            for (int j = 0; j < 100; j++) {
//                tileType = board[i][j];
//                switch (tileType) {
//                    case 0:
//                        tile = new Blue(i, j);
//                        break;
//                    case 1:
//                        tile = new Red(i, j);
//                        break;
//                    case 2:
//                        tile = new Grey(i, j);
//                        break;
//                    case 3:
//                        tile = new Yellow(i, j);
//                        break;
//                }
//                tileMap[i][j] = tile;
//            }
//        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        renderTiles(g2d);
        drawPlayer(g2d, this);
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

    public void renderMove(int dir) {
        if(dir == 1) {
            if (xPos > 40) System.out.println("Boundary Reached");
            else {
                System.out.println("Right");
                xPos++;
                System.out.println(xPos + " " + yPos);
                direction = 1;
            }
        }
        else if(dir == 3) {
            if (xPos < 10) System.out.println("Boundary Reached");
            else {
                System.out.println("Left");
                xPos--;
                System.out.println(xPos + " " + yPos);
                direction = 3;
            }
        }
        else if(dir == 2) {
            if (yPos > 40) System.out.println("Boundary Reached");
            else {
                System.out.println("Down");
                yPos++;
                System.out.println(xPos + " " + yPos);
                direction = 2;
            }
        }
        else if(dir == 0) {
            if (yPos < 10) System.out.println("Boundary Reached");
            else {
                System.out.println("Up");
                yPos--;
                System.out.println(xPos + " " + yPos);
                direction = 0;
            }
        }

        this.repaint();
    }
}
