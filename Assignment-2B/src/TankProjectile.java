import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;
import bagel.util.Rectangle;

/**
 * TankProjectile is the projectile fired by a Tank
 */
public class TankProjectile extends Projectile {
    private static final String IMAGE_FILE = "res/images/tank_projectile.png";
    private static final int DAMAGE = 1;


    /**
     * creates a new TankProjectile
     *
     * @param towerCenterPoint the coordinate of the center of the tank, where the TankProjectile is fired from
     * @param target target slicer that the TankProjectile is fired at
     */
    public TankProjectile(Point towerCenterPoint, Slicer target){
        super(towerCenterPoint, IMAGE_FILE, DAMAGE, target);

    }

    public static int getDAMAGE() {
        return DAMAGE;
    }

    /**
     * calls the update method in the Projectile class
     *
     * @param input input from the mouse or keyboard
     */
    @Override
    public void update(Input input) {
        super.update(input);
    }
}
