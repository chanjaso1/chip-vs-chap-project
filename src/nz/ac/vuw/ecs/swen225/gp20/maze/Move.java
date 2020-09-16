package nz.ac.vuw.ecs.swen225.gp20.maze;

public class Move {
    String direction;

    public Move(String direction) {
        this.direction = direction;
    }

    public void apply(){

    }

    @Override
    public String toString() {
        return direction;
    }
}
