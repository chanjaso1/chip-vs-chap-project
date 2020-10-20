package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Preconditions;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


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
    public ClassLoader cl = null;

    /**
     * This constructor reads a JSON file from a given directory. This object is able to return the map, player and the number of treasures the map has.
     * @param directory - The file directory.
     */
    public parseJSON(String directory){
        try {
            JsonObject jobject = null;
            if(!directory.substring(12, 13).equals("1")) {
                Class aClass = loadClass(directory.substring(12, 13));
                if(aClass.getSimpleName().equals("Bug")) jobject = loadMap(directory.substring(12,13));

            }else jobject = new Gson().fromJson(new FileReader(directory), JsonObject.class);
            assert jobject != null : "The map was not correctly loaded!";

            JsonArray jmap = jobject.getAsJsonArray("map");

            this.map = new Tile[totalSize][totalSize];

            //initialize the level
            for(int row = 0; row < totalSize; row++){
                for (int col = 0; col < totalSize; col++) {
                    String tileType = jmap.get((row * totalSize) + col).getAsString();
                    //TODO put if statements inside a method
                    if(tileType.equals("_"))  map[row][col] = new floorTile(row, col, null);   //Define a floor tile.
                    else if(tileType.equals("P"))  {                                              //Define the player's position on the board.
                        map[row][col]   = new floorTile(row, col, null);
                        this.player = new Player(row, col);
                    }else if(tileType.equals("▊"))   map[row][col] = new wallTile(row, col);                            //define a wall tile.
                    else if(tileType.equals("DC"))   map[row][col] = new treasureDoor(row, col);
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
                    }else if(tileType.equals("I")) map[row][col] = new infoTile(row, col);

                }
            }

            System.out.println("File loaded!");

        }catch(FileNotFoundException e){
            System.out.println("This file doesn't exist!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Exception!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Class not found!");
        }
    }

    /**
     * loadClass will load a class from the level 2 jar file.
     * @param id - The level number
     * @return the given class
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static Class loadClass(String id) throws IOException, ClassNotFoundException {
        $Gson$Preconditions.checkNotNull(id);


        //From stackoverflow.com/a/11016392
        JarFile jarFile = new JarFile("levels/level" + id + ".jar");
        var e = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + "levels/level" + id + ".jar" + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) continue;

            String className = je.getName().substring(0, je.getName().length()-".class".length());
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);
            if(className.contains("Bug"))
                return c;
        }
        throw new ClassNotFoundException();
    }

    /**
     * load the Map from the JAR file
     * @param id - The level number
     * @return the JSON map.
     * @throws IOException
     */
    private static JsonObject loadMap(String id) throws IOException, IndexOutOfBoundsException {
        JsonObject jobject = null;
        JarFile jarFile = new JarFile("levels/level" + id + ".jar");
        Enumeration<JarEntry> e = jarFile.entries();


        //Based on http://www.devx.com/tips/Tip/22124
        while (e.hasMoreElements()) {
            JarEntry entry = e.nextElement();
            if (!entry.getName().endsWith(".json")) continue;   //Find the .json map.
            System.out.println(entry.getName());

            File file = new File(entry.getName());

            InputStream inputStream = jarFile.getInputStream(entry);
            FileOutputStream outputStream = new FileOutputStream(file);

            while(inputStream.available() > 0){ //write the file
                outputStream.write(inputStream.read());
            }

            outputStream.close();
            inputStream.close();

            jobject = new Gson().fromJson(new FileReader(file), JsonObject.class); //convert the file to be read into a JsonObject
            file.deleteOnExit();

            return jobject;


        }
        throw new IndexOutOfBoundsException("No map in the JAR file!");

    }

    private void checkType(String type, Tile[][] map, int row, int col){
        switch (type){
            case "_":
                map[row][col] = new floorTile(row, col, null);   //Define a floor tile.
            case "P":
                map[row][col] = new floorTile(row, col, null);   //Define a floor tile.
                this.player = new Player(row, col);
            case "▊":
                map[row][col] = new wallTile(row, col);
            case "T":
                map[row][col] = new floorTile(row, col, new Treasure(map[row][col]));   //Define a floor tile with a treasure in it
                this.treasures++;
            case "W":
                map[row][col] = new winTile(row, col);                                  //Define the win tile
            case "B":
                map[row][col]   = new floorTile(row, col, null);
                ((floorTile) map[row][col]).setBugTile();              //Set this tile to be part of the bug's movement
                this.bug = new Bug(row, col);                          //Define the bug.
            case "X":
                map[row][col] = new floorTile(row, col, null);   //Define a floor tile.
                ((floorTile) map[row][col]).setBugTile();              //Set this tile to be part of the bug's movement
        }

        if(type.substring(0,1).equals("D")){
            map[row][col] = new doorTile(row, col, type.substring(1,2));  //define a coloured door.
        }else if(type.substring(0,1).equals("K")) {
            this.keys++;
            map[row][col] = new floorTile(row, col, new Key(map[row][col],  type.substring(1,2)));  //define a coloured key
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
