package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.persistence.parseJSON;

/**
 * Game class is the main class that run the game.
 */
public class Game {
    private int num;
    private Player player;
    private Tile currentTile;
    private Tile[][] map;
    private parseJSON parser;
    private Bug bug = null;
    private int level = 0;


    public Game(int level)  {
        this.level = level;
        loadLevel();
    }

    public void runGame(){


    }

    /**
     * Create a new parser and initialised map, player and bug for the current level.
     * This function will be called every time the level is reload.
     */
    public void loadLevel(){
        parser = new parseJSON("levels/level" + level + ".json");
        map = parser.getMap();
        player = parser.getPlayer();
        player.setGame(this);
        player.setTotalTreasures(parser.getTreasures());
        bug = parser.getBug();
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

    public parseJSON getParser() {
        return parser;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void moveActor(Move move){
        move.apply();
    }

    public void saveGame(){
        parser.saveGame(map,player);
    }

    /**
     * Return the current bug object in the game.
     * @return -- the current bug object in the game.
     */
    public Bug getBug() {
        return bug;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Return the number of treasure set in parser.
     * @return -- number of treasure.
     */
    public int getTreasure(){
        return parser.getTreasures();
    }

}
