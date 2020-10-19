package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class Pane {

    public Pane() {

        JFrame frame = new JFrame("Tile Map Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);

       RendererPanel drawBoard = new RendererPanel(new Game());

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
