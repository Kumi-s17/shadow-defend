import bagel.*;
import bagel.util.Rectangle;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.List;


/**
 * The parent class for all Towes (Tank, SuperTank and Airplane)
 */
public abstract class Tower extends Sprite{
     private String imageFile;
     private double effectRadius;
     private double cooldown;
     private double dropTime;
     private double speed;
     private Point location;



     /**
      * constructor for active towers (Tank and SuperTank)
      *
      * @param location   The coordinate where the ActiveTower was placed when it was purchased
      * @param imageFile    The image of the ActiveTower
      * @param effectRadius  The effect radius of the ActiveTower
      * @param cooldown    The cooldown time required to launch the next projectile
      */
     public Tower(Point location, String imageFile, double effectRadius, double cooldown) {
          super(location, imageFile);
          this.location = location;
          this.imageFile = imageFile;
          this.effectRadius = effectRadius;
          this.cooldown = cooldown;
     }


     /**
      * constructor for passive towers (Airplane)
      *
      * @param imageFile   The image of the Airplane
      * @param speed    The speed of the Airplane
      * @param flyStart   The starting point of the Airplane
      */
     public Tower(String imageFile, double speed, Point flyStart) {
          super(flyStart, imageFile);
          this.imageFile = imageFile;
          this.dropTime = dropTime;
          this.speed = speed;
     }

     public Point getLocation() {
          return location;
     }



     /**
      * Checks that the tower that is to be purchased can be placed there without its center intersecting with
      * another tower already on the map
      *
      * @param level    The current level of the game
      * @param input    The current mouse/keyboard state
      * @return    true if the point that we want to place the tower at intersects with another tower that is already
      *            there on the map
      */
     public static boolean intersectsTower(Level level, Input input){
          // if there are other towers on the map
          if (!level.getTowersOnMap().isEmpty()){
               for (Tower tower : level.getTowersOnMap()) {
                    if(tower.getRect().intersects(input.getMousePosition())){
                         return true;
                    }
               }
          }
          return false;
     }




     /**
      * Get the slicer to target by the tank or super tank.
      *
      * @param effectArea  The are that the tank or super tank can attack a slicer in
      * @return  The slicer to be targetted by the active tank (tank or super tank)
      */
     public Slicer getActiveTowerTarget(Rectangle effectArea){
          ArrayList<Slicer> canTarget = new ArrayList<>();
          //get all slicers that can be targetted by the tank or supertank.
          for(int i = 0; i < Level.getSlicers().size(); i++){
               Slicer s = Level.getSlicers().get(i);
               //can attack only if the slicer is in the effect area of the active tower and only slicers that are
               // on the map
               if(effectArea.intersects(s.getCenter()) && s.getCenter().x >= 0 && s.getCenter().y >= 0
                       && s.getCenter().x <= Window.getWidth() && s.getCenter().y <= Window.getHeight()){
                    canTarget.add(s);
               }

          }
          //If there are slicers in the effect radius of the active tower, return a slicer that can be targeted
          if(!canTarget.isEmpty()){
               return canTarget.get(canTarget.size() - 1);
          }
          return null;
     }
}

