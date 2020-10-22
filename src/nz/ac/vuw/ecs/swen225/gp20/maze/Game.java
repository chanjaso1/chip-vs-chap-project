package nz.ac.vuw.ecs.swen225.gp20.maze;
import com.google.gson.internal.$Gson$Preconditions;
import nz.ac.vuw.ecs.swen225.gp20.persistence.parseJSON;

import java.lang.reflect.InvocationTargetException;

/**
 * Game create a new parser and initialized every object that will need to run a game.
 */
public class Game {
    private int num;
    private Player player;
    private Tile[][] map;
    private parseJSON parser;
    private Object bug = null;
    private int level = 0;

    /**
     * Game constructor to create a game object.
     * @param level -- level of the game.
     */
    public Game(int level) {
        $Gson$Preconditions.checkArgument(level > 0 && level < 3);
        this.level = level;
        loadLevel();
    }

    /**
     * Create a new parser and initialised map, player and bug for the current level.
     * This function will be called every time the level is reload.
     */
    public void loadLevel()  {
        parser = new parseJSON("levels/level" + level + ".json");
        $Gson$Preconditions.checkNotNull(parser);

        map = parser.getMap();
        player = parser.getPlayer();
        player.setGame(this);
        player.setTotalTreasures(parser.getTreasures());
        player.setPosition(player.getRow(),player.getCol());
        player.setStartPosition(player.getRow(),player.getCol());

        bug = parser.getBug();
        try {
            if (bug != null) {
                parser.aClass.getMethod("setGame", Game.class).invoke(bug, this);
            }
        } catch (NoSuchMethodException ignored){

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    /**
     * Keep track of player status with the bug enemies.
     * The player position will be set to the start if the player is attack by bug.
     */
    public void updatePlayerBugStatus(){
        if(bug == null || player == null) return;

        try {
            if(player.getCurrentTile().equals(parser.aClass.getMethod("getCurrentTile").invoke(bug))){
                player.setPlayerBackToStartPosition();
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the current map in the game.
     * @return current map in the game.
     */
    public Tile[][] getMap() {
        return map.clone();
    }

    /**
     * Return the game object.
     * @return -- current game object.
     */
    public  Game getGame(){
        return this;
    }

    /**
     * Return the parser.
     * @return -- current parser that define.
     */
    public parseJSON getParser() {
        return parser;
    }

    /**
     * Return the current player in the game.
     * @return -- current player in the game.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Apply the given move to the actor.
     * @param move -- move object depends on the direction the actor is moving.
     */
    public void moveActor(Move move){
        $Gson$Preconditions.checkNotNull(move);
        move.apply();
    }


    /**
     * Return the current bug object in the game.
     * @return -- the current bug object in the game.
     */
    public Object getBug() {
        return bug;
    }

    /**
     * Return the current level of the game.
     * @return -- current level of the game.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Set the given level to the level field.
     * @param level -- given level should be more than 0.
     */
    public void setLevel(int level) {
        $Gson$Preconditions.checkArgument(level > 0 && level < 3);
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
