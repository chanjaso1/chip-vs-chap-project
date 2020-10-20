package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player in the left direction
 */
public class moveLeft extends Move {

    public moveLeft(Actor actor) {
        super(actor);
        dir = 3;
    }

    @Override
    public void apply() {
        Tile nextTile = mover.getGame().getMap()[mover.getRow()][Math.max(mover.getCol() - 1, 0)];
        if(mover instanceof Player && nextTile.checkValidMove((Player) mover) || mover instanceof Bug) {
            mover.setPosition(mover.getRow(), Math.max(mover.getCol() - 1, 0));
        }

        this.getMover().getGame().updatePlayerBugStatus();
    }

    @Override
    public String toString() {
        return "left";
    }
}
