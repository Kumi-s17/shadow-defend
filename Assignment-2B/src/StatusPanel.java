import bagel.*;
import bagel.util.Colour;

/**
 * StatusPanel, a panel displayed at the bottom of the window
 */
public class StatusPanel {
    private static final Image PANEL = new Image("res/images/statuspanel.png");
    private static final double STATUS_PANEL_TOP_LEFT_Y = Window.getHeight() - PANEL.getHeight();

    private String waveNumberDisplayed;
    private String timeScaleDisplayed;
    private String statusDisplayed;
    private String numberOfLivesDisplayed;

    //For fonts used in the panel
    private static final int FONT_SIZE = 10;
    private static final Font FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", FONT_SIZE);
    private static final int BOTTOM_LEFT_Y = Window.getHeight() - 15;
    private static final int WAVE_BOTTOM_LEFT_X = 5;
    private static final int TIME_SCALE_BOTTOM_LEFT_X = 250;
    private static final int STATUS_BOTTOM_LEFT_X = 500;
    private static final int LIVES_BOTTOM_LEFT_X = Window.getWidth() - 110;


    public static double getStatusPanelTopLeftY() {
        return STATUS_PANEL_TOP_LEFT_Y;
    }

    /**
     * updates what is shown on the status panel
     *
     * @param level   The current level of the game
     * @param timeScale   The current timescale of the game
     */
    public void updateStatusPanel(Level level, double timeScale) {

        //visualise the background of the status panel
        PANEL.drawFromTopLeft(0, STATUS_PANEL_TOP_LEFT_Y );

        //Display as wave 1 before first wave is started.
        if(level.getCurrentWaveNumber()  == 0) {
            waveNumberDisplayed = "Wave: " + (level.getCurrentWaveNumber() + 1);
        }
        else{
            waveNumberDisplayed = "Wave: " + level.getCurrentWaveNumber();
        }
        timeScaleDisplayed = "TimeScale: " + timeScale;
        statusDisplayed = "Status: " + checkStatus(level);
        numberOfLivesDisplayed = "Lives: " + level.getNumberOfLives();

        // visualises information of the status panel
        FONT.drawString(waveNumberDisplayed, WAVE_BOTTOM_LEFT_X , BOTTOM_LEFT_Y);
        FONT.drawString(timeScaleDisplayed, TIME_SCALE_BOTTOM_LEFT_X , BOTTOM_LEFT_Y, setTimescaleColour(timeScale));
        FONT.drawString(statusDisplayed, STATUS_BOTTOM_LEFT_X , BOTTOM_LEFT_Y);
        FONT.drawString(numberOfLivesDisplayed, LIVES_BOTTOM_LEFT_X , BOTTOM_LEFT_Y);


    }

    /**
     * checks and updates the current status of the game
     *
     * @param level   The current level of the game
     * @return   The current status of the game
     */
    public String checkStatus(Level level) {
        String status;
        // If wave has not started
        if(!level.isWaveStarted()){
            status = "Awaiting Start";
        }
        else{
            status = "Wave In Progress";
        }
        // If tower in buy panel is selected and can be purchased
        if(BuyPanel.isItemSelected()){
            status = "Placing";
        }
        // If it is the end of level of the last level of the game, the player has won
        else if(level.isEndOfLevel() && level.getLevelNumber() == ShadowDefend.getNumberOfLevels()){
            status = "Winner!";
        }
        return status;
    }



    /**
     * Set the colour of the timescale display in the status panel to white if timescale is 1, and green if other wise.
     *
     * @param timeScale  The current timescale of the game
     * @return  the colour to which the timescale is to be set
     */
    public DrawOptions setTimescaleColour(double timeScale) {
        DrawOptions colour = new DrawOptions();
        //timescale is greater than 1 so render green
        if(timeScale > 1){
            colour.setBlendColour(Colour.GREEN);
        }
        //timescale is 1 so render white
        else{
            colour.setBlendColour(Colour.WHITE);
        }
        return colour;
    }

}
