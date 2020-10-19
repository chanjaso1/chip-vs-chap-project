package nz.ac.vuw.ecs.swen225.gp20.maze;

public class Bug extends Actor {
    private boolean moveDown = true;

    public Bug(int row, int col) {
        super(row, col);
    }

    public void moveBug(){
        if(moveDown) {
            Tile tile = this.getGame().getMap()[this.getRow()][this.getCol()];
            if(tile instanceof floorTile && !((floorTile) tile).isBugPath()){
                moveDown = false;
            }
        }
    }

    @Override
    public String toString() {
        return "B";
    }
}
