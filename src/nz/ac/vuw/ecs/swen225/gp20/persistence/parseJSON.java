package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.*;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;

import java.io.*;


/**
 * Parsing a JSON file. This class can read and write a JSON file for loading
 * and saving.
 *
 */
public class parseJSON{

    private static int currentGameSave = 1;

    private Player player;
    private Bug bug;
    private Tile[][] map;
    private int treasures = 0, keys = 0;
    public int totalSize = 30;

    /**
     * This constructor reads a JSON file from a given directory. This object is able to return the map, player and the number of treasures the map has.
     * @param directory - The file directory.
     */
    public parseJSON(String directory){
        try {

            JsonObject jobject = new Gson().fromJson(new FileReader(directory), JsonObject.class);   //directory: levels/level1.json
            JsonArray jmap = jobject.getAsJsonArray("map");

            this.map = new Tile[totalSize][totalSize];

            //initialize the level
            for(int row = 0; row < totalSize; row++){
                for (int col = 0; col < totalSize; col++) {
                    String tileType = jmap.get((row * totalSize) + col).getAsString();

                    if(tileType.equals("_"))  map[row][col] = new floorTile(row, col, null);   //Define a floor tile.
                    else if(tileType.equals("P"))  {                                              //Define the player's position on the board.
                        map[row][col]   = new floorTile(row, col, null);
                        this.player = new Player(row, col);
                    }else if(tileType.equals("▊"))   map[row][col] = new wallTile(row, col);                            //define a wall tile.
                    else if(tileType.substring(0,1).equals("D"))          map[row][col] = new doorTile(row, col, tileType.substring(1,2));  //define a coloured door.
                    else if(tileType.substring(0,1).equals("K"))        {
                        this.keys++;
                        map[row][col] = new floorTile(row, col, new Key(map[row][col],  tileType.substring(1,2)));  //define a coloured key
                    }
                    else if(tileType.equals("T")){
                        Treasure treasure = new Treasure(map[row][col]);
                        map[row][col] = new floorTile(row, col, treasure);
                        this.treasures++;                                                  //Add 1 to the total number of treasures/chips.
                    }else if(tileType.equals("W"))                      map[row][col] = new winTile(row, col);
                    else if(tileType.equals("B"))  {
                        map[row][col]   = new floorTile(row, col, null);
                        this.bug = new Bug(row, col);
                    }else if(tileType.equals("X")){
                        map[row][col] = new floorTile(row, col, null);
                        ((floorTile) map[row][col]).setBugTile();
                    }

                }
            }
            
            System.out.println("File loaded!");

        }catch(FileNotFoundException e){
            System.out.println("This file doesn't exist!");
        }
    }


    /**
     * Save the current state of the game to a JSON file
     * @param board - The current state of the board.
     */
        public void saveGame(Tile[][] board, Player player){
        String fileName = "save" + currentGameSave++ + ".json";
        StringBuilder stringFile = new StringBuilder();

        //Read the current state of the board into a string.
        stringFile.append("{\n" +
                "  \"map\" : [\n");
        for(int row = 0; row < totalSize; row++){
            stringFile.append("   ");
            for (int col = 0; col < totalSize; col++) {
                    if(row == player.getRow() && col == player.getCol())    stringFile.append("\""+player.toString()+"\",");
                    else if(row == totalSize - 1 && col == totalSize - 1)   {
                        stringFile.append("\""+board[row][col].toString()+"\"");
                        break;
                    } else
                        stringFile.append("\""+board[row][col].toString()+"\",");
                }
            stringFile.append("  \n");
            }




        stringFile.append("  ]\n" +
                "}");

        //Create a new save
        File file = new File("saves/" + fileName);

        try {
            BufferedWriter newFile = new BufferedWriter(new FileWriter(file));
            newFile.write(stringFile.toString());
            newFile.close();
        } catch (IOException e) {
            System.out.println("File cannot be saved!");
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

    public Bug getBug(){
        return this.bug;
    }

    public int getNumberOfKeys(){
        return this.keys;
    }

}
