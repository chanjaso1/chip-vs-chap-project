package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class Pane {

    public int[][] board = new int[100][100];
    public Tile[][] tileMap = new Tile[100][100];
    int xPos, yPos;
    //BufferedImage player =

    public Pane() {
        int num;
        // Initialize 2D Array with random SHIT.
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                num = ThreadLocalRandom.current().nextInt(0, 3 + 1);
                board[i][j] = num;
            }
        }

        // Convert int 2D array into tileMap 2D Array
        int tileType;
        Tile tile = null;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                tileType = board[i][j];
                switch (tileType) {
                    case 0:
                        tile = new Blue(i, j);
                        break;
                    case 1:
                        tile = new Red(i, j);
                        break;
                    case 2:
                        tile = new Grey(i, j);
                        break;
                    case 3:
                        tile = new Yellow(i, j);
                        break;
                }
                tileMap[i][j] = tile;
            }
        }

        JFrame frame = new JFrame("Tile Map Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        xPos = 25;
        yPos = 25;

       JComponent drawBoard = new JComponent() {
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
                renderTiles(g2d);
                // if greater than 0-24 or 0-25 don't accept.
            }
        };

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

                if(keyEvent.getKeyCode()== KeyEvent.VK_RIGHT) {
                    if (xPos > 40) System.out.println("Boundary Reached");
                    else {
                        System.out.println("Right");
                        xPos++;
                        System.out.println(xPos + " " + yPos);
                    }
                }
                else if(keyEvent.getKeyCode()== KeyEvent.VK_LEFT) {
                    if (xPos < 10) System.out.println("Boundary Reached");
                    else {
                        System.out.println("Left");
                        xPos--;
                        System.out.println(xPos + " " + yPos);
                    }
                }
                else if(keyEvent.getKeyCode()== KeyEvent.VK_DOWN) {
                    if (yPos > 40) System.out.println("Boundary Reached");
                    else {
                        System.out.println("Down");
                        yPos++;
                        System.out.println(xPos + " " + yPos);
                    }
                }
                else if(keyEvent.getKeyCode()== KeyEvent.VK_UP) {
                    if (yPos < 10) System.out.println("Boundary Reached");
                    else {
                        System.out.println("Up");
                        yPos--;
                        System.out.println(xPos + " " + yPos);
                    }
                }

                drawBoard.repaint();
            }
        });

       frame.add(drawBoard);
       frame.setVisible(true);

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
        tileMap[y + yPos][xPos].drawTile(g, 225, 225 + (y * 50));

        for (int i = 1; i < 5; i++) {
            tileMap[y + yPos][xPos - i].drawTile(g, 225 - (i * 50), 225 + (y * 50));
            tileMap[y + yPos][xPos + i].drawTile(g, 225 + (i * 50), 225 + (y * 50));
        }
    }



    public static void main(String... args) {
        new Pane();
    }
}
