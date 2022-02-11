import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;


/**
 * SuperTank, an active tower in the game
 */
import java.util.ArrayList;
public class SuperTank extends Tower {
    private static final String IMAGE_FILE = "res/images/supertank.png";
    private static final double EFFECT_RADIUS = 150;
    private static final double MILLISECOND_PER_SECOND = 1000;
    private static final double PROJECTILE_COOLDOWN = 500 / MILLISECOND_PER_SECOND;
    private ArrayList<SuperTankProjectile> superTankProjectiles;
    private ArrayList<Slicer> targetSlicer = new ArrayList<>();
    private double fromPreviousFire;


    /**
     * creates a new SuperTank
     *
     * @param location   The coordinate where the SuperTank was placed when it was purchased
     */
    public SuperTank(Point location) {
        super(location, IMAGE_FILE, EFFECT_RADIUS, PROJECTILE_COOLDOWN);
        this.superTankProjectiles = new ArrayList<>();
        this.fromPreviousFire = 0;

    }


    /**
     * Updates the current state of the SuperTank, potentially reading from input
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        //Keeps track frame counter after the SuperTank is purchased
        fromPreviousFire += ShadowDefend.getTimescale();

        // get current location of SuperTank
        Point superTankPoint = getCenter();
        // get the area where the SuperTank can attack a slicer
        Rectangle effectArea = new Rectangle(superTankPoint.x - EFFECT_RADIUS, superTankPoint.y - EFFECT_RADIUS,
                EFFECT_RADIUS * 2, EFFECT_RADIUS * 2);

        // Check if it is time to fire another super tank projectile and if there is a slicer nearby.
        if (fromPreviousFire / ShadowDefend.getFPS() >= PROJECTILE_COOLDOWN) {
            Slicer targetSlicer = super.getActiveTowerTarget(effectArea);
            // if there is a slicer that can be targetted
            if(targetSlicer != null) {
                // fire a projectile
                superTankProjectiles.add(new SuperTankProjectile(superTankPoint, targetSlicer));
                // reset frame counter
                fromPreviousFire = 0;

                // Update current rotation angle to face target slicer
                setAngle(Math.PI / 2 + Math.atan2(targetSlicer.getCenter().y - superTankPoint.y,
                        targetSlicer.getCenter().x - superTankPoint.x));

            }

        }
        super.update(input);

        // Update all super tank projectiles, and remove them if they've finished
        for (int i = superTankProjectiles.size() - 1; i >= 0; i--) {
            SuperTankProjectile p = superTankProjectiles.get(i);
            p.update(input);
            if (p.hasHitTarget()) {
                superTankProjectiles.remove(i);
            }
        }
    }
}
