import bagel.util.Point;

import java.util.List;

/**
 * RegularSlicer, a type of slicer(antagonist of game)
 */
public class RegularSlicer extends Slicer{
    private static final String IMAGE_FILE = "res/images/slicer.png";
    private static final double SPEED = 2;
    private static final int HEALTH = 1;
    private static final int REWARD = 2;
    private static final int PENALTY = 1;


    public static double getSPEED(){
        return SPEED;
    }

    public static int getHEALTH(){
        return HEALTH;
    }

    public static int getPENALTY() {
        return PENALTY;
    }

    /**
     * creates a new RegularSlicer
     *
     * @param startPoint startPoint the starting point of the RegularSlicer
     * @param polyline polyline the polyline that the RegularSlicer travels along
     */
    public RegularSlicer(Point startPoint, List<Point> polyline){
        super(startPoint, polyline, IMAGE_FILE, SPEED, HEALTH, REWARD, PENALTY);
    }


}
