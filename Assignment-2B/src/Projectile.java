import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * The parent class for all projectiles (TankProjectile and SuperTankProjectile)
 */
public abstract class Projectile extends Sprite{

    private static final int SPEED = 10;
    private int damage;
    private Slicer target;
    private boolean hasHitTarget;

    /**
     * constructor for abstract parent class projectile
     *
     * @param point point where the projectile starts moving from after it is fired
     * @param imageSrc image of the projectile
     * @param damage amount of damage the projectile inflicts on the slicer
     * @param target slicer that the SuperTankProjectile is fired at
     */
    public Projectile(Point point, String imageSrc, int damage, Slicer target){
        super(point, imageSrc);
        this.damage = damage;
        this.hasHitTarget = false;
        this.target = target;

    }

    /**
     * Updates the current state of the moving projectiles that have been fired.
     * When it hits the target slicer, it inflicts damage to it and is removed from the map.
     */

    /**
     * Updates the current state of the moving projectiles that have been fired.
     * When it hits the target slicer, it inflicts damage to it and is removed from the map.
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    public void update(Input input) {

        // Obtain current location of the projectile
        Point currentPoint = getCenter();
        Rectangle projectileBoundingBox = getRect();

        if (hasHitTarget) {
            return;
        }
        // Check if projectile has hit the target slicer
        if(projectileBoundingBox.intersects(target.getCenter())) {
            //inflict damage onto the target slicer
            target.setHealth(target.getHealth() - damage);
            hasHitTarget = true;
            return;
        }

        Vector2 directionVector = target.getCenter().asVector().sub(currentPoint.asVector());

        // Move towards the target slicer
        // We do this by getting a unit vector in the direction of the target slicer, and multiplying it
        // by the speed of the projectile (accounting for the timescale)
        super.move(directionVector.normalised().mul(SPEED * ShadowDefend.getTimescale()));
        // Update current rotation angle to face target slicer
        super.update(input);
    }


    /**
     *
     * @return true if the projectile has hit the target slicer
     */
    public boolean hasHitTarget() {
        return hasHitTarget;
    }

}
