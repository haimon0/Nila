package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.lang.Math;
import java.util.ArrayList;



/**
 * A base arrow type; used to create arrow hit-box, calculates velocity and angle it should travel at.
 */
public class Arrow extends DynamicBody {
    private ArrowCollisionListener arrowCollisionListener;

    private ArrayList<String> flyingAnimation;

    private int flyingAnimationPointer = 0, flyingAnimationSize;

    private boolean flip;

    private Timer arrowTimer;

    private ArrowAnimation arrowAnimation;

    private float angleX;

    private int arrowSize;

    public Timer getArrowTimer() {
        return arrowTimer;
    }

    /**
     * Used to set hit-box of arrow and calculate initial trajectory, constructor will adjust arrow animation and
     * movement to negative direction if target click is on the left side of player.
     * Trajectory is calculated by finding difference of mouse click and arrow spawn location
     * and adjusting forces as necessary to reach as close to the mouse click as possible. It also assigns
     * a collision listener to arrow, so arrow can interact with enemy characters, and delete itself after doing so.
     *
     * @param w - passes in a copy of world so the object can be made
     * @param target - the point where the user has clicked, so arrow has a destination to reach
     * @param position - the spawn position of the arrow
     * @param flyingAnimation - the images to show the arrow flying
     * @param boxShape - the hit-box of the arrow
     */

    public Arrow(World w, Vec2 target, Vec2 position, ArrayList<String> flyingAnimation, Shape boxShape) {
        super(w, boxShape);
        if(this.getClass().getSimpleName().equals("NormalArrow")){
            arrowSize = 1;
        } else if (this.getClass().getSimpleName().equals("MoonArrow")) {
            arrowSize = 5;
        }
        this.flyingAnimation = flyingAnimation;
        flyingAnimationSize = flyingAnimation.size();
        arrowCollisionListener = new ArrowCollisionListener();
        arrowAnimation = new ArrowAnimation(this);
        arrowTimer = new Timer(50, arrowAnimation);
        arrowTimer.start();
        this.addCollisionListener(arrowCollisionListener);
        angleX = (float) Math.atan((target.y-position.y)/(target.x- position.x));
        float distanceX = target.x-position.x;
        if(distanceX < 0){
            this.setPosition(new Vec2(position.x-5, position.y));
            this.addImage(new BodyImage(flyingAnimation.get(0))).setRotation(-3.14f+angleX);
            flip = false;
        }else{
            this.setPosition(new Vec2(position.x+5, position.y));
            this.addImage(new BodyImage(flyingAnimation.get(0))).setRotation(angleX);
            flip = true;
        }
    }

    /**
     * Iterates through images in an arraylist to create an animation of the arrow flying, it uses
     * angleX to tilt the image to make it visually clear that the arrow is travelling at an angle
     *
     * @param timeElapsed - used to adjust frame rate of arrow animation, if and when required
     */
    public void UpdateImage_Flying(int timeElapsed) {
        if(!flip){
            if (flyingAnimationPointer < flyingAnimationSize) {
                removeAllImages();
                new AttachedImage(this, new BodyImage(flyingAnimation.get(flyingAnimationPointer)), arrowSize, -3.14f+angleX, new Vec2(0, 0));
                flyingAnimationPointer++;
            } else {
                flyingAnimationPointer = 0;
            }
        }
        else {
            if (flyingAnimationPointer < flyingAnimationSize) {
                removeAllImages();
                new AttachedImage(this, new BodyImage(flyingAnimation.get(flyingAnimationPointer)), arrowSize, angleX, new Vec2(0, 0));
                flyingAnimationPointer++;
            } else {
                flyingAnimationPointer = 0;
            }
        }
    }
}
