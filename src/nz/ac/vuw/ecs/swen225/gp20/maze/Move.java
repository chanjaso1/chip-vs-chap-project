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

    //    public default boolean equals(){
//        return true;
//    }
}

 class MoveTesting {
    String direction;

    public MoveTesting(String direction) {
        this.direction = direction;
    }

    public void apply(){

    }

    @Override
    public String toString() {
        return direction;
    }
}


