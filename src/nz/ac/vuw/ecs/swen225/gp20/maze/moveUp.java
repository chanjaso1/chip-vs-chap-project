package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player up
 */
public class moveUp extends Move {

    public moveUp(Actor actor) {
        super(actor);
    }

    @Override
    public void apply(Player player) {

        Tile nextTile = player.getGame().getMap()[Math.max(0,player.getRow()-1)][player.getCol()];
        if (nextTile.checkValidMove(player)) {
            player.setPosition(Math.max(0,player.getRow()-1),player.getCol());
        }
    }

    @Override
    public String toString() {
        return "up";
    }
}
