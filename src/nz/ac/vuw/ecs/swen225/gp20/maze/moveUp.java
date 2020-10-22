package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player up.
 */
public class moveUp extends Move {

    /**
     * Move up constructor.
     * @param actor -- actor that perform the action.
     */
    public moveUp(Actor actor) {
        super(actor);

        dir = 0;
    }

    @Override
    public void apply() {

        Tile nextTile = mover.getGame().getMap()[Math.max(0,mover.getRow()-1)][mover.getCol()];
        if(!(mover instanceof Player) || nextTile.checkValidMove((Player) mover)) {
            mover.setPosition(Math.max(0,mover.getRow()-1),mover.getCol());
        }

    }

    @Override
    public String toString() {
        return "up";
    }
}
