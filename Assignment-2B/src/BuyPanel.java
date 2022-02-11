import bagel.util.Point;
import bagel.util.Colour;
import bagel.util.Rectangle;
import bagel.*;
import java.util.*;

import java.util.List;

/**
 * BuyPanel, a panel displayed at the top of the window
 */
public class BuyPanel {
    private static final double WINDOW_WIDTH = Window.getWidth();
    private static final double BUY_PANEL_HEIGHT = 100;
    private static final double BUY_PANEL_WIDTH = Window.getWidth();
    private static final double FIRST_IMAGE_X = 64;
    private static final double INTERVAL_BETWEEN_IMAGES = 120;
    private static final double ABOVE_CENTRE_BUY_PANEL = 10;
    private static final double TOWERS_Y = BUY_PANEL_HEIGHT/2 - ABOVE_CENTRE_BUY_PANEL;



    //Images of the purchase items.
    private static final Image PANEL = new Image("res/images/buypanel.png");
    private static final Image TANK_ITEM = new Image("res/images/tank.png");
    private static final Image SUPERTANK_ITEM = new Image("res/images/supertank.png");
    private static final Image AIRPLANE_ITEM = new Image("res/images/airsupport.png");
    private static final Point TANK_LOCATION = new Point(FIRST_IMAGE_X ,TOWERS_Y);
    private static final Point SUPERTANK_LOCATION = new Point(FIRST_IMAGE_X + INTERVAL_BETWEEN_IMAGES , TOWERS_Y);
    private static final Point AIRPLANE_LOCATION = new Point(FIRST_IMAGE_X + 2 * INTERVAL_BETWEEN_IMAGES , TOWERS_Y);

    //Money indicator
    private String money;
    private static final int MONEY_FONT_SIZE = 48;
    private static final Font MONEY_FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", MONEY_FONT_SIZE);
    private static final double MONEY_INDICATOR_X = Window.getWidth() - 200 ;
    private static final double MONEY_INDICATOR_Y = 65;


    //Key binds
    private static final int KEYBINDS_FONT_SIZE = 14;
    private final Font KEYBINDS_FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", KEYBINDS_FONT_SIZE);
    private static final String KEYBINDS = "Key binds:";
    private static final String KEYBINDS_START_WAVE = "S - Start Wave";
    private static final String KEYBINDS_INCREASE_TIMESCALE = "L - Increase Timescale";
    private static final String KEYBINDS_DECREASE_TIMESCALE = "K - Decrease Timescale";
    private static final double KEYBINDS_X = BUY_PANEL_WIDTH/2 - 50;
    private static final double KEYBINDS_Y = 17;
    private static final double KEYBINDS_START_WAVE_Y = KEYBINDS_Y * 3;
    private static final double KEYBINDS_INCREASE_TIMESCALE_Y = KEYBINDS_Y * 4;
    private static final double KEYBINDS_DECREASE_TIMESCALE_Y = KEYBINDS_Y * 5;

    // Price of items
    private static final int PRICE_FONT_SIZE = 20;
    private final Font PRICE_FONT = new Font("res/fonts/DejaVuSans-Bold.ttf", PRICE_FONT_SIZE);
    private static final int TANK_PRICE = 250;
    private static final int SUPERTANK_PRICE = 600;
    private static final int AIRPLANE_PRICE = 500;
    private String tankPrice = "$" + TANK_PRICE;
    private String superTankPrice = "$" + SUPERTANK_PRICE;
    private String airplanePrice = "$" + AIRPLANE_PRICE;
    private static final double PRICE_BOTTOM_LEFT_Y = 90;
    private static final double TANK_PRICE_BOTTOM_LEFT_X = 40;


    private static final Point TANK_PRICE_BOTTOM_LEFT = new Point(TANK_PRICE_BOTTOM_LEFT_X ,PRICE_BOTTOM_LEFT_Y);
    private static final Point SUPERTANK_PRICE_BOTTOM_LEFT =
            new Point(TANK_PRICE_BOTTOM_LEFT_X + INTERVAL_BETWEEN_IMAGES , PRICE_BOTTOM_LEFT_Y);
    private static final Point AIRPLANE_PRICE_BOTTOM_LEFT =
            new Point(TANK_PRICE_BOTTOM_LEFT_X + 2 * INTERVAL_BETWEEN_IMAGES , PRICE_BOTTOM_LEFT_Y);

    private static final Rectangle TANK_ON_BUY_PANEL = TANK_ITEM.getBoundingBoxAt(TANK_LOCATION);
    private static final Rectangle SUPERTANK_ON_BUY_PANEL = SUPERTANK_ITEM.getBoundingBoxAt(SUPERTANK_LOCATION);
    private static final Rectangle AIRPLANE_ON_BUY_PANEL = AIRPLANE_ITEM.getBoundingBoxAt(AIRPLANE_LOCATION);



    // Visualisation and purchase of towers onto the map
    private static boolean itemSelected = false;
    private static Image visualiseImage;
    private static String towerSelected;

    public static int getTankPrice() {
        return TANK_PRICE;
    }

    public static int getSupertankPrice() {
        return SUPERTANK_PRICE;
    }

    public static int getAirplanePrice() {
        return AIRPLANE_PRICE;
    }

    public static Image getTankItem() {
        return TANK_ITEM;
    }

    public static Image getSupertankItem() {
        return SUPERTANK_ITEM;
    }

    public static double getBuyPanelHeight() {
        return BUY_PANEL_HEIGHT;
    }

    /**
     *
     * @return true if item on the buy panel has been selected and can be bought
     */
    public static boolean isItemSelected() {
        return itemSelected;
    }

    public static void setItemSelected(boolean itemSelected) {
        BuyPanel.itemSelected = itemSelected;
    }

    public static Image getVisualiseImage() {
        return visualiseImage;
    }

    public static String getTowerSelected() {
        return towerSelected;
    }


    /**
     * updates what is shown on the buy panel
     *
     * @param cash amount of money the player currently has
     */
    public void updatePanel(double cash){
        PANEL.drawFromTopLeft(0, 0);

        // displays images of towers that can be purchased on the buy panel
        TANK_ITEM.draw(TANK_LOCATION.x, TANK_LOCATION.y);
        SUPERTANK_ITEM.draw(SUPERTANK_LOCATION.x, SUPERTANK_LOCATION.y);
        AIRPLANE_ITEM.draw(AIRPLANE_LOCATION.x, AIRPLANE_LOCATION.y);

        // displays the amount of money the player currently has
        money = "$" + (int) cash;
        MONEY_FONT.drawString(money, MONEY_INDICATOR_X , MONEY_INDICATOR_Y);

        //displays the price of each tower that can be purchased
        PRICE_FONT.drawString(tankPrice, TANK_PRICE_BOTTOM_LEFT.x , TANK_PRICE_BOTTOM_LEFT.y,
                                                                               setDisplayColour(cash, TANK_PRICE));
        PRICE_FONT.drawString(superTankPrice, SUPERTANK_PRICE_BOTTOM_LEFT.x , SUPERTANK_PRICE_BOTTOM_LEFT.y,
                                                                            setDisplayColour(cash, SUPERTANK_PRICE));
        PRICE_FONT.drawString(airplanePrice, AIRPLANE_PRICE_BOTTOM_LEFT.x , AIRPLANE_PRICE_BOTTOM_LEFT.y,
                                                                             setDisplayColour(cash, AIRPLANE_PRICE));

        // displays the key binds onto the buy panel
        KEYBINDS_FONT.drawString(KEYBINDS, KEYBINDS_X, KEYBINDS_Y);
        KEYBINDS_FONT.drawString(KEYBINDS_START_WAVE, KEYBINDS_X, KEYBINDS_START_WAVE_Y);
        KEYBINDS_FONT.drawString(KEYBINDS_INCREASE_TIMESCALE, KEYBINDS_X, KEYBINDS_INCREASE_TIMESCALE_Y);
        KEYBINDS_FONT.drawString(KEYBINDS_DECREASE_TIMESCALE, KEYBINDS_X, KEYBINDS_DECREASE_TIMESCALE_Y);


    }

    /**
     * If the player has enough money, the price of the tower will be shown green and red other wise
     *
     * @param cash amount of money the player currently has
     * @param price price of a tower
     * @return the colour to which the price of the tower is to be set
     */
    public DrawOptions setDisplayColour(double cash, int price) {
        DrawOptions colour = new DrawOptions();
        //have enough funds so render green
        if(price <= cash){
            colour.setBlendColour(Colour.GREEN);
        }
        //not enough funds so render red
        else{
            colour.setBlendColour(Colour.RED);
        }
        return colour;
    }

    /**
     * Allows the player to select the a tower if they click on it in the buy panel and have enough money to purchase
     *
     * @param level the current level the game is at
     * @param input The current mouse/keyboard state
     */
    public static void selectTower(Level level, Input input){

        //If there is no item selected already and a tank is clicked.
        if (TANK_ON_BUY_PANEL.intersects(input.getMousePosition()) && itemSelected == false) {
            if (level.getCash() >= TANK_PRICE) {
                //image to visualise at the mouse point
                visualiseImage = TANK_ITEM;
                itemSelected = true;
                towerSelected = "tank";
            }
        }
        //If there is no item selected already and a supertank is clicked.
        else if (SUPERTANK_ON_BUY_PANEL.intersects(input.getMousePosition()) && itemSelected == false) {
            if (level.getCash() >= SUPERTANK_PRICE) {
                //image to visualise at the mouse point
                visualiseImage = SUPERTANK_ITEM;
                itemSelected = true;
                towerSelected = "supertank";
            }
        }
        //If there is no item selected already and an airplane is clicked.
        else  if (AIRPLANE_ON_BUY_PANEL.intersects(input.getMousePosition()) && itemSelected == false) {
            if (level.getCash() >= AIRPLANE_PRICE) {
                //image to visualise at the mouse point
                visualiseImage = AIRPLANE_ITEM;
                itemSelected = true;
                towerSelected = "airplane";
            }
        }
    }
}
