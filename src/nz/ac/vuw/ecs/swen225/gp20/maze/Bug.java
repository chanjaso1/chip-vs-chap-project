package nz.ac.vuw.ecs.swen225.gp20.maze;

public class Bug implements Actor {
    @Override
    public void move(Move move) {
        System.out.println("moving bug");
    }
}
