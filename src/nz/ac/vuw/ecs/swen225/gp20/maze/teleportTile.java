package nz.ac.vuw.ecs.swen225.gp20.maze;

public class teleportTile implements Tile {
    @Override
    public boolean checkValidMove(Player player) {
        return false;
    }
}
