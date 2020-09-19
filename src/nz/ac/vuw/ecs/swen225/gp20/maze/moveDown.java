package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player down
 */
public class moveDown implements Move {

    @Override
    public void apply(Player player) {
        player.setPosition(Math.min(0, player.getRow() + 1), player.getCol());
    }

    @Override
    public String toString() {
        return "down";
    }

    //todo object does not have fields to check hence the weird equals and hashcode
    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
