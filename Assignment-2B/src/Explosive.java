import java.util.ArrayList;

import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;
import bagel.util.Rectangle;

/**
 * Explosive is dropped by an airplane (passive tower)
 */
public class Explosive extends Sprite{
    private static final String IMAGE_FILE = "res/images/explosive.png";
    private static final int DETONATE_TIME = 2;
    private int dropTime;
    private static final int EFFECT_RADIUS = 200;
    private static final int DAMAGE = 500;
    private double timeFrameAfterDrop;
    private boolean hasExploded;
    private Rectangle effectArea;


    /**
     * creates an Explosive
     *
     * @param airplaneLocation the location of the airplane where the explosive was dropped
     */
    public Explosive(Point airplaneLocation){
        super(airplaneLocation, IMAGE_FILE);
        this.timeFrameAfterDrop = 0;
        this.hasExploded = false;
        this.effectArea = new Rectangle(airplaneLocation.x - EFFECT_RADIUS,
                airplaneLocation.y - EFFECT_RADIUS, EFFECT_RADIUS * 2, EFFECT_RADIUS * 2);
    }


    /**
     * Updates the current state of the explosives on the map.
     * When it explodes, it inflicts damage to nearby slicers and is removed from the map.
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        //Keeps track frame counter after explosive is dropped
        timeFrameAfterDrop += ShadowDefend.getTimescale();
        if (hasExploded) {
            return;
        }
        // Check if explosive has exploded after being dropped
        if(timeFrameAfterDrop / ShadowDefend.getFPS() >= DETONATE_TIME) {
            inflictDamageWithinRange();
            hasExploded = true;
            return;
        }
        super.update(input);
    }


    /**
     *
     * @return true if the explosive has exploded
     */
    public boolean hasExploded() {
        return hasExploded;
    }

    /**
     * cause damage to all slicers in the effect radius of the explosive
     */
    public void inflictDamageWithinRange() {
        for (int i = 0; i < Level.getSlicers().size(); i++) {
            Slicer s = Level.getSlicers().get(i);
            // checks if the slicer is in the effect radius of the explosive
            if (effectArea.intersects(s.getCenter())) {
                // update the health of the slicer after it was damaged by the explosive
                s.setHealth(s.getHealth() - DAMAGE);
            }
        }
    }
}
