package nz.ac.vuw.ecs.swen225.gp20.maze;

public class Bug extends Actor {
    public Bug(int row, int col) {
        super(row, col);
    }

    @Override
    public void move(Move move) {
        System.out.println("moving bug");
    }

    @Override
    public String toString() {
        return "B";
    }
}
