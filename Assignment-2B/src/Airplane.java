import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Airplane, a passive tower in the game
 */
public class Airplane extends Tower {
    private static final String IMAGE_FILE = "res/images/airsupport.png";
    private static final int SPEED = 3;
    private static final double OTHER_COORDINATE = - 100;
    private boolean finished;
    private static Point airplaneStartPoint;
    private Point targetPoint;
    private Vector2 directionVector;
    private double airplaneFrameCount;
    private double dropTime;
    private ArrayList<Explosive> explosives;


    /**
     * creates a new Airplane
     *
     * @param startPoint the point where the airplane starts flying from
     */
    public Airplane(Point startPoint) {
        super(IMAGE_FILE, SPEED, startPoint);
        this.targetPoint = getTargetPoint(startPoint);
        this.airplaneFrameCount = 0;
        this.explosives = new ArrayList<>();
        this.finished = false;
    }

    /**
     * gets the start point of the airplane from the location it was placed by the player
     *
     * @param mousePoint the point where the plane was placed on the map upon purchase
     * @return point where the airplane starts flying from
     *
     */
    public static Point getStartPoint(Point mousePoint){
        //odd numbered airplane travels horizontally
       if((Level.getNumberOfAirplanes() + 1) % 2 == 1){
          airplaneStartPoint = new Point(OTHER_COORDINATE, mousePoint.y);
       }
        //even numbered airplane travels vertically
        else if((Level.getNumberOfAirplanes() + 1) % 2 == 0){
          airplaneStartPoint = new Point(mousePoint.x, OTHER_COORDINATE);

        }
        return airplaneStartPoint;
    }

    /**
     * gets the coordinates of final destination the plane is heading towards
     *
     * @param startPoint point where the airplane starts flying from
     * @return  point where the airplane flies towards
     */
    public Point getTargetPoint(Point startPoint){
        //odd numbered airplane travels horizontally
        if((Level.getNumberOfAirplanes() + 1) % 2 == 1){
            targetPoint = new Point(ShadowDefend.getWindowWidth(), startPoint.y);
        }
        //even numbered airplane travels vertically
        else if((Level.getNumberOfAirplanes() + 1) % 2 == 0){
            targetPoint = new Point(startPoint.x, ShadowDefend.getWindowHeight());
        }
        //increments the number of planes that were purchased in that level
        Level.setNumberOfAirplanes(Level.getNumberOfAirplanes() + 1);
        return targetPoint;
    }


    /**
     * Updates the current state of the airplane, potentially reading from input
     *
     * @param input input from the keyboard or mouse
     *
     */
    @Override
    public void update(Input input) {
        //Keeps track frame counter after the airplane is purchased
        airplaneFrameCount += ShadowDefend.getTimescale();
        if (finished) {
            return;
        }
        // Obtain where the airplane is currently flying
        Point currentPoint = getCenter();
        Vector2 directionVector = targetPoint.asVector().sub(currentPoint.asVector());
        // If the plane cannot travel in its direction of travel anymore, it has finished
        if(directionVector.x < 0 || directionVector.y < 0){
            finished = true;
            return;
        }
        // Move towards the end point of the airplane
        // We do this by getting a unit vector in the direction of the finish point of the airplane,
        // and multiplying it by the speed of the airplane (accounting for the timescale)
        super.move(directionVector.normalised().mul(SPEED * ShadowDefend.getTimescale()));

        // Update current rotation angle to face end point of the airplane
        // add PI/2 because image of plane is originally facing upwards
        setAngle(Math.PI/2 + Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x));

        super.update(input);

        // Check if it is time to drop an explosive
        if(airplaneFrameCount / ShadowDefend.getFPS() >= dropTime) {
            // a new explosive is dropped
            explosives.add(new Explosive(currentPoint));
            airplaneFrameCount = 0;
            // get a new drop time
            dropTime = (int) ( Math.random() * 2 + 1);
        }
        // Update all explosives, and remove them if they've finished
        for (int i = explosives.size() - 1; i >= 0; i--) {
            Explosive e = explosives.get(i);
            e.update(input);
            // remove explosives that have detonated from the map
            if (e.hasExploded()) {
                explosives.remove(i);
            }
        }


    }

    /**
     *
     * @return true if the airplane has reached the end of the map
     */
    public boolean isFinished() {
        return finished;
    }

}
