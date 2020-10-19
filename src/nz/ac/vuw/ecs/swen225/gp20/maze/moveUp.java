package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.persistence.Bug;

/**
 * This class is created to move the player up
 */
public class moveUp extends Move {

    public moveUp(Actor actor) {
        super(actor);
    }

    @Override
    public void apply() {


        Tile nextTile = mover.getGame().getMap()[Math.max(0,mover.getRow()-1)][mover.getCol()];
        if(mover instanceof Player && nextTile.checkValidMove((Player) mover)) {
            mover.setPosition(Math.max(0,mover.getRow()-1),mover.getCol());
        }
    }

    @Override
    public String toString() {
        return "up";
    }
}
