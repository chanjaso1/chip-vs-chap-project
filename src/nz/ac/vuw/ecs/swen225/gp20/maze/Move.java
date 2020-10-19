package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This interface represent the movement being done in the game.
 */
public abstract class Move {
    protected Actor mover;

    public Move(Actor actor){
        mover = actor;
    }

    /**
     * Apply the movement to the player.
     * @param player -- the current player in the game.
     */
    public abstract void apply(Player player);

    public Actor getMover() {
        return mover;
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




