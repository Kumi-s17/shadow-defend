
import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * The parent class for all slicers
 *
 * @author Project 1 sample solution
 */
public abstract class Slicer extends Sprite {

    private String imageFile;
    private double speed;
    private int health;
    private int reward;
    private int penalty;
    private List<Point> polyline;
    private int targetPointIndex;
    private boolean finished;
    private List <Point> updatedRoute = new ArrayList<>();

    /**
     * The parent class for all slicers (RegularSlicer, SuperSlicer, MegaSlicer and ApexSlicer)
     *
     * @param startPoint The starting point of the Slicer
     * @param polyline The polyline that the Slicer travels along
     * @param imageFile The image of the Slicer
     * @param speed The speed at which the Slicer travels at
     * @param health The health of the Slicer
     * @param reward The reward the player gets for eliminating the Slicer
     * @param penalty The penalty on the player if the Slicer travels to end of map
     */
    public Slicer(Point startPoint, List<Point> polyline, String imageFile, double speed, int health,
                                                                                   int reward, int penalty) {
        super(startPoint, imageFile);
        this.imageFile = imageFile;
        this.speed = speed;
        this.health = health;
        this.reward = reward;
        this.penalty = penalty;
        this.polyline = polyline;
        this.targetPointIndex = 1;
        this.finished = false;
    }



    public List<Point> getPolyline() {
        return polyline;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getReward() {
        return reward;
    }

    public int getPenalty() {
        return penalty;
    }

    /**
     * Updates the current state of the slicer. The slicer moves towards its next target point in
     * the polyline at its specified movement rate.
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        if (finished) {
            return;
        }
        // Obtain where the slicer is currenly at and where it is heading towards
        Point currentPoint = getCenter();
        Point targetPoint = polyline.get(targetPointIndex);
        // Convert them to vectors to perform some vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        // Distance slicer is (in pixels) away from the target point in the polyline
        double magnitude = distance.length();
        // Check if slicer is close to the target point
        if (magnitude < speed * ShadowDefend.getTimescale()) {
            // Check if slicer has reached the end of the map
            if (targetPointIndex == polyline.size() - 1) {
                finished = true;
                return;
            } else {
                // Make the target point the next point in the polyline
                targetPointIndex += 1;
            }
        }
        // Move towards the target point
        // We do this by getting a unit vector in the direction of our target, and multiplying it
        // by the speed of the slicer (accounting for the timescale)
        super.move(distance.normalised().mul(speed * ShadowDefend.getTimescale()));
        // Update current rotation angle to face target point
        setAngle(Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x));
        super.update(input);
    }

    /**
     *
     * @return true if the slicer has finished the map (exited map)
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     *
     * @return true if the slicer has been eliminated
     */
    public boolean isEliminated() {
        if(health <= 0) {
            return true;
        }
        return false;
    }



    /**
     * checks which type of slicer is spawned in the wave event.
     *
     * @param currentEvent Current wave event of the wave
     * @param polyline The polyline that the Slicers travel along
     * @return The slicer that was spawned in the spawn event
     */
    public static Slicer addNewSlicer(WaveEvent currentEvent, List<Point> polyline){
        Slicer slicer = null;
        if(currentEvent.getEnemyType().equals("slicer")){
            slicer = new RegularSlicer(polyline.get(0), polyline);
        }
        else if(currentEvent.getEnemyType().equals("superslicer")){
            slicer = new SuperSlicer(polyline.get(0), polyline);
        }
        else if(currentEvent.getEnemyType().equals("megaslicer")){
            slicer = new MegaSlicer(polyline.get(0), polyline);
        }
        else if(currentEvent.getEnemyType().equals("apexslicer")){
            slicer = new ApexSlicer(polyline.get(0), polyline);
        }
        return slicer;
    }


    /**
     * overridden method that spawns child slicers when parent slicer is eliminated
     */
    public void spawnChildSlicer(){}

    /**
     * gets the route that the child slicer will travel on the map after it is spawned
     *
     * @return The updated polyline that the child slicer will travel if it was spawned by the parent slicer
     */
    public List<Point> getRemainingRoute(){
        updatedRoute.clear();
        //update the new route to include the previous target point of the parent slicer onwards.
        for(int i = targetPointIndex - 1; i < polyline.size() ; i++){
            updatedRoute.add(polyline.get(i));
        }
        return updatedRoute;
    }
}
