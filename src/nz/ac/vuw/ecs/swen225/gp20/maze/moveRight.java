package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player in the right direction
 */
public class moveRight implements Move{

    @Override
    public void apply(Player player) {
        player.setPosition(player.getRow(),Math.min(player.getCol()+1,30));
    }
}