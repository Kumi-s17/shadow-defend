import bagel.util.Point;

import java.util.List;

/**
 * ApexSlicer, a type of slicer(antagonist of game)
 */
public class ApexSlicer extends Slicer {
    private static final String IMAGE_FILE = "res/images/apexslicer.png";
    private static final double SPEED = 1.0/2 * MegaSlicer.getSPEED();
    private static final int HEALTH = 25 * RegularSlicer.getHEALTH();
    private static final int REWARD = 150;
    private static final int NUMBER_OF_CHILD_SLICER = 4;
    private static final int PENALTY = NUMBER_OF_CHILD_SLICER * MegaSlicer.getPENALTY();


    /**
     * creates a new ApexSlicer
     *
     * @param startPoint the starting point of the ApexSlicer
     * @param polyline the polyline that the ApexSlicer travels along
     */
    public ApexSlicer(Point startPoint, List<Point> polyline){
        super(startPoint, polyline, IMAGE_FILE, SPEED, HEALTH, REWARD, PENALTY);
    }

    /**
     * spawns 4 child slicers (MegaSlicers) when it is eliminated
     */
    @Override
    public void spawnChildSlicer(){
        for(int i = 0; i < NUMBER_OF_CHILD_SLICER; i++){
            //spawns 4 MegaSlicers at the point it was eliminated
            Level.getSlicers().add(new MegaSlicer(getCenter(), getRemainingRoute()));

        }
    }
}
