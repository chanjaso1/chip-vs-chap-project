package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.*;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.floorTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.wallTile;

import java.io.FileNotFoundException;
import java.io.FileReader;


/**
 * Parsing a JSON file. This class can read and write a JSON file for loading
 * and saving.
 *
 */
public class parseJSON{

    private Player player;
    private Tile[][] map;

    //directory= levels/level1.json
    public parseJSON(String directory){
        try {

            JsonObject jobject = new Gson().fromJson(new FileReader(directory), JsonObject.class);   //directory: levels/level1.json
            JsonArray jmap = jobject.getAsJsonArray("map");

            this.map = new Tile[30][30];
            int totalSize = 30;
            int index;


            //initialize the level
            for(int row = 0; row < totalSize; row++){
                for (int col = 0; col < totalSize; col++) {
                    index = (row * totalSize) + col;

                    if(jmap.get(index).equals("_"))  map[row][col] = new floorTile(row, col, null);   //define a floor tile
                    else if(jmap.get(index).equals("P"))  {                                              //Define the player's position on the board
                        map[row][col] = new floorTile(row, col, null);
                        this.player = new Player(row, col);
                    }else if(jmap.get(index).equals("⬛")) {
                        map[row][col] = new wallTile(row, col);
                    }
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("File doesn't exist!");
        }
    }


    public Player getPlayer(){
        return this.player;
    }

    public Tile[][] getMap(){
        return this.map;
    }

}
