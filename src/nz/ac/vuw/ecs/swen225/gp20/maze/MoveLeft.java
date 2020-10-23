package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player in the left direction.
 */
public class MoveLeft extends Move {

    /**
     * Move Left constructor.
     * @param actor -- actor that perform this action.
     */
    public MoveLeft(Actor actor) {
        super(actor);
        dir = 3;
    }

    @Override
    public void apply() {
        Tile nextTile = mover.getGame().getMap()[mover.getRow()][Math.max(mover.getCol() - 1, 0)];
        if(!(mover instanceof Player) || nextTile.checkValidMove((Player) mover)) {
            mover.setPosition(mover.getRow(), Math.max(mover.getCol() - 1, 0));
        }

    }

    @Override
    public String toString() {
        return "left";
    }
}
