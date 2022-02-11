import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;
import bagel.util.Rectangle;
import java. lang. Math. *;

/**
 * Tank, an active tower in the game
 */
import java.util.ArrayList;
public class Tank extends Tower {

    private static final String IMAGE_FILE = "res/images/tank.png";
    private static final double EFFECT_RADIUS = 100;
    private static final double MILLISECOND_PER_SECOND = 1000;
    private static final double PROJECTILE_COOLDOWN = 1000 / MILLISECOND_PER_SECOND;
    private ArrayList<TankProjectile> tankProjectiles;
    private ArrayList<Slicer> targetSlicer = new ArrayList<>();
    private double fromPreviousFire;


    /**
     * creates a new Tank
     * @param location   The coordinate where the Tank was placed when it was purchased
     */
    public Tank(Point location) {
        super(location, IMAGE_FILE, EFFECT_RADIUS, PROJECTILE_COOLDOWN);
        this.tankProjectiles = new ArrayList<>();
        this.fromPreviousFire = 0;
    }


    /**
     * Updates the current state of the Tank, potentially reading from input
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {
        //Keeps track frame counter after the Tank is purchased
        fromPreviousFire += ShadowDefend.getTimescale();

        // get current location of Tank
        Point tankPoint = getCenter();
        // get the area where the Tank can attack a slicer
        Rectangle effectArea = new Rectangle(tankPoint.x - EFFECT_RADIUS, tankPoint.y - EFFECT_RADIUS,
                EFFECT_RADIUS * 2, EFFECT_RADIUS * 2);

        // Check if it is time to fire another tank projectile and if there is a slicer nearby.
        if (fromPreviousFire / ShadowDefend.getFPS() >= PROJECTILE_COOLDOWN) {
            Slicer targetSlicer = super.getActiveTowerTarget(effectArea);
            // if there is a slicer that can be targetted
            if(targetSlicer != null) {
                // fire a projectile
                tankProjectiles.add(new TankProjectile(tankPoint, targetSlicer));
                // reset frame counter
                fromPreviousFire = 0;

                // Update current rotation angle to face target slicer
                setAngle(Math.PI/2 + Math.atan2(targetSlicer.getCenter().y - tankPoint.y,
                        targetSlicer.getCenter().x - tankPoint.x));

            }

        }
        super.update(input);

        // Update all tank projectiles, and remove them if they've finished
        for (int i = tankProjectiles.size() - 1; i >= 0; i--) {
            TankProjectile p = tankProjectiles.get(i);
            p.update(input);
            if (p.hasHitTarget()) {
                tankProjectiles.remove(i);
            }
        }
    }
}
