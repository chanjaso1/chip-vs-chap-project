package nz.ac.vuw.ecs.swen225.gp20.maze;

public interface Move {

    /**
     * Apply the movement to the player.
     * @param player -- the current player in the game.
     */
    public void apply(Player player);

}

/**
 * This class is created to move the player in the left direction
 */
class moveLeft implements Move{

    @Override
    public void apply(Player player) {
        player.setPosition(player.getRow(),Math.max( player.getCol()-1,0));
    }
}

/**
 * This class is created to move the player in the right direction
 */
class moveRight implements Move{

    @Override
    public void apply(Player player) {
        player.setPosition(player.getRow(),Math.min(player.getCol()+1,30));
    }
}

/**
 * This class is created to move the player up
 */
class moveUp implements Move{

    @Override
    public void apply(Player player) {
        player.setPosition(Math.max(0,player.getRow()-1),player.getCol());
    }
}

/**
 * This class is created to move the player down
 */
class moveDown implements Move{

    @Override
    public void apply(Player player) {
        player.setPosition(Math.min(0,player.getRow()+1),player.getCol());

    }
}
