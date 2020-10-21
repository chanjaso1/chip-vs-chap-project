package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.internal.$Gson$Preconditions;

/**
 * This abstract class represent the movement being done in the game.
 * It is the super class for 4 other subclasses that moves in different direction.
 */
public abstract class Move {
    protected Actor mover;
    protected int dir;

    /**
     * Move constructor.
     * @param actor -- actor is moving.
     */
    public Move(Actor actor){
        $Gson$Preconditions.checkNotNull(actor);
        mover = actor;
    }

    /**
     * Apply the movement to the player.
     */
    public abstract void apply();

    /**
     * Get the actor that perform this movement.
     * @return -- actor that perform this movement.
     */
    public Actor getMover() {
        return mover;
    }

    /**
     * Get the direction of the move.
     * Each direction has their own number:
     * up = 0
     * right = 1
     * down = 2
     * right = 3
     * @return -- unique number for each direction.
     */
    public int getDir(){
        return dir;
    }

    /**
     * Set mover to the move object.
     * @param mover -- the given mover to set to the object.
     */
    public void setMover(Actor mover) {
        this.mover = mover;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        $Gson$Preconditions.checkNotNull(obj);
        //@note object does not have fields to check hence the weird equals and hashcode
        return obj.getClass() == getClass() && mover.getClass() == ((Move)obj).getMover().getClass();
    }
}






