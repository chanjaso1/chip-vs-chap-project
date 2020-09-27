package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player down
 */
public class moveDown implements Move {

    @Override
    public void apply(Player player) {
        //the next tile that player is moving to
        Tile nextTile = player.getGame().getMap()[Math.min(24, player.getRow() + 1)][player.getCol()];
        if (nextTile.checkValidMove(player)) {
            player.setPosition(Math.min(0, player.getRow() + 1), player.getCol());
        }

    }
}
