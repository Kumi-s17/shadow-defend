import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * SuperTankProjectile is the projectile fired by a SuperTank
 */
public class SuperTankProjectile extends Projectile{
    private static final String IMAGE_FILE = "res/images/supertank_projectile.png";
    private static final int DAMAGE = 3 * TankProjectile.getDAMAGE();


    /**
     * creates a new SuperTankProjectile
     *
     * @param towerCenterPoint the coordinate of the center of the super tank, where the SuperTankProjectile is
     * fired from
     * @param target slicer that the SuperTankProjectile is fired at
     */
    public SuperTankProjectile(Point towerCenterPoint, Slicer target){
        super(towerCenterPoint, IMAGE_FILE, DAMAGE, target);
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
