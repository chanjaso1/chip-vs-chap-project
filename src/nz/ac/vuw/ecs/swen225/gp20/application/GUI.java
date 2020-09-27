package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.render.RendererPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * This class constructs and displays the GUI for the game.
 * The GUI displays the time left to play, the current level, keys collected
 * and the number of treasures that still need to be connected.
 * The player can interact with the GUI through keystrokes.
 */
public abstract class GUI {

    private final Font TITLE_FONT = new Font("", Font.BOLD, 30);
    private final GridLayout GAME_STATS_LAYOUT = new GridLayout(2, 1);
    private final Border GAME_STATS_BORDER = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    private final double MAX_TIME = 5; // TODO: replace values with actual values from Jason's file

    private JFrame frame, replayFrame = new JFrame();
    private JPanel gameStatsPanel;
    private Dimension screenSize;
    private InputMap inputMap;
    private ActionMap actionMap;


    private double replaySpeed;
    private double currentTime = MAX_TIME;
    private int keysLeft = 2;
    private boolean roundFinished;


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
        frame.setLocationRelativeTo(null);

        // creates, binds and responds to keystrokes
        JLabel keystrokes = addKeyStrokes();
        frame.add(keystrokes);

        // creates menu bar and components
        JMenuBar menuBar = new JMenuBar();

        // SAVE button
        JMenuItem saveButton = new JMenu("SAVE");
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: ADD TIAN'S SAVING CODE
                String message = "Your game has successfully been saved.";
                JOptionPane.showMessageDialog(frame, message, "SAVE", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // PAUSE button
        JMenuItem pauseButton = new JMenu("PAUSE");
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayPauseFrame();
                //TODO: ADD PAUSE CODE

            }
        });

        // RESUME button
        JMenuItem resumeButton = new JMenu("RESUME");
        resumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayResumeFrame();
                //TODO: ADD RESUME CODE
            }
        });

        // REPLAY button
        JMenuItem replayButton = new JMenu("REPLAY");
        replayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("calling from menu");
                displayReplayFrame();
            }
        });

        // HELP button
        JMenuItem helpButton = new JMenu("HELP");
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

        // EXIT button
        JMenuItem exitButton = new JMenu("EXIT");
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayExitFrame();
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
//        JComponent board = displayBoardPanel();    //TODO: replace with standard's
        JComponent board = new RendererPanel();


//        board.setBorder(border);

        // creates game stats panel
        gameStatsPanel = new JPanel(new GridLayout(5, 1, 10, 30));
        displayGameStatsPanel(gameStatsPanel);

        // creates panel containing user controls
        JPanel controlsPanel = displayControlsPanel();

        // timer
        createTimer();

        // add all components to the main frame
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, board);
        frame.getContentPane().add(BorderLayout.EAST, gameStatsPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, controlsPanel);
        frame.setVisible(true);

    }

    /**
     * Helper method which adds all the keystroke functionality to the GUI.
     * Keystrokes are CTRL-X (exit), CTRL-S (save), CTRL-R (resume), CTRL-P (new game),
     * CTRL-1 (new game at level 1), SPACE (pause), ESC (escape pause and resume game)
     * as well as the four arrow keys which move Chap around the board.
     *
     * @return JLabel containing the keystroke functionality for the game.
     */
    public JLabel addKeyStrokes() {
        JLabel keystrokes = new JLabel("");
        inputMap = keystrokes.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        actionMap = keystrokes.getActionMap();

        // UP
        inputMap.put(KeyStroke.getKeyStroke("UP"), "MOVE_UP");
        actionMap.put("MOVE_UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("up works");
            }
        });

        // DOWN
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "MOVE_DOWN");
        actionMap.put("MOVE_DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("down works");
            }
        });

        // LEFT
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "MOVE_LEFT");
        actionMap.put("MOVE_LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("left works");
            }
        });

        // RIGHT
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "MOVE_RIGHT");
        actionMap.put("MOVE_RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("right works");
            }
        });

        // CTRL-X (exit)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.META_DOWN_MASK), "EXIT");
        actionMap.put("EXIT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayExitFrame();
            }
        });

        // CTRL-S (save)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_DOWN_MASK), "SAVE");
        actionMap.put("SAVE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("save works");
            }
        });

        // CTRL-R (resume a saved game)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.META_DOWN_MASK), "RESUME");
        actionMap.put("RESUME", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("RESUME works");
            }
        });

        // CTRL-P (start new game at last unfinished level)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.META_DOWN_MASK), "NEW_GAME");
        actionMap.put("NEW_GAME", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("new game at unfinished");
            }
        });

        // CTRL-1 (start new game at level 1)
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.META_DOWN_MASK), "NEW_GAME_1");
        actionMap.put("NEW_GAME_1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("level 1");
            }
        });

        // SPACE (pauses game)
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "PAUSE");
        actionMap.put("PAUSE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pause works");
            }
        });

        // ESC (escapes pause and resumes game)
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "ESCAPE");
        actionMap.put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ESCAPE works");
            }
        });

        return keystrokes;
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
        board.setBackground(new Color(28, 173, 81));

        return board;

    }

    /**
     * Displays the game statistics panel on the RHS.
     * This includes displaying the current level, time, chips left and inventory.
     */
    public void displayGameStatsPanel(JPanel gameStats) {
//        gameStats = new JPanel(new GridLayout(5, 1, 10, 30));
        gameStats.setBorder(BorderFactory.createEmptyBorder(50, 100, 200, 100));
//        gameStats.setBackground(Color.GRAY);


        // level panel
        JPanel levelPanel = createGameStat("LEVEL", "1");
      /*  JPanel levelPanel = new JPanel(layout);
        JLabel levelTitle = new JLabel("LEVEL", JLabel.CENTER);
        levelTitle.setFont(TITLE_FONT);
        levelTitle.setForeground(Color.RED);

        JLabel level = new JLabel("1", JLabel.CENTER);
        formatStat(level, border);

        levelPanel.add(levelTitle);
        levelPanel.add(level); */


        // time panel
        JPanel timePanel = displayTimePanel();


      /*  JPanel timePanel = new JPanel(layout);
        JLabel timeTitle = new JLabel("TIME", JLabel.CENTER);
        timeTitle.setFont(TITLE_FONT);
        timeTitle.setForeground(Color.RED);

        JLabel time = new JLabel("60", JLabel.CENTER);
        formatStat(time, border);

        timePanel.add(timeTitle);
        timePanel.add(time); */

        // keys left panel
        JPanel keysLeftPanel = createGameStat("CHIPS\nLEFT", "2");
      /*  JPanel keysLeftPanel = new JPanel(layout);
        JLabel keysLeftTitle = new JLabel("CHIPS\nLEFT", JLabel.CENTER);
        keysLeftTitle.setFont(TITLE_FONT);
        keysLeftTitle.setForeground(Color.RED);

        JLabel chips = new JLabel("2", JLabel.CENTER);
        formatStat(chips, border);

        keysLeftPanel.add(keysLeftTitle);
        keysLeftPanel.add(chips); */

        // inventory (keys collected) panel
        JPanel inventoryPanel = new JPanel(GAME_STATS_LAYOUT);
        JLabel inventoryTitle = new JLabel("INVENTORY", JLabel.CENTER);
        inventoryTitle.setFont(TITLE_FONT);
        inventoryTitle.setForeground(Color.RED);

        JLabel inventory = new JLabel("*key*", JLabel.CENTER);
        inventory.setBorder(GAME_STATS_BORDER);
        inventory.setFont(TITLE_FONT);
        inventory.setOpaque(true);
        inventory.setBackground(Color.darkGray);
        inventory.setForeground(Color.WHITE);

        inventoryPanel.add(inventoryTitle);
        inventoryPanel.add(inventory);


        // add all components to frame
        gameStats.add(levelPanel);
        gameStats.add(timePanel);
        gameStats.add(keysLeftPanel);
        gameStats.add(inventoryPanel);

    }

    /**
     * Displays the current time the user has to complete the round.
     *
     * @return the JPanel containing a title and current time.
     */
    public JPanel displayTimePanel() {
        JPanel newTimePanel = new JPanel(GAME_STATS_LAYOUT);
        JLabel timeTitle = new JLabel("TIME", JLabel.CENTER);
        timeTitle.setFont(TITLE_FONT);
        timeTitle.setForeground(Color.RED);

        JLabel time = new JLabel(Double.toString(currentTime), JLabel.CENTER);
        time.setFont(TITLE_FONT);
        time.setOpaque(true);
        time.setBackground(Color.darkGray);
        time.setForeground(Color.WHITE);
        time.setBorder(GAME_STATS_BORDER);

        newTimePanel.add(timeTitle);
        newTimePanel.add(time);

//        JOptionPane.showMessageDialog(null, newTimePanel);

        return newTimePanel;
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
                displayPauseFrame();
            }
        });

        // RESUME button
        JButton resumeButton = new JButton("RESUME");
        resumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: ADD RESUME CODE
                displayResumeFrame();
            }
        });

        // REPLAY button
        JButton replayButton = new JButton("REPLAY");
        replayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
        replayFrame.setLocation((screenSize.width / 2 - replayFrame.getWidth() / 2) + 385, (screenSize.height / 2 - replayFrame.getHeight() / 2) + 220);

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
                JOptionPane.showMessageDialog(replayFrame, message, "STEP-BY-STEP REPLAY", JOptionPane.INFORMATION_MESSAGE);

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

    /**
     * Displays the pause frame which indicates to user the game is paused.
     */
    public void displayPauseFrame() {
        String message = "The game is paused.";
        JOptionPane.showMessageDialog(frame, message, "PAUSE", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Displays the resume frame which indicates to user the game is about to resume.
     */
    public void displayResumeFrame() {
        String message = "The game will resume once you click 'OK'.";
        JOptionPane.showMessageDialog(frame, message, "RESUME", JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Displays the exit frame which checks if user wants to exit game.
     * If yes, the game exits without saving user's progress.
     */
    public void displayExitFrame() {
        String message = "You are about to exit the the game...\n" +
                "If you exit the game, your progress will not be saved.\n" +
                "The next time the game is started, it will resume from the last unfinished level.\n" +
                "Are you sure you want to exit the game?";
        if (JOptionPane.showConfirmDialog(frame, message, "EXIT GAME", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * A helper method which creates and formats the JPanel containing a game statistic.
     * Used to display the current level and keys left in the game.
     *
     * @param name     -- name of the statistic (eg. "LEVEL");
     * @param gameStat -- the current value for this statistic (eg. "1").
     * @return completed JPanel displaying game statistic.
     */
    public JPanel createGameStat(String name, String gameStat) {
        // title
        JPanel panel = new JPanel(GAME_STATS_LAYOUT);
        JLabel title = new JLabel(name, JLabel.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(Color.RED);

        // game stat
        JLabel level = new JLabel(gameStat, JLabel.CENTER);
        level.setBorder(GAME_STATS_BORDER);
        level.setFont(TITLE_FONT);
        level.setOpaque(true);
        level.setBackground(Color.darkGray);
        level.setForeground(Color.WHITE);

        panel.add(title);
        panel.add(level);

        return panel;

    }


    /**
     * Creates and executes the timer for each round.
     * Player wins a round if they succeed in collecting all the keys within the time limit.
     * Otherwise, the player has failed and loses the game.
     */
    public void createTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //                if (roundFinished) {
//                    roundFinished = false;
//                    return;
//                }
                if (currentTime == 0) return;   // remove when code is added to reset level
                currentTime--;
                redisplayTimer();

                // end of round (user lost)
                if (currentTime <= 0 && keysLeft != 0) { // TODO: add condition to check if user has won/lost round
                    String message = "You didn't collect the keys in time... GAME OVER!\nClick 'OK' to reset the game and restart the level";
                    JOptionPane.showMessageDialog(frame, message, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
                    // TODO: add code which restarts level
                    currentTime = 0;
                    roundFinished = true;
                }
            }
        });
        timer.start();
    }

    /**
     * Updates and re-displays the timer on the screen.
     */
    public void redisplayTimer() {
        gameStatsPanel.removeAll();
        displayGameStatsPanel(gameStatsPanel);
        gameStatsPanel.revalidate();
    }


    /**
     * Gets the current replay speed selected by user.
     *
     * @return the current replay speed.
     */
    public double getReplaySpeed() {
        return replaySpeed;
    }


}


//class Dummy extends GUI {
//
//
//    public static void main(String... args) {
//        new Dummy();
//    }
//}

