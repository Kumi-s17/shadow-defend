import bagel.AbstractGame;
import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Game extends AbstractGame {

    private final TiledMap map;
    private final Image house;
    private final Image player;
    private final Point houseLocation;
    private final Point doorLocation;
    private final Font conformableFont;
    private static final double CHARACTER_MOVEMENT_SPEED = 5;
    private static final int FONT_SIZE = 48;
    private static final int WELCOME_DISTANCE = 100;
    private static final String WELCOME_MESSAGE = "Welcome home!";
    private static final String BLOCKED_PROPERTY = "blocked";
    private Point characterLocation;

    /**
     * Entry point for Bagel game
     *
     * Explore the capabilities of Bagel: https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/
     */
    public static void main(String[] args) {
        // Create new instance of game and run it
        new Game().run();
    }

    /**
     * Setup the game
     */
    public Game() {
        map = new TiledMap("res/map/map.tmx");
        house = new Image("res/images/house.png");
        player = new Image("res/images/player.png");
        characterLocation = new Point(50, 350);
        houseLocation = new Point(850, 180);
        doorLocation = new Point(854, 268);
        conformableFont = new Font("res/fonts/conformable.otf", FONT_SIZE);
    }

    /**
     * Checks if a given point is valid. A point is valid if it is within the screen bounds and does
     * not intersect with a blocking tile.
     *
     * @param point The point to validate
     * @return true if the point is valid, else false
     */
    private boolean validPoint(Point point) {
        boolean invalidX = point.x < 0 || point.x > Window.getWidth();
        boolean invalidY = point.y < 0 || point.y > Window.getHeight();
        boolean outOfBounds = invalidX || invalidY;
        if (outOfBounds) {
            return false;
        }
        return !map.getPropertyBoolean((int) point.x, (int) point.y, BLOCKED_PROPERTY, false);
    }

    /**
     * Updates the game state approximately 60 times a second, potentially reading from input.
     *
     * @param input The input instance which provides access to keyboard/mouse state information.
     */
    @Override
    protected void update(Input input) {
        map.draw(0, 0, 0, 0, Window.getWidth(), Window.getHeight());
        house.draw(houseLocation.x, houseLocation.y);
        // Handle movement
        double newX = characterLocation.x;
        double newY = characterLocation.y;
        if (input.isDown(Keys.UP)) {
            newY -= CHARACTER_MOVEMENT_SPEED;
        }
        if (input.isDown(Keys.LEFT)) {
            newX -= CHARACTER_MOVEMENT_SPEED;
        }
        if (input.isDown(Keys.DOWN)) {
            newY += CHARACTER_MOVEMENT_SPEED;
        }
        if (input.isDown(Keys.RIGHT)) {
            newX += CHARACTER_MOVEMENT_SPEED;
        }
        Point newPlayerPoint = new Point(newX, newY);
        // Calculate the future bounding box for the player if they moved to the newPlayerPoint
        Rectangle newBoundingBox = player.getBoundingBoxAt(newPlayerPoint);
        boolean validBR = validPoint(newBoundingBox.bottomRight());
        boolean validTR = validPoint(newBoundingBox.topRight());
        boolean validTL = validPoint(newBoundingBox.topLeft());
        boolean validBL = validPoint(newBoundingBox.bottomLeft());
        // Only update character location if the corners of the bounding box at new point are valid
        if (validBL && validBR && validTL && validTR) {
            characterLocation = new Point(newX, newY);
        }
        if (doorLocation.distanceTo(characterLocation) <= WELCOME_DISTANCE) {
            // Calculate render X for coordinate (middle of the screen)
            double welcomeMessageWidth = conformableFont.getWidth(WELCOME_MESSAGE);
            double welcomeX = Window.getWidth() / 2.0 - welcomeMessageWidth / 2.0;
            // Draw the message to the screen using the loaded font
            conformableFont.drawString(WELCOME_MESSAGE, welcomeX, 50);
        }
        // Draw player to the screen!
        player.draw(characterLocation.x, characterLocation.y);
    }
}