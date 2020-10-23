package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Preconditions;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;

import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * Parsing a JSON file. This class can read and write a JSON file for loading
 * and saving.
 */
public class ParseJSON {

    private Player player;
    private Object bug;
    private Tile[][] map;
    private int treasures = 0, keys = 0;
    public Class<Object> aClass = null;
    public String directory;

    /**
     * This constructor reads a JSON file from a given directory. This object is able to return the map, player and the number of treasures the map has.
     *
     * @param directory - The file directory.
     */
    public ParseJSON(String directory) {
        this.directory = directory;
        try {
            JsonObject jContents;
            if (directory.contains("2")) {
                this.aClass = loadClass(directory);
                jContents = loadMap(directory);

            } else {
                jContents = new Gson().fromJson(new FileReader(directory), JsonObject.class);
            }

            assert jContents != null : "The map was not correctly loaded!";

            JsonArray jmap = jContents.getAsJsonArray("map");

            int totalSize = 30;
            this.map = new Tile[totalSize][totalSize];

            //initialize the level
            for (int row = 0; row < totalSize; row++) {
                for (int col = 0; col < totalSize; col++) {
                    String tileType = jmap.get((row * totalSize) + col).getAsString();
                    defineType(tileType, map, row, col); //Define this tile, at row and col, as a certain object to be stored in the map.
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("IO Exception!");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found!");
        }
    }

    /**
     * loadClass will load a class from the level 2 jar file.
     *
     * @param directory - The directory to the .json file
     * @return the given class
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Class<Object> loadClass(String directory) throws IOException, ClassNotFoundException {
        $Gson$Preconditions.checkNotNull(directory);

        String pathToJAR = directory.substring(0, directory.length() - ".json".length()) + ".jar";
        //From stackoverflow.com/a/11016392
        JarFile jarFile = new JarFile(pathToJAR);

        var e = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + pathToJAR + "!/")};
        ClassLoader classLoader = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) continue;

            String className = je.getName().substring(0, je.getName().length() - ".class".length());
            className = className.replace('/', '.');
            Class<Object> c = (Class<Object>) classLoader.loadClass(className);
            if (className.contains("Bug"))
                return c;
        }

        jarFile.close();
        throw new ClassNotFoundException();
    }

    /**
     * load the Map from the JAR file
     *
     * @param directory - The level number
     * @return the JSON map.
     * @throws IOException
     * @throws IndexOutOfBoundsException
     */
    private static JsonObject loadMap(String directory) throws IOException, IndexOutOfBoundsException {
        String pathToJAR = directory.substring(0, directory.length() - ".json".length()) + ".jar";

        JarFile jarFile = new JarFile(pathToJAR);
        Enumeration<JarEntry> e = jarFile.entries();

        //Based on http://www.devx.com/tips/Tip/22124
        while (e.hasMoreElements()) {
            JarEntry entry = e.nextElement();
            if (!entry.getName().endsWith(".json")) continue;   //Find the .json map.

            File file = new File(entry.getName());

            InputStream inputStream = jarFile.getInputStream(entry);
            FileOutputStream outputStream = new FileOutputStream(file);

            while (inputStream.available() > 0) { //write the file
                outputStream.write(inputStream.read());
            }

            outputStream.close();
            inputStream.close();

            JsonObject map = new Gson().fromJson(new FileReader(file), JsonObject.class); //convert the file to be read into a JsonObject
            file.deleteOnExit();

            return map;
        }
        jarFile.close();
        throw new IndexOutOfBoundsException("No map in the JAR file!");
    }

    /**
     * This method parses a string into a tile, which is then stored inside the map field.
     *
     * @param type - The type of tile.
     * @param map  - The map from a .json file.
     * @param row  - The current row.
     * @param col  - The current column.
     */
    private void defineType(String type, Tile[][] map, int row, int col) {
        switch (type) {
            default:
                map[row][col] = new FloorTile(row, col, null);   //Define a floor tile.
                break;
            case "P":
                map[row][col] = new FloorTile(row, col, null);   //Define a floor tile.
                this.player = new Player(row, col);
                break;
            case "▊":
                map[row][col] = new WallTile(row, col);                //Define a wall tile.
                break;
            case "T":
                map[row][col] = new FloorTile(row, col, new Treasure(map[row][col]));   //Define a floor tile with a treasure in it
                this.treasures++;
                break;
            case "W":
                map[row][col] = new WinTile(row, col);                                  //Define the win tile
                break;
            case "B":
                map[row][col] = new FloorTile(row, col, null);
                ((FloorTile) map[row][col]).setBugTile();              //Set this tile to be part of the bug's movement
                assert aClass != null : "This level does not have a valid class to load the bug!";
                try {
                    this.bug = aClass.getDeclaredConstructor(int.class, int.class).newInstance(row, col);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    System.out.println("The bug could not be created during parsing");
                }
                break;
            case "X":
                map[row][col] = new FloorTile(row, col, null);   //Define a floor tile.
                ((FloorTile) map[row][col]).setBugTile();              //Set this tile to be part of the bug's movement
                break;
            case "DC":                                                  //Define a chip tile, which opens if all treasure chips are collected.
                map[row][col] = new TreasureDoor(row, col);
                break;
            case "I":
                map[row][col] = new InfoTile(row, col);                  //Define an info tile.
                break;
            case "R":
                map[row][col] = new RechargeTile(row, col);              //Define a recharge tile.
                break;
        }

        if (type.substring(0, 1).equals("D") && !type.equals("DC")) {
            map[row][col] = new DoorTile(row, col, type.substring(1, 2));  //define a coloured door.
        } else if (type.substring(0, 1).equals("K")) {
            this.keys++;
            map[row][col] = new FloorTile(row, col, new Key(map[row][col], type.substring(1, 2)));  //define a coloured key
        }
    }

    /**
     * Load an image from a JAR file.
     *
     * @param imgName - The name of the image.
     * @return the image, otherwise throw an exception and catch it.
     */
    public Image loadImage(String imgName) {
        String pathToJAR = directory.substring(0, directory.length() - ".json".length()) + ".jar";
        try {
            JarFile jarFile = new JarFile(pathToJAR);
            Enumeration<JarEntry> e = jarFile.entries();

            //Based on http://www.devx.com/tips/Tip/22124
            while (e.hasMoreElements()) {
                JarEntry entry = e.nextElement();

                if (entry.getName().equals(imgName)) {
                    InputStream inputStream = jarFile.getInputStream(entry);
                    return Toolkit.getDefaultToolkit().createImage(inputStream.readAllBytes());
                }
            }
            jarFile.close();
        } catch (Exception e) {
            System.out.println("There was an error in loading an image");
        }
        throw new IndexOutOfBoundsException(imgName + " was not found");
    }

    /**
     * Get the player.
     *
     * @return the player.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get the map.
     *
     * @return the map.
     */
    public Tile[][] getMap() {
        return this.map;
    }

    /**
     * Get the treasure.
     *
     * @return the treasure.
     */
    public int getTreasures() {
        return this.treasures;
    }

    /**
     * Get the bug.
     *
     * @return the bug.
     */
    public Object getBug() {
        return this.bug;
    }

    /**
     * Get the number of keys that are in the current level.
     *
     * @return the number of keys.
     */
    public int getNumberOfKeys() {
        return this.keys;
    }

}