import bagel.util.Point;

import java.util.List;


/**
 * SuperSlicer, a type of slicer(antagonist of game)
 */
public class SuperSlicer extends Slicer{
    private static final String IMAGE_FILE = "res/images/superslicer.png";
    private static final double SPEED = 3.0/4 * RegularSlicer.getSPEED();
    private static final int HEALTH = RegularSlicer.getHEALTH();
    private static final int REWARD = 15;
    private static final int NUMBER_OF_CHILD_SLICER = 2;
    private static final int PENALTY = NUMBER_OF_CHILD_SLICER * RegularSlicer.getPENALTY();


    public static double getSPEED() {
        return SPEED;
    }

    public static int getHEALTH() {
        return HEALTH;
    }

    public static int getPENALTY() {
        return PENALTY;
    }

    /**
     * creates a new SuperSlicer
     *
     * @param startPoint startPoint the starting point of the SuperSlicer
     * @param polyline polyline the polyline that the SuperSlicer travels along
     */
    public SuperSlicer(Point startPoint, List<Point> polyline){
        super(startPoint, polyline, IMAGE_FILE, SPEED, HEALTH, REWARD, PENALTY);
    }

    /**
     * spawns 2 child slicers (RegularSlicers) when it is eliminated
     */
    @Override
    public void spawnChildSlicer(){
       for(int i = 0; i < NUMBER_OF_CHILD_SLICER; i++){
           //spawns 2 RegularSlicers at the point it was eliminated
           Level.getSlicers().add(new RegularSlicer(getCenter(), getRemainingRoute()));

        }
    }
}
