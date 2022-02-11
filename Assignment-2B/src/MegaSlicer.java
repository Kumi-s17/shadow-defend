import bagel.util.Point;

import java.util.List;

/**
 * MegaSlicer, a type of slicer(antagonist of game)
 */
public class MegaSlicer extends Slicer{
    private static final String IMAGE_FILE = "res/images/megaslicer.png";
    private static final double SPEED = SuperSlicer.getSPEED();
    private static final int HEALTH = 2 * SuperSlicer.getHEALTH();
    private static final int REWARD = 10;
    private static final int NUMBER_OF_CHILD_SLICER = 2;
    private static final int PENALTY = NUMBER_OF_CHILD_SLICER * SuperSlicer.getPENALTY();


    /**
     * creates a new MegaSlicer
     *
     * @param startPoint the starting point of the MegaSlicer
     * @param polyline the polyline that the MegaSlicer travels along
     */
    public MegaSlicer(Point startPoint, List<Point> polyline){
        super(startPoint, polyline, IMAGE_FILE, SPEED, HEALTH, REWARD, PENALTY);
    }

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
     * spawns 2 child slicers(SuperSlicers) when it is eliminated
     */
    @Override
    public void spawnChildSlicer(){
        for(int i = 0; i < NUMBER_OF_CHILD_SLICER; i++){
            //spawns 2 SuperSlicers at the point it was eliminated
            Level.getSlicers().add(new SuperSlicer(getCenter(), getRemainingRoute()));

        }
    }
}
