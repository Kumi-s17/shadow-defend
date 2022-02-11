//package lists;

import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * ShadowDefend, a tower defence game
 *
 * @author Project 1 sample solution
 */
public class ShadowDefend extends AbstractGame {
    private int levelNumber = 1;
    private WaveEvent currentEvent;
    private Level level;
    private double frameCount;
    private static final double FPS = 60;
    private static final int NUMBER_OF_LEVELS = 2;
    private static int windowHeight;
    private static int windowWidth;
    private String mapFile = "res/levels/" + levelNumber +".tmx";
    private static final String TEXT_FILE = "res/levels/waves.txt";
    private static final String BLOCKED_PROPERTY = "blocked";
    private boolean rewarded = false;

    private final BuyPanel buyPanel;
    private final StatusPanel statusPanel;

    private static final int MAX_TIMESCALE = 5;
    private static final int INTIAL_TIMESCALE = 1;
    // Timescale is made static because it is a universal property of the game and the specification
    // says everything in the game is affected by this
    private static int timescale = INTIAL_TIMESCALE;



    /**
     * Creates a new instance of the ShadowDefend game
     */
    public ShadowDefend() {
        this.level = new Level(TEXT_FILE, new TiledMap(mapFile), levelNumber);
        this.buyPanel = new BuyPanel();
        this.statusPanel = new StatusPanel();
        this.frameCount = 0;
        this.windowHeight = Window.getHeight();
        this.windowWidth = Window.getWidth();
    }

    /**
     * The entry-point for the game
     *
     * @param args Optional command-line arguments
     */
    public static void main(String[] args) {
        new ShadowDefend().run();
    }

    public static int getTimescale() {
        return timescale;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static double getFPS() {
        return FPS;
    }

    public static int getNumberOfLevels() {
        return NUMBER_OF_LEVELS;
    }

    //Increases the timescale
    private void increaseTimescale() {
        if(timescale < MAX_TIMESCALE) {
            timescale++;
        }
    }


    //Decreases the timescale but doesn't go below the base timescale
    private void decreaseTimescale() {
        if (timescale > INTIAL_TIMESCALE) {
            timescale--;
        }
    }

    /**
     * Update the state of the game, potentially reading from input
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    protected void update(Input input) {
        // Increase the frame counter by the current timescale
        frameCount += timescale;

        // Visualises map
        Level.getMap().draw(0, 0, 0, 0, windowWidth, windowHeight);



        // Handle key presses
        if (input.wasPressed(Keys.S)) {
            // if we are awaiting start of a wave
            if (!level.isWaveStarted()) {
                level.setCurrentWaveNumber(level.getCurrentWaveNumber() + 1);
                // get the wave events for the current wave
                level.processWave();
                rewarded = false;
                level.setWaveStarted(true);
                // get the first wave event of that wave
                currentEvent = level.getCurrentWaveEvents().get(0);
                // reset frame count
                frameCount = 0;
            }
        }

        if (input.wasPressed(Keys.L)) {
            increaseTimescale();
        }

        if (input.wasPressed(Keys.K)) {
            decreaseTimescale();
        }

        //handle mouse clicks
        if (input.isDown(MouseButtons.LEFT)) {
            // select tower if the tower was clicked and it can be purchased
            BuyPanel.selectTower(level, input);
        }

        if (input.isDown(MouseButtons.RIGHT)) {
            // deselect tower if it was selected from the buy panel
            BuyPanel.setItemSelected(false);
        }

        // checks if a tower to be purchased has been selected.
        if (BuyPanel.isItemSelected()) {
            // if centre of tower is not intersecting with the panels.
            if (input.getMousePosition().y > BuyPanel.getBuyPanelHeight()
                    && input.getMousePosition().y < StatusPanel.getStatusPanelTopLeftY()) {
                if (BuyPanel.getTowerSelected().equals("airplane")){
                    // visualise visual indicator for airplane
                    BuyPanel.getVisualiseImage().draw(input.getMousePosition().x, input.getMousePosition().y);
                    // if left clicked when airplane is chosen
                    if (input.isDown(MouseButtons.LEFT)) {
                        // purchase the airplane
                        level.getTowersOnMap().add(new Airplane(Airplane.getStartPoint(input.getMousePosition())));
                        // update amount of money the player has after purchase
                        level.setCash(level.getCash() - BuyPanel.getAirplanePrice());
                        BuyPanel.setItemSelected(false);
                    }
                }
                // check if centre of tower is not intersecting with the bounding box of another tower.
                // (only check for active towers)
                else if(!Tower.intersectsTower(level, input)) {
                    //checks if that area of map is not blocked. (only check for passive towers)
                    if (!level.getMap().getPropertyBoolean((int) input.getMousePosition().x,
                                                         (int) input.getMousePosition().y, BLOCKED_PROPERTY, false)) {
                        // visualise visual indicator for tank or super tank
                        BuyPanel.getVisualiseImage().draw(input.getMousePosition().x, input.getMousePosition().y);
                        //if active tower is selected and can be placed at the location that it was clicked again.
                        if (input.isDown(MouseButtons.LEFT)) {
                            if (BuyPanel.getTowerSelected().equals("tank")) {
                                // purchase the tank
                                level.getTowersOnMap().add(new Tank(input.getMousePosition()));
                                // update amount of money the player has after purchase
                                level.setCash(level.getCash() - BuyPanel.getTankPrice());
                            } else if (BuyPanel.getTowerSelected().equals("supertank")) {
                                // purchase the super tank
                                level.getTowersOnMap().add(new SuperTank(input.getMousePosition()));
                                // update amount of money the player has after purchase
                                level.setCash(level.getCash() - BuyPanel.getSupertankPrice());
                            }
                            BuyPanel.setItemSelected(false);
                        }
                    }

                }
            }
        }


        //if current wave is not over
        if(!level.getCurrentWaveEvents().isEmpty()) {
            if (currentEvent.getWaveType().equals("spawn")) {
                // Spawn slicer if it is the first slicer to be spawned or
                // if the spawn delay has been passed after spawning of the last slicer(and we have some left to spawn)
                if (currentEvent.getSpawnedSlicers() == 0 || (frameCount / FPS >= currentEvent.getSpawnDelay()
                                            && currentEvent.getSpawnedSlicers() < currentEvent.getNumberToSpawn())) {
                    level.getSlicers().add(Slicer.addNewSlicer(currentEvent, level.getPolyline()));
                    currentEvent.setSpawnedSlicers(currentEvent.getSpawnedSlicers() + 1);
                    // Reset frame counter
                    frameCount = 0;
                }
                //if spawn event has ended (last slicer has been spawned)
                if (currentEvent.getSpawnedSlicers() == currentEvent.getNumberToSpawn()) {
                    // remove the spawn event
                    level.getCurrentWaveEvents().remove(0);
                    //if spawn event ended but wave has not ended
                    if (!level.getCurrentWaveEvents().isEmpty()) {
                        // get next wave event
                        currentEvent = level.getCurrentWaveEvents().get(0);
                    }
                }
            } else if (currentEvent.getWaveType().equals("delay")) {
                // if delay event has ended, remove that event
                if (frameCount / FPS >= currentEvent.getDelay()) {
                    level.getCurrentWaveEvents().remove(0);
                    // Reset frame counter
                    frameCount = 0;
                    //if delay event ended but wave has not ended
                    if (!level.getCurrentWaveEvents().isEmpty()) {
                        // get next wave event
                        currentEvent = level.getCurrentWaveEvents().get(0);
                    }
                }
            }
        }
        //if current wave is over and next wave not started or first wave has not been started.
        else {
            //if at least one wave has completed, there are no more slicers on the map and
            // the player has not been rewarded.
            if(level.getCurrentWaveNumber() != 0 && !rewarded && level.getSlicers().isEmpty()) {
                WaveEvent.rewardCash(level);
                rewarded = true;
                level.setWaveStarted(false);
            }
            // If we reached the end of a level and there are no slicers on the map.
            if (level.isEndOfLevel()) {
                levelNumber += 1;
                // If game still has a new level (game has not been won yet), start new level
                if (levelNumber <= NUMBER_OF_LEVELS) {
                    mapFile = "res/levels/" + levelNumber +".tmx";
                    level = new Level(TEXT_FILE, new TiledMap(mapFile), levelNumber);
                    //If a tower was selected for purchase in the previous level, deselect it.
                    BuyPanel.setItemSelected(false);
                }
            }
        }


        // Update all slicers.
        for (int i = level.getSlicers().size() - 1; i >= 0; i--) {
            Slicer s = level.getSlicers().get(i);
            s.update(input);
            // Deduct the penalty number of lives if slicer reaches end of map
            if (s.isFinished()) {
                level.setNumberOfLives(level.getNumberOfLives() - s.getPenalty());
                level.getSlicers().remove(i);
            }
            // Reward the player if the slicer is eliminated and remove that slicer
            else if(s.isEliminated()){
                level.setCash(level.getCash() + s.getReward());
                s.spawnChildSlicer();
                level.getSlicers().remove(i);
            }
        }

        // Update all towers
        for (int i = level.getTowersOnMap().size() - 1; i >= 0; i--) {
            Tower t = level.getTowersOnMap().get(i);
            t.update(input);
        }

        // Visualises buy panel and status panel
        buyPanel.updatePanel(level.getCash());
        statusPanel.updateStatusPanel(level, timescale);

        // Close game if the player's life reaches zero (if game is lost)
        if (level.getNumberOfLives() <= 0) {
            Window.close();
        }


    }
}
