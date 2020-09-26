package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.persistence.parseJSON;

import java.util.Arrays;

/**
 * Game class is the main class that run the game.
 */
public class Game {
    private Player player;
    private Tile currentTile;
    private Tile[][] map;

    public Game() {
        //initialised
        parseJSON parser = new parseJSON("levels/level1.json");
        map = parser.getMap();
        player = parser.getPlayer();
        player.setGame(this);
        System.out.println(Arrays.deepToString(map));

    }

    public void runGame(){
    }

    /**
     * Return the current map in the game.
     * @return current map in the game.
     */
    public Tile[][] getMap() {
        return map;
    }

    public static void main(String[] args) {
        new Game().runGame();
    }
}
