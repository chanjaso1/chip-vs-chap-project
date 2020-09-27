package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player up
 */
public class moveUp implements Move {

    @Override
    public void apply(Player player) {
        player.setPosition(Math.max(0,player.getRow()-1),player.getCol());
    }
}
