package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player in the right direction
 */
public class moveRight implements Move {

    @Override
    public void apply(Player player) {
        Tile nextTile = player.getGame().getMap()[player.getRow()][Math.min(player.getCol() + 1, 24)];
        if (nextTile.checkValidMove(player)) {
            player.setPosition(player.getRow(), Math.min(player.getCol() + 1, 24));
        }
    }

    @Override
    public String toString() {
        return "right";
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}