package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.persistence.Bug;

/**
 * This class is created to move the player down
 */
public class moveDown extends Move {

    public moveDown(Actor actor) {
        super(actor);
        dir = 2;
    }

    @Override
    public void apply() {
        //the next tile that player is moving to
        Tile nextTile = mover.getGame().getMap()[Math.min(24, mover.getRow() + 1)][mover.getCol()];
        if(mover instanceof Player && nextTile.checkValidMove((Player) mover) || mover instanceof Bug)
                mover.setPosition(Math.min(24, mover.getRow() + 1), mover.getCol());

        this.getMover().getGame().updatePlayerBugStatus();
    }

    @Override
    public String toString() {
        return "down";
    }

}
