package game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

public class RaptorAnimation implements ActionListener {
    private static int timeElapsedRaptor;
    private static boolean requestRoarUpdate;
    private static boolean requestIdleUpdate = false;
    private static boolean requestRunningUpdate = false;
    private static boolean requestBitingUpdate = false;

    private static boolean isDead = false;

    private static Raptor raptor;

    public RaptorAnimation(Raptor raptor, boolean requestRoarUpdate){
        RaptorAnimation.raptor = raptor;
        // starts game by roaring
        RaptorAnimation.requestRoarUpdate = requestRoarUpdate;
    }

    // actionPerformed is called every 50ms to check if the raptor animation needs an update
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        timeElapsedRaptor += 1;
        // if player gets close to raptor, attack animation will player and player will take damage
        // unless player is already dead
        if(raptor.getPosition().x - GameLevel.getSamurai().getPosition().x <= 11.0 && !isDead){
            requestBitingUpdate = true;
            GameLevel.getSamurai().takenDamage(2);
        }
        if (requestRoarUpdate) {
            raptor.UpdateImage_Roar();
        } else if (requestRunningUpdate) {
            raptor.UpdateImage_Run();
        } else if (requestBitingUpdate) {
            raptor.UpdateImage_Bite();
        } else if(requestIdleUpdate) {
            raptor.UpdateImage_Idle();
        }

         */
    }

    // keep for future use
    public static int getTimeElapsed() {
        return timeElapsedRaptor;
    }

    // notifies action listener know to change raptors current state to run
    public static void allowRequestRunningUpdate(){
        requestRunningUpdate = true;
        requestIdleUpdate = false;
        requestRoarUpdate = false;
        requestBitingUpdate = false;
    }

    // notifies action listener know to cancel raptors current state to run
    public static void denyRequestRunningUpdate(){
        requestRunningUpdate = false;
        requestIdleUpdate = true;
        requestRoarUpdate = false;
        requestBitingUpdate = false;
    }

    // notifies action listener know to change raptors current state to roar
    public static void allowRequestRoarUpdate(){
        requestRoarUpdate = true;
        requestRunningUpdate = false;
        requestIdleUpdate = false;
        requestBitingUpdate = false;
    }

    // notifies action listener know to cancel raptors current state to roar
    public static void denyRequestRoarUpdate(){
        requestRunningUpdate = false;
        requestIdleUpdate = true;
        requestRoarUpdate = false;
        requestBitingUpdate = false;
    }

    // notifies action listener know to change raptors current state to biting

    public static void allowRequestBitingUpdate(){
        requestRunningUpdate = false;
        requestIdleUpdate = false;
        requestRoarUpdate = false;
        requestBitingUpdate = true;
    }

    // notifies action listener know to cancel raptors current state to biting
    public static void denyRequestBitingUpdate(){
        requestRunningUpdate = false;
        requestIdleUpdate = true;
        requestRoarUpdate = false;
        requestBitingUpdate = false;
    }

    // allows the raptor to die by other classes when called
    public static void setIsDead(boolean isDead) {
        RaptorAnimation.isDead = isDead;
    }

    public boolean getIsDead() {
        return isDead;
    }

}
