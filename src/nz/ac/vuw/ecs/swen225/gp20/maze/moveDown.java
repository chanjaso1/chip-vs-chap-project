package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player down.
 */
public class MoveDown extends Move {

    /**
     * Move Down constructor.
     * @param actor -- the actor performing this movement.
     */
    public MoveDown(Actor actor) {
        super(actor);
        dir = 2;
    }

    @Override
    public void apply() {
        //the next tile that player is moving to
        Tile nextTile = mover.getGame().getMap()[Math.min(24, mover.getRow() + 1)][mover.getCol()];
        if(!(mover instanceof Player) || nextTile.checkValidMove((Player) mover))
                mover.setPosition(Math.min(24, mover.getRow() + 1), mover.getCol());
    }

    @Override
    public String toString() {
        return "down";
    }

}
