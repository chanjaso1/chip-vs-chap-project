package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player in the right direction
 */
public class moveRight implements Move {

    @Override
    public void apply(Player player) {
        Tile nextTile = player.getGame().getMap()[player.getRow()][Math.max(player.getCol() - 1, 0)];
        if (nextTile.checkValidMove(player)) {
            player.setPosition(player.getRow(), Math.min(player.getCol() + 1, 24));
        }
    }
}