package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player in the left direction
 */
public class moveLeft implements Move {

    @Override
    public void apply(Player player) {
        player.setPosition(player.getRow(),Math.max( player.getCol()-1,0));
    }
}
