package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Raptor extends Walker {
    private static final Shape raptorShape = new BoxShape(4, 8);
    private static final PolygonShape raptorHeadShape = new PolygonShape(-19.3f,7.4f, -15.2f,7.2f, -6.3f,14.8f, -14.3f,17.2f, -19.1f,8.1f);
    private static final PolygonShape raptorBodyShape = new PolygonShape(-7.9f, 0.2f, -2.7f,-4.1f, 5.4f,-3.7f, 4.4f, 4.4f, -2.3f,7.6f, -7.6f,1.7f);
    private static final PolygonShape raptorNeckShape = new PolygonShape(-5.8f,5.6f, -1.9f,8.5f, -4.9f,14.3f, -8.7f,11.5f);

    /* creates multiple fixtures for head, body and neck
       to allow the player to deal increased or decreased amounts
       of damage depending on when hit
     */

    public SolidFixture raptorHeadFixture = new SolidFixture(this,raptorHeadShape);
    public SolidFixture raptorBodyFixture = new SolidFixture(this,raptorBodyShape);
    public SolidFixture raptorNeckFixture = new SolidFixture(this,raptorNeckShape);


    private static int health = 100;

    private static AttachedImage image;

    private static boolean  isIdle = false, isRoaring = false, isRunning= false, isBiting = false;

    private static int timeSpentRunning;

    private static CollisionListener rc;

    public Raptor(World w) {
        super(w, raptorShape);
        rc = new RaptorCollisionListener();
        this.addCollisionListener(rc);
        this.setGravityScale(12); // makes raptor heavier than player so cannot be easily moved
    }

    // raptor takes damage and dies if health goes below 0
    public void takenDamage(int damage){
        //raptor doesn't die if player is already dead
        /*
        health -= damage;
        if(health <= 0 && Samurai.getHealth() > 0){
            this.destroy();
            RaptorAnimation.setIsDead(true);
        }
         */
    }

    /* sets the image to idle gif, since it is a gif once set, it
    shouldn't be set again repeatedly so gif looks smooth with no cuts.
    This is achieved using a boolean variable isIdle.
    Gif stops playing after a time spent running as reached a specific number.
     */
    public void UpdateImage_Idle(){
        if(!isIdle){
            timeSpentRunning = 0;
            removeAllImages();
            image = new AttachedImage(this, new BodyImage("data/Raptor/raptor-idle.gif"), 24, 0, new Vec2(0,0));
            image.flipHorizontal();
            startWalking(0);
            Raptor.isIdle = true;
            Raptor.isRunning = false;
            Raptor.isRoaring = false;
            Raptor.isBiting = false;
        }
        timeSpentRunning++;
    }

    /* sets the image to run gif, since it is a gif once set it
    shouldn't be set again repeatedly so gif looks smooth with no cuts.
    This is achieved using a boolean variable isRunning.
    Gif stops playing after a time spent running as reached a specific number.
     */
    public void UpdateImage_Run(){
        if(!isRunning){
            timeSpentRunning = 0;
            removeAllImages();
            image = new AttachedImage(this, new BodyImage("data/Raptor/raptor-run.gif"), 64, 0, new Vec2(0,23));
            image.flipHorizontal();
            startWalking(-1);
            Raptor.isRunning = true;
            Raptor.isRoaring = false;
            Raptor.isIdle = false;
            Raptor.isBiting = false;
        }
        timeSpentRunning++;
        if(timeSpentRunning>26){
            RaptorAnimation.denyRequestRunningUpdate();
        }
    }

    /* sets the image to roar gif, since it is a gif once set it
    shouldn't be set again repeatedly so gif looks smooth with no cuts.
    This is achieved using a boolean variable isRoaring.
    Gif stops playing after a time spent running as reached a specific number.
     */
    public void UpdateImage_Roar(){
        if(!isRoaring){
            timeSpentRunning = 0;
            removeAllImages();
            image = new AttachedImage(this, new BodyImage("data/Raptor/raptor-roar.gif"), 64, 0, new Vec2(0,23));
            image.flipHorizontal();
            startWalking(0);
            Raptor.isRoaring = true;
            Raptor.isIdle = false;
            Raptor.isRunning = false;
            Raptor.isBiting = false;
        }
        timeSpentRunning++;
        if(timeSpentRunning>26){
            RaptorAnimation.denyRequestRoarUpdate();
        }
    }

    /* sets the image to bite gif, since it is a gif once set it
    shouldn't be set again repeatedly so gif looks smooth with no cuts.
    This is achieved using a boolean variable isBiting.
    Gif stops playing after a time spent running as reached a specific number.
     */
    public void UpdateImage_Bite(){
        if(!isBiting){
            timeSpentRunning = 0;
            removeAllImages();
            image = new AttachedImage(this, new BodyImage("data/Raptor/raptor-pounced-attack.gif"), 64, 0, new Vec2(0,23));
            image.flipHorizontal();
            startWalking(-2);
            Raptor.isRunning = false;
            Raptor.isRoaring = false;
            Raptor.isIdle = false;
            Raptor.isBiting = true;
        }
        timeSpentRunning++;
        if(timeSpentRunning>20){
            RaptorAnimation.denyRequestBitingUpdate();
        }
    }

    // allows collision listener to be removed by other classes
    public void removeCollisionListenerRaptor() {
        this.removeCollisionListener(rc);
    }

    // allows collision listener to be added by other classes
    public void addCollisionListenerRaptor() {
        rc = new RaptorCollisionListener();
        this.addCollisionListener(rc);
    }
}
