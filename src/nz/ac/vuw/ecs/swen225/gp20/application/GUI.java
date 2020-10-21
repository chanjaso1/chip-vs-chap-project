package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;
import nz.ac.vuw.ecs.swen225.gp20.maze.Move;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.RecordReader;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.RecordSaver;
import nz.ac.vuw.ecs.swen225.gp20.render.RendererPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * This class constructs and displays the GUI for the game.
 * The GUI displays the time left to play, the current level, keys collected
 * and the number of treasures that still need to be connected.
 * The player can interact with the GUI through keystrokes.
 */
public class GUI {

    private final Font TITLE_FONT = new Font("", Font.BOLD, 30);
    private final GridLayout GAME_STATS_LAYOUT = new GridLayout(2, 1);
    private final Border GAME_STATS_BORDER = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    private final double MAX_TIME = 60; // TODO: replace values with actual values from Jason's file

    private JFrame frame, replayFrame = new JFrame();
    private JPanel gameStatsPanel;
    private RendererPanel board;
    private RecordReader recordReader;
    private BufferedImage redKey, greenKey;
    private ArrayList<Move> moveSequence = new ArrayList<>();
    private Timer timer;

    private Game game;
    private double replaySpeed, currentTime = MAX_TIME, prevTime;
    private boolean pauseGame, displayInfoTile;


    public GUI() {
        game = new Game(1);
        board = new RendererPanel(game);

        // creates red and green keys
        try {
            greenKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/greenKey.png"));
            redKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp20/render/data/redKey.png"));
        } catch (IOException e) {
            System.out.println("Item image not found!");
        }

        initialise();

        // starts game from recent saved recording
        setDisplayInfoTile(false);
        resetLevel(1);
        recordReader = new RecordReader(this, new File("Recordings/UserData/lastgame.json"), game.getPlayer(), game.getBug());
        currentTime = recordReader.getTime();
        recordReader.playAtSpeed(0);
    }

    /**
     * Initialises and displays the GUI on the screen.
     */
    public void initialise() {
        // creates main frame
        frame = new JFrame("Chip's Challenge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 1040);
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
                saveMovements();
            }
        });

        // PAUSE button
        JMenuItem pauseButton = new JMenu("PAUSE");
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pauseGame(true);
                displayPauseFrame();
            }
        });

        // RESUME button
        JMenuItem resumeButton = new JMenu("RESUME");
        resumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (pauseGame) {
                    displayResumeFrame();
                    pauseGame(false);
                }
            }
        });

        // REPLAY button
        JMenuItem replayButton = new JMenu("REPLAY");
        replayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pauseGame(true);
                displayReplayFrame();
            }
        });

        // HELP button
        JMenuItem helpButton = new JMenu("HELP");
        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pauseGame(true);
                displayHelpFrame();
                pauseGame(false);
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

        // creates game stats panel
        gameStatsPanel = new JPanel(new GridLayout(4, 1, 0, 15));
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
        InputMap inputMap = keystrokes.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = keystrokes.getActionMap();

        // UP
        inputMap.put(KeyStroke.getKeyStroke("UP"), "MOVE_UP");
        actionMap.put("MOVE_UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(new moveUp(game.getPlayer()));
            }
        });

        // DOWN
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "MOVE_DOWN");
        actionMap.put("MOVE_DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(new moveDown(game.getPlayer()));
            }
        });

        // LEFT
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "MOVE_LEFT");
        actionMap.put("MOVE_LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(new moveLeft(game.getPlayer()));
            }
        });

        // RIGHT
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "MOVE_RIGHT");
        actionMap.put("MOVE_RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(new moveRight(game.getPlayer()));
            }
        });

        // CTRL-X (exit)
//        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.META_DOWN_MASK), "EXIT");           // mac only
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), "EXIT");
        actionMap.put("EXIT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayExitFrame();
            }
        });

        // CTRL-S (save)
//        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_DOWN_MASK), "SAVE");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "SAVE");
        actionMap.put("SAVE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                new RecordSaver(moveSequence, currentTime, true);
                System.exit(0);
            }
        });

        // CTRL-R (resume a saved game)
//        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.META_DOWN_MASK), "RESUME");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "RESUME");
        actionMap.put("RESUME", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pauseGame) {
                    displayResumeFrame();
                    pauseGame(false);
                }
            }
        });

        // CTRL-P (start new game at last unfinished level)
//        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.META_DOWN_MASK), "NEW_GAME");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), "NEW_GAME");
        actionMap.put("NEW_GAME", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetLevel(game.getLevel());
            }
        });

        // CTRL-1 (start new game at level 1)
//        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.META_DOWN_MASK), "NEW_GAME_1");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.CTRL_DOWN_MASK), "NEW_GAME_1");
        actionMap.put("NEW_GAME_1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetLevel(1);
            }
        });

        // SPACE (pauses game)
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "PAUSE");
        actionMap.put("PAUSE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseGame(true);
                displayPauseFrame();
            }
        });

        // ESC (escapes pause and resumes game)
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "ESCAPE");
        actionMap.put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // only displays if game is on pause
                if (pauseGame) {
                    displayResumeFrame();
                    pauseGame(false);
                }
            }
        });

        return keystrokes;
    }


    /**
     * Displays the game statistics panel on the RHS.
     * This includes displaying the current level, time, chips left and inventory.
     */
    public void displayGameStatsPanel(JPanel gameStats) {
        gameStats.setBorder(BorderFactory.createEmptyBorder(50, 90, 50, 90));

        JPanel levelPanel = createGameStat("LEVEL", game.getLevel());
        JPanel timePanel = displayTimePanel();
        JPanel treasuresLeftPanel = createGameStat("TREASURES\nLEFT", game.getPlayer().getNumberTreasures());
        JPanel keysCollectedPanel = createKeysCollected("KEYS COLLECTED");

        // add all components to frame
        gameStats.add(levelPanel);
        gameStats.add(timePanel);
        gameStats.add(treasuresLeftPanel);
        gameStats.add(keysCollectedPanel);

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
        pauseButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none"); // ignores space key
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pauseGame(true);
                displayPauseFrame();
            }
        });

        // RESUME button
        JButton resumeButton = new JButton("RESUME");
        resumeButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none"); // ignores space key
        resumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (pauseGame) {
                    displayResumeFrame();
                    pauseGame(false);
                }
            }
        });

        // REPLAY button
        JButton replayButton = new JButton("REPLAY");
        replayButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none"); // ignores space key
        replayButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pauseGame(true);
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
        // get replay file
        File file = getFile();
        if (file == null) return;

        resetLevel(1);
        recordReader = new RecordReader(this, file, game.getPlayer(), game.getBug());
        resetTime();

        // formats frame
        replayFrame = new JFrame("REPLAY CONTROLS");
        replayFrame.setSize(600, 270);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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

        // AUTO-REPLAY panel
        String[] options = {"0.5", "1", "1.5", "2"};
        JComboBox<String> combobox = new JComboBox<>(options);
        combobox.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel speed = new JPanel();
        speed.add(new JLabel("Select a replay speed (time between each step):"));
        speed.add(combobox);

        // STEP-BY-STEP Panel
        JPanel stepPanel = new JPanel(new GridLayout(2, 1));
        JButton nextButton = new JButton("NEXT");
        stepPanel.add(new JLabel("Click the 'NEXT' button to step through the replay."), SwingConstants.CENTER);
        stepPanel.add(nextButton);
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clicking next");
                recordReader.playNextFrame(); //todo give player an object not null
            }
        });

        // STEP-BY-STEP button
        JButton stepButton = new JButton("STEP-BY-STEP REPLAY");
        stepButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(replayFrame, stepPanel, "STEP-BY-STEP REPLAY", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // AUTO-REPLAY button
        JButton autoButton = new JButton("AUTO-REPLAY");
        autoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: ADD AUTO-REPLAY CODE
                JOptionPane.showMessageDialog(replayFrame, speed, "AUTO-REPLAY", JOptionPane.INFORMATION_MESSAGE);
                replaySpeed = Double.parseDouble((String) combobox.getSelectedItem());
                recordReader.playAtSpeed(replaySpeed);
            }
        });

        actions.add(stepButton);
        actions.add(autoButton);

        // exit panel
        JPanel exitPanel = new JPanel();
        JButton exitReplay = new JButton("Back to Game");
        exitReplay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String message = "Exited Replay mode. Click 'OK' to return to game.";
//                JOptionPane.showMessageDialog(frame, message, "BACK TO GAME", JOptionPane.INFORMATION_MESSAGE);
                int input = JOptionPane.showOptionDialog(frame, message, "BACK TO GAME", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                if (input == JOptionPane.OK_OPTION) {
//                    System.out.println("restart timer");
                    replayFrame.setVisible(false);
                    pauseGame = false;
                    currentTime = recordReader.getTime();
                    redisplayTimer();
                } else {
                    displayReplayFrame();
                }
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
     * Displays the help frame which displays the game rules and instructions.
     */
    public void displayHelpFrame() {
        String message = "HOW TO PLAY\n\n" +
                "AIM OF THE GAME\n" +
                "- Collect all the treasures in the level and reach the winning tile before time runs out!\n\n" +
                "MOVEMENT\n" +
                "- Use the arrow keys to move Chap around the maze.\n" +
                "- Unlock doors by collecting their respective keys.\n" +
                "- You cannot progress to the next level until you collect all the treasures!\n\n" +
                "SPECIAL KEYS\n" +
                "CTRL-X:\t Exit the game. The current game state will be lost, the next time the game is started, it will resume from the last unfinished level.\n" +
                "CTRL-S:\t Exit the game, saves the game state, game will resume next time application is started.\n" +
                "CTRL R:\t Resume a saved game.\n" +
                "CTRL-P:\t Start a new game a the last unfinished level (restart level).\n" +
                "CTRL-1:\t Start a new game at level 1.\n" +
                "SPACE:\t\t Pause the game and displays a 'game is paused' dialog.\n" +
                "ESC:\t\t\tClose the 'game is paused' dialog and resume the game.\n" +
                "You can also use the menu options and buttons below to execute actions.\n\n" +
                "SPECIAL TILES\n" +
                "INFO tile: Step on this tile to show instructions\n" +
                "TIME tile: Step on this tile to add 10 extra seconds to the timer.\n\n" +
                "REPLAY MODE\n" +
                "";
        JOptionPane.showMessageDialog(frame, message, "HELP", JOptionPane.PLAIN_MESSAGE);

    }

    /**
     * Displays a "GAME WON" frame and exits the game.
      */
    public void displayGameWon() {
        String message = "Woohoo!! You won the game!\nSee you next time :D";
        JOptionPane.showMessageDialog(frame, message, "GAME WON", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * A helper method which creates and formats the JPanel containing a game statistic.
     * Used to display the current level and keys left in the game.
     *
     * @param name     -- name of the statistic (eg. "LEVEL");
     * @param gameStat -- the current value for this statistic (eg. "1").
     * @return completed JPanel displaying game statistic.
     */
    public JPanel createGameStat(String name, int gameStat) {
        // title
        JPanel panel = new JPanel(GAME_STATS_LAYOUT);
        JLabel title = new JLabel(name, JLabel.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(Color.RED);

        // game stat
        JLabel level = new JLabel(String.valueOf(gameStat), JLabel.CENTER);
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
     * Creates and formats the JPanel containing the current keys collected.
     * The keys are only displayed after the player collects them.
     *
     * @param name -- name of panel.
     * @return completed JPanel displaying title and keys collected.
     */
    public JPanel createKeysCollected(String name) {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        JLabel title = new JLabel(name, JLabel.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(Color.RED);

        JPanel keysPanel = new JPanel();
        keysPanel.setLayout(new GridLayout(1, 3));

        // draws red key
        if (game.getPlayer().getKeys().containsKey("R")) {
            JLabel rKey = new JLabel(new ImageIcon(new ImageIcon(redKey).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT)), JLabel.CENTER);
            keysPanel.add(rKey);
        }

        // draws green key
        if (game.getPlayer().getKeys().containsKey("G")) {
            JLabel gKey = new JLabel(new ImageIcon(new ImageIcon(greenKey).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT)), JLabel.CENTER);
            keysPanel.add(gKey);
        }

        panel.add(title);
        panel.add(keysPanel);

        return panel;
    }

    /**
     * Creates and executes the timer for each round.
     * Player wins a round if they succeed in collecting all the keys within the time limit.
     * Otherwise, the player has failed and loses the game.
     */
    public void createTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // power-up that adds time //TODO: check this works!
                if (game.getPlayer().playerIsRecharge()) {
                    currentTime += 10;
                    System.out.println("recharge power-up");
                    return;
                }

                // retrieves and moves bug
                if (game.getLevel() == 2) {
                    try {
                        game.getParser().aClass.getMethod("moveBugSequence").invoke(game.getBug());
                        game.updatePlayerBugStatus();
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                        ex.printStackTrace();
                    }
                    board.moveBug();
                }

                // pauses game
                if (pauseGame || currentTime == 0)
                    return;

                currentTime--;
                redisplayTimer();

                // game over
                if (gameOver()) {
                    String message = "You didn't pass the level in time... GAME OVER!\nClick 'OK' to restart the level.";
                    JOptionPane.showMessageDialog(frame, message, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
                    resetLevel(1);
                }
            }
        });
        timer.start();
    }

    /**
     * Checks if user has lost the game.
     * The user loses a game if they fail to reach the winning tile before time runs out.
     *
     * @return true if user has lost. Otherwise, false.
     */
    public boolean gameOver() {
        return currentTime <= 0 && !(game.getPlayer().getCurrentTile() instanceof winTile);
    }

    /**
     * Abstract method that saves the movements.
     */
    public void saveMovements() {
        new RecordSaver(moveSequence, currentTime);
    }

    /**
     * Sets the state of the game between pause and live game.
     *
     * @param pause -- true if game is paused. Otherwise, false.
     */
    public void pauseGame(boolean pause) {
        pauseGame = pause;
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
     * Moves the player to the next level if they land on a win tile.
     * Executed after a player moves and moves the player to the next level.
     */
    public void checkWinTile() {
        if (game.getMap()[game.getPlayer().getRow()][game.getPlayer().getCol()] instanceof winTile) {

            // game won
            if (game.getLevel() == 2) {
                board.playWinSound();
                displayGameWon();
                System.exit(0);
            }

            game.getPlayer().moveToNextLevel();
            game.loadLevel();
            board.winLevel();
            resetTime();

            // updates actor's move between levels
            if (recordReader != null)
                recordReader.updateMovesForActors();
        }
    }

    /**
     * This method moves the player in the specified direction and updates the board.
     * Also checks if the player has landed on a winTile or infoTile.
     *
     * @param move -- the player's most recent move.
     */
    public void movePlayer(Move move) {
        moveSequence.add(move);
        game.moveActor(move);
        board.renderMove(move.getDir());
        checkWinTile();
        redisplayTimer();

        // displays bug
        if (game.getLevel() == 2) {
            game.updatePlayerBugStatus();
        }

        // checks if player is on info tile
        if (displayInfoTile && game.getPlayer().getCurrentTile() instanceof infoTile) {
            pauseGame(true);
            displayHelpFrame();
            pauseGame(false);
        }

    }

    /**
     * Resets the level once replay mode is executed. Replay mode
     * assumes that the replay starts at level 1 as it will move
     * to level 2 when level 1 is passed with the same moves.
     *
     * @param level -- the current level.
     */
    public void resetLevel(int level) {
        game.setLevel(level);
        game.loadLevel();
        board.updateLevel(game.getMap());
        board.updateRenderMaps();
        resetTime();
    }

    /**
     * Resets the time to the maximum time.
     */
    public void resetTime() {
        currentTime = MAX_TIME;
        redisplayTimer();
    }

    /**
     * Determines whether the info tile is shown or not.
     * During replays, the info tile should not be shown.
     */
    public void setDisplayInfoTile(boolean showTile) {
        displayInfoTile = showTile;
    }

    /**
     * Returns the current player.
     *
     * @return the current player.
     */
    public Player getPlayer() {
        return game.getPlayer();
    }

    public Game getGame() {
        return game;
    }

    /**
     * Gets a file from user via a {@link JFileChooser}4
     *
     * @return the selected file.
     */
    public static File getFile() {
        JFileChooser fileChooser = new JFileChooser("Recordings/");
        if (fileChooser.showOpenDialog(new JButton("Open")) == JFileChooser.APPROVE_OPTION)
            return fileChooser.getSelectedFile();
        return null;
    }

    /**
     * Used by RecordReader class to display error messages.
     *
     * @param message -- the error message to be displayed.
     */
    public static void notifyError(String message) {
        // based on https://stackoverflow.com/questions/7993000/need-to-use-joptionpane-error-message-type-of-jdialog-in-a-jframe
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }

}


