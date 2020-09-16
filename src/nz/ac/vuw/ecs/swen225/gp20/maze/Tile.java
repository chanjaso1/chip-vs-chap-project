package nz.ac.vuw.ecs.swen225.gp20.maze;

public class Tile {
    private int col, row;
    Tile(int col, int row){
        this.col = col;
        this.row = row;
    }
}

class wallTile extends Tile{

    wallTile(int col, int row) {
        super(col, row);
    }
}

class floorTile extends Tile {
    private Item item;
    floorTile(int col, int row,Item item) {
        super(col, row);
        this.item = item;
    }
}

class infoTile extends Tile{

    infoTile(int col, int row) {
        super(col, row);
    }
}

class doorTile extends Tile {
    doorTile(int col, int row) {
        super(col, row);
    }
}

class winTile extends Tile{
    winTile(int col, int row) {
        super(col, row);
    }
}


