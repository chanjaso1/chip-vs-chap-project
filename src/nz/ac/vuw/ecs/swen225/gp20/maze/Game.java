package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.application.GUI;
import nz.ac.vuw.ecs.swen225.gp20.persistence.parseJSON;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.RecordReader;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.RecordSaver;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Game class is the main class that run the game.
 */
public class Game extends GUI {
    private int num;
    private Player player;
    private Tile currentTile;
    private Tile[][] map;
    private ArrayList<Move> moveSequence;
    private parseJSON parser;

    public Game()  {

        parser = new parseJSON(System.getProperty("os.name").equalsIgnoreCase("Linux")?"chip-vs-chap-project/levels/level1.json":"levels/level1.json");

        map = parser.getMap();
        player = parser.getPlayer();

        player.setGame(this);
        moveSequence = new ArrayList<>();

        this.setRendererPanel(this);
        this.initialise();
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

    /**
     * Return the game object
     * @return -- current game object
     */
    public  Game getGame(){
        return this;
    }

    @Override
    public void movePlayer(Move move) {
        moveSequence.add(move);
        move.apply(player);

    }

    @Override
    public void saveMovements(){
        new RecordSaver(moveSequence);
        parser.saveGame(map,player);
    }

    public static void main(String[] args){
        new Game().runGame();
    }
}
