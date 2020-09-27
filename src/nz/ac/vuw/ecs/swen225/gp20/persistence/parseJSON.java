package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.*;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;

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
    private int treasures = 0;



    /**
     * This constructor reads a JSON file from a given directory. This object is able to return the map, player and the number of treasures the map has.
     * @param directory
     */
    public parseJSON(String directory){
        try {

            JsonObject jobject = new Gson().fromJson(new FileReader(directory), JsonObject.class);   //directory: levels/level1.json
            JsonArray jmap = jobject.getAsJsonArray("map");

            int totalSize = 30;
            this.map = new Tile[totalSize][totalSize];

            //initialize the level
            for(int row = 0; row < totalSize; row++){
                for (int col = 0; col < totalSize; col++) {
                    String tileType = jmap.get((row * totalSize) + col).getAsString();

                    if(tileType.equals("_"))  map[row][col] = new floorTile(row, col, null);   //Define a floor tile.
                    else if(tileType.equals("P"))  {                                              //Define the player's position on the board.
                        map[row][col] = new floorTile(row, col, null);
                        this.player = new Player(row, col);
                    }else if(tileType.equals("▊"))   map[row][col] = new wallTile(row, col);                            //define a wall tile.
                    else if(tileType.substring(0,1).equals("D"))          map[row][col] = new doorTile(row, col, tileType.substring(1,2));  //define a coloured door.
                    else if(tileType.substring(0,1).equals("K"))        map[row][col] = new floorTile(row, col, new Key(map[row][col],  tileType.substring(1,2)));  //define a coloured key
                    else if(tileType.equals("T")){
                        Treasure treasure = new Treasure(map[row][col]);
                        map[row][col] = new floorTile(row, col, treasure);
                        this.treasures++;                                                  //Add 1 to the total number of treasures/chips.
                    }else if(tileType.equals("W"))                      map[row][col] = new winTile(row, col);
                }
            }
            
            System.out.println("File loaded!");

        }catch(FileNotFoundException e){
            System.out.println("This file doesn't exist!");
        }
    }

    public Player getPlayer(){
        return this.player;
    }

    public Tile[][] getMap(){
        return this.map;
    }

    public int getTreasures(){
        return this.treasures;
    }

}
