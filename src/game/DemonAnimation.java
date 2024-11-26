package game;
import org.jbox2d.common.Vec2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to handle demon AI and animations
 */
public class DemonAnimation implements ActionListener {
    private int timeElapsed;
    public boolean requestIdleUpdate;
    public boolean requestSliceUpdate = false;
    public boolean requestWalkUpdate = false;

    public boolean requestRunUpdate = false;
    public boolean requestJumpUpdate = false, requestKnockbackUpdate = false;
    public boolean requestDeathUpdate;
    public boolean flip = false;
    public boolean slowed = false;

    private int slowedReps = 2;


    /**
     *
     * @param slowedReps - number of slowed animation cycles the demon needs to complete before moving at normal speed
     */
    public void setSlowedReps(int slowedReps) {
        this.slowedReps = slowedReps;
    }

    /**
     * increases the slowedReps counter until demon has completed desired amount
     */
    public void incrementSlowedReps(){
        slowedReps++;
        if(slowedReps>2){
            slowed=false;
        }
    }

    private int direction;
    public Demon demon;
    public static DemonSpawner demonSpawner;
    private GameLevel level;

    /**
     * Initalises the demon it will be animating, so it can handle its only its animations and no other objects of the
     * same demon class. Makes the demon do its idle animation after being spawned in.
     *
     * @param demon - passes in demon object, so it can access its attributes such as: position, health etc.
     * @param requestIdleUpdate - at spawn the demon is set to being idle
     * @param demonSpawner - has access to object that spawned demon, so it can let it know when a demon has died
     * @param level - current level of demon and player, so it can get player location and make demon walk towards player
     */

    public DemonAnimation(Demon demon, boolean requestIdleUpdate, DemonSpawner demonSpawner, GameLevel level){
        this.demon = demon;
        this.requestIdleUpdate = requestIdleUpdate;
        this.level = level;
        DemonAnimation.demonSpawner = demonSpawner;
    }

    /**
     * Moon arrow will call this method as it has the ablity to knock back,
     * the knock-back animation will play and the demon will be slowed after it has
     * been played.
     * @param requestKnockbackUpdate
     */
    public void setRequestKnockbackUpdate(boolean requestKnockbackUpdate) {
        this.requestKnockbackUpdate = requestKnockbackUpdate;
    }

    /**
     * gets the current amount of slowed reps so demon isn't slowed
     * for more than the required amount
     */
    public int getSlowedReps() {
        return slowedReps;
    }

    /**
     * updates demon animation every 50ms. Method calculates the position of player and makes
     * demon walk towards it. After reaches a specific attack range, demon will attack and knock up the player.
     * If player exceeds attack range it will go back to moving player. This method handles all required animations
     * of the demon, some animations have higher precedence than others -> i.e. if demon is dead, attack animation shouldn't player
     * no matter how in range the player is. This method also applies knock back to the demon via applyforce. Once a required animation has been
     * selected it will call the demon object and let it play the animation.
     * @param e the event to be processed
     */
    // actionPerformed is called every 50ms and updates samurai animation by request
    @Override
    public void actionPerformed(ActionEvent e) {
        timeElapsed += 1;
        if(!requestDeathUpdate){
            if(!requestJumpUpdate){
                if((demon.getPosition().x - level.getSamurai().getPosition().x)/-1 >0){
                    requestRunUpdate = true;
                    requestIdleUpdate = false;
                    requestWalkUpdate = false;
                    if(slowed){
                        demon.startWalking(0.1f);
                        requestRunUpdate = false;
                        requestWalkUpdate = true;
                    }else {
                        demon.startWalking(1);
                    }
                    flip = true;
                }else if(!requestDeathUpdate){
                    requestRunUpdate = true;
                    requestIdleUpdate = false;
                    requestWalkUpdate = false;
                    if(slowed){
                        demon.startWalking(-0.1f);
                        requestRunUpdate = false;
                        requestWalkUpdate = true;
                    }else {
                        demon.startWalking(-1);
                    }
                    flip = false;
                }
                if(Math.abs(demon.getPosition().x - level.getSamurai().getPosition().x) <= 35){
                    demon.stopWalking();
                    requestSliceUpdate = true;
                }
            }else{
                demon.stopWalking();
                if(direction>0){
                    demon.applyForce(new Vec2(200000, 0));
                }
                else{
                    demon.applyForce(new Vec2(-200000, 0));
                }
            }
        }

        //System.out.println(samurai.getPosition().y);
        if (requestDeathUpdate) {
            demon.UpdateImage_Dying(timeElapsed, flip);
        }
        else if (requestJumpUpdate) {
            demon.UpdateImage_Jump(timeElapsed, flip);
        }
        else if (requestSliceUpdate) {
            demon.UpdateImage_Slice(timeElapsed, flip);
        }
        else if(requestIdleUpdate & demon.getLinearVelocity().equals(new Vec2(0,0))){
            demon.UpdateImage_Idle(timeElapsed, flip);
        } else if (requestWalkUpdate) {
            demon.UpdateImage_Walk(timeElapsed, flip);
        }
        else if (requestRunUpdate) {
            demon.UpdateImage_Run(timeElapsed, flip);
        }
    }

    // keep for later use
    public double getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * lets the animation know that an attack animation has been requested
     */
    // lets actionPerformed method know to play attack animation
    public void allowRequestSliceUpdate(){
        requestSliceUpdate = true;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestRunUpdate = false;
    }

    /**
     * lets the animation know that an attack animation has been completed
     */
    // lets actionPerformed method know to stop attack animation
    public void denyRequestSliceUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
        requestRunUpdate = false;
    }

    /**
     * lets the animation know that an walk animation has been completed
     */
    // lets actionPerformed method know to stop walk animation
    public void denyRequestWalkUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
        requestRunUpdate = false;
    }

    /**
     * lets the animation know that an walk animation has been requested
     */
    // lets actionPerformed method know to play walk animation
    public void allowRequestWalkUpdate(boolean b){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = true;
        demon.stopWalking = false;
        requestRunUpdate = false;
        this.flip = b;
    }

    /**
     * lets the animation know that jump animation has been completed
     */
    // lets actionPerformed method know to stop jump animation
    public void denyRequestJumpUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
        requestJumpUpdate = false;
        requestRunUpdate = false;
    }

    /**
     * lets the animation know that jump animation has been requested
     */
    // lets actionPerformed method know to play jump animation
    public void allowRequestJumpUpdate(int direction){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestJumpUpdate = true;
        requestRunUpdate = false;
        this.direction = direction;
    }

    /**
     * lets the animation know that death animation has been completed.
     * This then reduces the demon count by one in demon Spawner and stops the animation from running
     * to save computational resources
     */

    // lets actionPerformed method know to stop death animation
    public void denyRequestDeathUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestDeathUpdate = false;
        requestRunUpdate = false;
        demonSpawner.reduceNumberOfDemons();
        demon.demonTimer.stop();
    }

    /**
     * lets the animation know that a death animation has been requested
     */
    // lets actionPerformed method know to play death animation
    public void allowRequestDeathUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestDeathUpdate = true;
        requestRunUpdate = false;
    }

    /**
     * lets the animation know that a run animation has been completed
     */
    public void denyRequestRunUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
        requestRunUpdate = false;
    }

    /**
     * lets the animation know that run animation has been completed
     */
    public void allowRequestRunUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestRunUpdate = true;
    }
}

