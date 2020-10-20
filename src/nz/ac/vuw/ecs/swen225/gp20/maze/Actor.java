package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This interface represent the characters in the game.
 *  Each characters will have t
 */
public class Actor {
    private Game game;
    private int row, col;
    private Tile currentTile;

    /**
     * Actor constructor use to create all the actor in the game that has movement.
     * @param row -- the row on the map this actor is on.
     * @param col -- the column in the map this actor is on.
     */
    public Actor(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Set the player position to the given row and column.
     * @param row -- new row that the player is
     * @param col -- new column that the player is
     */
    public void setPosition(int row , int col){
        this.row = row;
        this.col = col;
        this.currentTile = game.getMap()[row][col];
    }

    /**
     * Return the player's current column.
     * @return player's current column
     */
    public int getCol() {
        return col;
    }

    /**
     * Return player's current row.
     * @return player's current row.
     */
    public int getRow() {
        return row;
    }
    /**
     * Move the player in the chosen direction.
     *
     * @param move move class applies according to the players choices in directions
     */
    public void move(Move move) {
        move.apply();
    }

    /**
     * Return the current game that player's in.
     * @return player's current Game object
     */
    public Game getGame() {
        return game;
    }

    /**
     * Return the current tile that the actor is on.
     * @return -- current tile the actor is on.
     */
    public Tile getCurrentTile() {
        return currentTile;
    }

    /**
     * Set the player's game to the given game.
     * @param game -- the current game that the player's in.
     */
    public void setGame(Game game) {
        this.game = game;
    }

}
