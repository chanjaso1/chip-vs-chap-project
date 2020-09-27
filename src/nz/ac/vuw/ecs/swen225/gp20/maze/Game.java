package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.persistence.parseJSON;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Game class is the main class that run the game.
 */
public class Game {
    private Player player;
    private Tile currentTile;
    private Tile[][] map;

    public Game()  {

        parseJSON parser = new parseJSON(System.getProperty("os.name").equalsIgnoreCase("Linux")?"chip-vs-chap-project/levels/level1.json":"levels/level1.json");

        map = parser.getMap();
        player = parser.getPlayer();
        player.setGame(this);
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                //System.out.println(map[i][j]);
            }
        }

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

    public static void main(String[] args){
        new Game().runGame();
    }
}
