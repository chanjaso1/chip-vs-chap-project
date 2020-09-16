package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.*;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile.*;

import java.io.FileNotFoundException;
import java.io.FileReader;


/**
 * Parsing a JSON file. This class can read and write a JSON file for loading
 * and saving.
 *
 */
public class parseJSON{



    //directory= levels/level1.json
    public void read(String directory){


        try {

            JsonObject jobject = new Gson().fromJson(new FileReader("levels/level1.json"), JsonObject.class);   //directory: levels/level1.json
            JsonArray jmap = jobject.getAsJsonArray("map");
            String row = jmap.get(0).getAsString();

            Tile[][] map = new Tile[30][30];
            int totalSize = 30;

            for(int i = 0; i < totalSize; i++){
                for (int j = 0; j < totalSize; j++) {
                    if(jmap.get((i * totalSize) + j).equals("_"))  map[i][j] = new Tile(j, i); //TODO Cast to floor tile
                    if(jmap.get((i * totalSize) + j).equals("P"))  {
                        map[i][j] = new Tile(j, i); //TODO Cast to floor tile
                        Player player = new Player(0,0);
                    }
                }
            }

        }catch(FileNotFoundException e){
            System.out.println("File doesn't exist!");
        }



    }

    public void write(){

    }

}
