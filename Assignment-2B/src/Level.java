import java.util.ArrayList;
import java.util.List;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * A Level in a game
 */
public class Level {
    private int numberOfLives;
    private double cash;
    private int currentWaveNumber;
    private int levelNumber;
    private final ArrayList<WaveEvent> currentWaveEvents;
    private static int numberOfAirplanes;
    private ArrayList <Tower> towersOnMap;
    private static ArrayList<Slicer> slicers;
    private static final ArrayList<String[]> ALL_WAVE_EVENTS = new ArrayList<>();
    private static String[] waveEvent;
    private boolean waveStarted;
    private int allCurrentWaveItems;
    private static TiledMap map;
    private List <Point> polyline;

    /**
     * creates a new level in the game
     *
     * @param textFile text file specifying the waves in a level
     * @param map the map used in the level of the game
     * @param levelNumber the current level number of the game
     */
    public Level(String textFile, TiledMap map, int levelNumber){
        this.map = map;
        this.polyline = map.getAllPolylines().get(0);
        this.levelNumber = levelNumber;
        this.numberOfLives = 25;
        this.cash = 500;
        this.currentWaveNumber = 0;
        this.numberOfAirplanes = 0;
        this.currentWaveEvents = new ArrayList<>();
        Level.readTextFile(textFile);
        this.slicers = new ArrayList<>();
        this.towersOnMap = new ArrayList<>();
        this.waveStarted = false;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<Point> getPolyline() {
        return polyline;
    }

    public static TiledMap getMap() {
        return map;
    }


    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }

    public int getCurrentWaveNumber() {
        return currentWaveNumber;
    }

    public void setCurrentWaveNumber(int currentWaveNumber) {
        this.currentWaveNumber = currentWaveNumber;
    }

    public static int getNumberOfAirplanes() {
        return numberOfAirplanes;
    }

    public static void setNumberOfAirplanes(int numberOfAirplanes) {
        Level.numberOfAirplanes = numberOfAirplanes;
    }

    public ArrayList<WaveEvent> getCurrentWaveEvents() {
        return currentWaveEvents;
    }

    public static ArrayList<Slicer> getSlicers() {
        return slicers;
    }

    public ArrayList<Tower> getTowersOnMap() {
        return towersOnMap;
    }

    /**
     * @return true if wave is in progress
     */
    public boolean isWaveStarted() {
        return waveStarted;
    }

    public void setWaveStarted(boolean waveStarted) {
        this.waveStarted = waveStarted;
    }

    /**
     *
     * @return true if the level has ended
     */
    public boolean isEndOfLevel() {
        // if there are no more wave events to read in and no more slicers on the map
        if(ALL_WAVE_EVENTS.isEmpty() && slicers.size() == 0){
            return true;
        }
        return false;
    }

    /**
     * reads in the text file and stores wave events as an array list of wave events
     *
     * @param textFile text file specifying the waves in a level
     */
    public static void readTextFile(String textFile){
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            String text = null;
            // while there are still lines that have not been read in waves.txt
            while ((text = br.readLine()) != null) {
                waveEvent = text.split(",");
                ALL_WAVE_EVENTS.add(waveEvent);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * reads in wave events for the current wave and creates spawn events and delay events for that wave
     */
    public void processWave(){
        for(int i = ALL_WAVE_EVENTS.size() - 1; i >= 0; i--){
            String[] event = ALL_WAVE_EVENTS.get(i);
            // taking in values only for those in the current wave
            if(Integer.parseInt(event[0]) == currentWaveNumber){
                if(event[1].equals("spawn")){
                    WaveEvent spawnEvent = new WaveEvent(Integer. parseInt(event[2]),event[3],
                                                                          Double.parseDouble(event[4]));
                    // Adding wave events from the end so whatever that gets added
                    // gets added to the first index of the list
                    currentWaveEvents.add(0, spawnEvent);
                }
                else if(event[1].equals("delay")){
                    WaveEvent delayEvent = new WaveEvent(Double.parseDouble(event[2]));
                    // Adding wave events from the end so whatever that gets added
                    // gets added to the first index of the list
                    currentWaveEvents.add(0, delayEvent);
                }
                ALL_WAVE_EVENTS.remove(i);
            }
        }

    }
}

