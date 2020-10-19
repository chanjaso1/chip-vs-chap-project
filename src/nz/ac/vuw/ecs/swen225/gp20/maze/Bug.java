package nz.ac.vuw.ecs.swen225.gp20.maze;

public class Bug extends Actor {
    private boolean moveDown = true;

    public Bug(int row, int col) {
        super(row, col);
    }

    public void moveBug(){
        if(moveDown) //MOVE DOWN AND CHECK IF TILE IS GOOD
            this.getGame()
    }

    @Override
    public String toString() {
        return "B";
    }
}
