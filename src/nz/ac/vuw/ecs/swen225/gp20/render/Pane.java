package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class Pane {

    Image north, south, east, west;

    public Pane() {

        // Initialize character facing GIFS
        north = new ImageIcon(getClass().getResource("resource/backFacing.gif")).getImage();
        south = new ImageIcon(getClass().getResource("resource/frontFacing.gif")).getImage();
        east = new ImageIcon(getClass().getResource("resource/rightFacing.gif")).getImage();
        west = new ImageIcon(getClass().getResource("resource/leftFacing.gif")).getImage();

        JFrame frame = new JFrame("Tile Map Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);

       RendererPanel drawBoard = new RendererPanel();

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
                    drawBoard.renderMove(1);
                }
                else if(keyEvent.getKeyCode()== KeyEvent.VK_LEFT) {
                    drawBoard.renderMove(3);
                }
                else if(keyEvent.getKeyCode()== KeyEvent.VK_DOWN) {
                    drawBoard.renderMove(2);
                }
                else if(keyEvent.getKeyCode()== KeyEvent.VK_UP) {
                    drawBoard.renderMove(0);
                }
            }
        });

       frame.add(drawBoard);
       frame.setVisible(true);

    }

    public static void main(String... args) {
        new Pane();
    }
}