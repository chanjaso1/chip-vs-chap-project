package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This interface represent the movement being done in the game.
 */
public abstract class Move {
    protected Actor mover;
    protected int dir;

    public Move(Actor actor){
        mover = actor;
    }

    /**
     * Apply the movement to the player.
     */
    public abstract void apply();

    public Actor getMover() {
        return mover;
    }

    public int getDir(){
        return dir;
    }

    public void setMover(Actor mover) {
        this.mover = mover;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    //todo object does not have fields to check hence the weird equals and hashcode
    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == getClass() && mover.getClass() == ((Move)obj).getMover().getClass();
    }
}






