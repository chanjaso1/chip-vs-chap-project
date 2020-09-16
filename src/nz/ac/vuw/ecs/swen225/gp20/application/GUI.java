package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

/**
 * This class constructs and displays the GUI for the game.
 * The GUI displays the time left to play, the current level, keys collected
 * and the number of treasures that still need to be connected.
 * The player can interact with the GUI through keystrokes.
 */
public abstract class GUI {

    private JFrame frame;


    public GUI() {
        initialise();
        System.out.println("in constructor");
    }


    /**
     * Initialises and displays the GUI on the screen.
     */
    public void initialise() {

        // creates main frame
        frame = new JFrame("Chip's Challenge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 850);


        // creates menu bar components
        JMenuBar menuBar = new JMenuBar();

        JMenu saveButton = new JMenu("SAVE");
        JMenu pauseButton = new JMenu("PAUSE");
        JMenu resumeButton = new JMenu("RESUME");
        JMenu replayButton = new JMenu("REPLAY");
        JMenu helpButton = new JMenu("HELP");
        JMenu exitButton = new JMenu("EXIT");


        // adds components to menu bar
        menuBar.add(saveButton);
        menuBar.add(pauseButton);
        menuBar.add(resumeButton);
        menuBar.add(replayButton);
        menuBar.add(helpButton);
        menuBar.add(exitButton);

        // creates board panel
        JPanel bodyPanel = new JPanel(new GridLayout(1, 2));

        // creates board panel
        JComponent board = new JPanel();    //TODO: replace with standard's
        board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));
        board.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        board.setBackground(Color.GRAY);
        board.add(new JButton("board"));


        // creates game stats panel
        JPanel gameStats = new JPanel();
        gameStats.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        gameStats.setBackground(Color.darkGray);
        gameStats.add(new JButton("game stats"));

        bodyPanel.add(board);
        bodyPanel.add(gameStats);


        // add all components to the main frame
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, bodyPanel);
//        frame.getContentPane().add(BorderLayout.EAST, gameStats);
        frame.setVisible(true);
        System.out.println("here");


    }

}

class Dummy extends GUI {


    public static void main(String... args) {
        new Dummy();
    }
}

