package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * This class constructs and displays the GUI for the game.
 * The GUI displays the time left to play, the current level, keys collected
 * and the number of treasures that still need to be connected.
 * The player can interact with the GUI through keystrokes.
 */
public abstract class GUI {

    private JFrame frame;
    private JFrame replayFrame = new JFrame();     // displays replay controls
    private Dimension screenSize;

    private double replaySpeed;


    public GUI() {
        initialise();
    }


    /**
     * Initialises and displays the GUI on the screen.
     */
    public void initialise() {

        // creates main frame
        frame = new JFrame("Chip's Challenge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 850);

        // creates menu bar and components
        JMenuBar menuBar = new JMenuBar();

        // SAVE button
        JMenu saveButton = new JMenu("SAVE");
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: ADD TIAN'S SAVING CODE
                String message = "Your game has successfully been saved.";
                JOptionPane.showMessageDialog(frame, message, "SAVE", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // pause button
        JMenu pauseButton = new JMenu("PAUSE");
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: ADD PAUSE CODE
                String message = "The game is paused.";
                JOptionPane.showMessageDialog(frame, message, "PAUSE", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // resume button
        JMenu resumeButton = new JMenu("RESUME");
        resumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: ADD RESUME CODE
                String message = "The game will resume once you click 'OK'.";
                JOptionPane.showMessageDialog(frame, message, "RESUME", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // replay button
        JMenu replayButton = new JMenu("REPLAY");
        replayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("calling from menu");
                displayReplayFrame();
            }
        });

        // help button
        JMenu helpButton = new JMenu("HELP");
        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String message = "HOW TO PLAY\n\n" +
                        "LEFT PANEL\n" +
                        "- Buttons are used to perform actions in the game.\n" +
                        "- The current player's name is displayed with a GREEN border. Eliminated players are displayed with a RED border.\n\n" +
                        "BOTTOM PANEL\n" +
                        "- Cards with GREEN borders indicate the player's deck of cards as well as cards other players have refuted to them throughout the game.\n- Refuted cards will be displayed on player's next turn.\n\n" +
                        "GAME ORDER\n\n" + "1. Dice is rolled and shown next to the current player's name (in the bottom panel).\n" +
                        "Green tiles on the board show the tiles/rooms you can reach. Reachable rooms will have a green centre tile.\n" +
                        "You must move your character token before you perform any action.\n\n" +
                        "2. If you moved into a room you have to suggest. You cannot suggest outside a room.\n" +
                        "You can accuse anywhere on the board. You can only either suggest or accuse once in your turn (ie. cannot make multiple suggestions/accusations).\n" +
                        "However, if you suggest and no card is refuted you can then accuse.\n\n" +
                        "3. If your accusation is wrong you will be eliminated from the game but you can still refute cards to other players.\n\n" +
                        "4. The END TURN button MUST be pressed in order to end your turn.\n";
                JOptionPane.showMessageDialog(frame, message, "HELP", JOptionPane.PLAIN_MESSAGE);
            }
        });

        // exit button
        JMenu exitButton = new JMenu("EXIT");
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String message = "If you exit the game, your progress will not be saved.\n " +
                        "The next time the game is started, it will resume from the last unfinished level.\n" +
                        "Are you sure you want to exit the game?";
                if (JOptionPane.showConfirmDialog(frame, message, "EXIT GAME", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // adds components to menu bar
        menuBar.add(saveButton);
        menuBar.add(pauseButton);
        menuBar.add(resumeButton);
        menuBar.add(replayButton);
        menuBar.add(helpButton);
        menuBar.add(exitButton);

        // creates board panel
        JPanel bodyPanel = new JPanel(new GridLayout(1, 2));

        Border border = BorderFactory.createEmptyBorder(50, 60, 50, 60);


        // creates board panel
        JComponent board = displayBoardPanel();    //TODO: replace with standard's
//        board.setBorder(border);


        // creates game stats panel
        JPanel gameStats = displayGameStatsPanel();
//        gameStats.setBorder(border);

        // creates panel containing user controls
        JPanel controlsPanel = displayControlsPanel();


//        bodyPanel.add(board);
//        bodyPanel.add(gameStats);


        // add all components to the main frame
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, board);
        frame.getContentPane().add(BorderLayout.EAST, gameStats);
        frame.getContentPane().add(BorderLayout.SOUTH, controlsPanel);

        frame.setVisible(true);

    }


    /**
     * Displays the board on the LHS.
     *
     * @return Center JPanel displaying board game.
     */
    public JPanel displayBoardPanel() {
        JPanel board = new JPanel();

        board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));
        board.setBorder(BorderFactory.createEmptyBorder(100, 60, 10, 10));
        board.setBackground(Color.GRAY);

        return board;

    }

    /**
     * Displays the game statistics panel on the RHS.
     * This includes displaying the current level, time, chips left and inventory.
     *
     * @return RHS JPanel displaying game statistics.
     */
    public JPanel displayGameStatsPanel() {
        JPanel gameStats = new JPanel(new GridLayout(5, 1, 10, 30));
        gameStats.setBorder(BorderFactory.createEmptyBorder(50, 100, 200, 100));
//        gameStats.setBackground(Color.GRAY);

        GridLayout layout = new GridLayout(2, 1);
        Font font = new Font("", Font.BOLD, 30);
        Border border = BorderFactory.createEmptyBorder(20, 20, 20, 20);

        // level panel
        JPanel levelPanel = new JPanel(layout);
        JLabel levelTitle = new JLabel("LEVEL", JLabel.CENTER);
        levelTitle.setFont(font);
        levelTitle.setForeground(Color.RED);

        JLabel level = new JLabel("1", JLabel.CENTER);
        displayTitle(level, border, font);


        levelPanel.add(levelTitle);
        levelPanel.add(level);

        // time panel
        JPanel timePanel = new JPanel(layout);
        JLabel timeTitle = new JLabel("TIME", JLabel.CENTER);
        timeTitle.setFont(font);
        timeTitle.setForeground(Color.RED);

        JLabel time = new JLabel("60", JLabel.CENTER);
        displayTitle(time, border, font);

        timePanel.add(timeTitle);
        timePanel.add(time);

        // keys left panel
        JPanel keysLeftPanel = new JPanel(layout);
        JLabel keysLeftTitle = new JLabel("CHIPS\nLEFT", JLabel.CENTER);
        keysLeftTitle.setFont(font);
        keysLeftTitle.setForeground(Color.RED);

        JLabel chips = new JLabel("2", JLabel.CENTER);
        displayTitle(chips, border, font);

        keysLeftPanel.add(keysLeftTitle);
        keysLeftPanel.add(chips);

        // inventory (keys collected) panel
        JPanel inventoryPanel = new JPanel(layout);
        JLabel inventoryTitle = new JLabel("INVENTORY", JLabel.CENTER);
        inventoryTitle.setFont(font);
        inventoryTitle.setForeground(Color.RED);

        JLabel inventory = new JLabel("*key*", JLabel.CENTER);
        inventory.setBorder(border);
        inventory.setFont(font);
        inventory.setOpaque(true);
        inventory.setBackground(Color.darkGray);
        inventory.setForeground(Color.WHITE);

        inventoryPanel.add(inventoryTitle);
        inventoryPanel.add(inventory);


        gameStats.add(levelPanel);
        gameStats.add(timePanel);
        gameStats.add(keysLeftPanel);
        gameStats.add(inventoryPanel);

        return gameStats;

    }

    /**
     * A helper method which displays the title for each game stat on the RHS of the GUI.
     */
    public void displayTitle(JLabel title, Border border, Font font) {
        title.setBorder(border);
        title.setFont(font);
        title.setOpaque(true);
        title.setBackground(Color.darkGray);
        title.setForeground(Color.WHITE);
    }


    /**
     * Displays the user controls panel located at the bottom of the screen.
     * This includes the buttons "PAUSE", "RESUME", "REPLAY".
     *
     * @return bottom JPanel displaying user controls.
     */
    public JPanel displayControlsPanel() {

        JPanel controlPanel = new JPanel(new GridLayout(1, 7));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        controlPanel.setBackground(Color.WHITE);

        // title
        JLabel replayTitle = new JLabel("USER CONTROLS:");
        replayTitle.setFont(new Font("", Font.BOLD, 15));

        // PAUSE button
        JButton pauseButton = new JButton("PAUSE");
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: ADD PAUSE CODE
                String message = "The game is paused.";
                JOptionPane.showMessageDialog(frame, message, "PAUSE", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // RESUME button
        JButton resumeButton = new JButton("RESUME");
        resumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: ADD RESUME CODE
                String message = "The game will resume once you click 'OK'.";
                JOptionPane.showMessageDialog(frame, message, "RESUME", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // REPLAY button
        JButton replayButton = new JButton("REPLAY");
        replayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                JFrame replayControls = new JFrame("REPLAY CONTROLS");

                displayReplayFrame();
            }
        });


        controlPanel.add(replayTitle);
        controlPanel.add(pauseButton);
        controlPanel.add(resumeButton);
        controlPanel.add(replayButton);
        for (int i = 0; i <= 4; i++)        // formats buttons
            controlPanel.add(new JLabel(""));


        return controlPanel;

    }

    /**
     * Displays a frame containing all the replay actions the user can select.
     * These actions include "step-by-step", "auto-replay" (replays from beginning) and "set-replay speed".
     * The first two modes are displayed as JButtons.
     * The replay speed is displayed as a JComboBox so users can select a speed within the specified range.
     */
    public void displayReplayFrame() {
        // formats frame
        replayFrame = new JFrame("REPLAY CONTROLS");
        replayFrame.setSize(600, 270);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        replayFrame.setLocation((screenSize.width / 2 - replayFrame.getWidth() / 2) + 230, (screenSize.height / 2 - replayFrame.getHeight() / 2) +170);

        // title and text description
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new GridLayout(2, 1, 10, 10));
        JLabel replayTitle = new JLabel("REPLAY", SwingConstants.CENTER);
        replayTitle.setFont(new Font("", Font.BOLD, 30));
        JLabel text = new JLabel("Select a replay speed and mode to view your progress.", SwingConstants.CENTER);
        descriptionPanel.add(replayTitle);
        descriptionPanel.add(text);

        // actions panel
        JPanel actions = new JPanel();

        // REPLAY SPEED button
        String[] options = {"0.5", "1", "1.5", "2"};
        JComboBox<String> combobox = new JComboBox<>(options);
        combobox.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel speed = new JPanel();
        speed.add(new JLabel("Replay speed:"));
        speed.add(combobox);

        // STEP-BY-STEP button
        JButton stepButton = new JButton("STEP-BY-STEP REPLAY");
        stepButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: ADD STEP-BY-STEP CODE

                replaySpeed = Double.parseDouble((String) combobox.getSelectedItem());
                String message = "Selected mode: STEP-BY-STEP\nSeleced replay speed: " + replaySpeed;
                JOptionPane.showMessageDialog(replayFrame, message, "STEP-BY-STEP", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        // AUTO-REPLAY button
        JButton autoButton = new JButton("AUTO-REPLAY");
        autoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: ADD AUTO-REPLAY CODE
                replaySpeed = Double.parseDouble((String) combobox.getSelectedItem());
                String message = "Selected replay mode: AUTO-REPLAY\nSeleced replay speed: " + replaySpeed;
                JOptionPane.showMessageDialog(replayFrame, message, "AUTO-REPLAY", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        actions.add(speed);
        actions.add(stepButton);
        actions.add(autoButton);

        // exit panel
        JPanel exitPanel = new JPanel();
        JButton exitReplay = new JButton("Back to Game");
        exitReplay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String message = "Exited Replay mode. Click 'OK' to return to game.";
                JOptionPane.showMessageDialog(frame, message, "BACK TO GAME", JOptionPane.INFORMATION_MESSAGE);
                // TODO: make sure replay mode is exited correctly and game is resumed
                replayFrame.setVisible(false);
            }
        });
        exitPanel.add(exitReplay);


        replayFrame.getContentPane().add(BorderLayout.NORTH, descriptionPanel);
        replayFrame.getContentPane().add(BorderLayout.CENTER, actions);
        replayFrame.getContentPane().add(BorderLayout.SOUTH, exitPanel);
        replayFrame.setVisible(true);
    }

}


class Dummy extends GUI {


    public static void main(String... args) {
        new Dummy();
    }
}

