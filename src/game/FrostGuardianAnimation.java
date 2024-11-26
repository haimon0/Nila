package game;
import org.jbox2d.common.Vec2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrostGuardianAnimation implements ActionListener {
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


    public void setSlowedReps(int slowedReps) {
        this.slowedReps = slowedReps;
    }

    public void incrementSlowedReps(){
        slowedReps++;
        if(slowedReps>2){
            slowed=false;
        }
    }

    private int direction;
    public FrostGuardian frostGuardian;
    public static FrostGuardianSpawner frostGuardianSpawner;
    private GameLevel level;

    public FrostGuardianAnimation(FrostGuardian frostGuardian, boolean requestIdleUpdate, FrostGuardianSpawner frostGuardianSpawner, GameLevel level){
        this.frostGuardian = frostGuardian;
        this.requestIdleUpdate = requestIdleUpdate;
        this.level = level;
        FrostGuardianAnimation.frostGuardianSpawner = frostGuardianSpawner;
    }

    public void setRequestKnockbackUpdate(boolean requestKnockbackUpdate) {
        this.requestKnockbackUpdate = requestKnockbackUpdate;
    }

    public int getSlowedReps() {
        return slowedReps;
    }

    // actionPerformed is called every 50ms and updates samurai animation by request
    @Override
    public void actionPerformed(ActionEvent e) {
        timeElapsed += 1;
        if(!requestDeathUpdate){
            if(!requestJumpUpdate){
                if((frostGuardian.getPosition().x - level.getSamurai().getPosition().x)/-1 >0){
                    requestRunUpdate = true;
                    requestIdleUpdate = false;
                    requestWalkUpdate = false;
                    if(slowed){
                        frostGuardian.startWalking(1);
                        requestRunUpdate=false;
                        requestWalkUpdate = true;
                    }else {
                        frostGuardian.startWalking(10);
                    }
                    flip = true;
                }else if(!requestDeathUpdate){
                    requestRunUpdate = true;
                    requestIdleUpdate = false;
                    requestWalkUpdate = false;
                    if(slowed){
                        frostGuardian.startWalking(-1);
                        requestRunUpdate=false;
                        requestWalkUpdate = true;
                    }else {
                        frostGuardian.startWalking(-10);
                    }
                    flip = false;
                }
                if(Math.abs(frostGuardian.getPosition().x - level.getSamurai().getPosition().x) <= 10){
                    requestSliceUpdate = true;
                }
            }else{
                frostGuardian.stopWalking();
                if(direction>0){
                    frostGuardian.applyForce(new Vec2(50000, 0));
                }
                else{
                    frostGuardian.applyForce(new Vec2(-50000, 0));
                }
            }
        }

        //System.out.println(samurai.getPosition().y);
        if (requestDeathUpdate) {
            frostGuardian.UpdateImage_Dying(timeElapsed, flip);
        }
        else if (requestJumpUpdate) {
            frostGuardian.UpdateImage_Jump(timeElapsed, flip);
        }
        else if (requestSliceUpdate) {
            frostGuardian.UpdateImage_Slice(timeElapsed, flip);
        }
        else if(requestIdleUpdate & frostGuardian.getLinearVelocity().equals(new Vec2(0,0))){
            frostGuardian.UpdateImage_Idle(timeElapsed, flip);
        } else if (requestWalkUpdate) {
            frostGuardian.UpdateImage_Walk(timeElapsed, flip);
        }
        else if (requestRunUpdate) {
            frostGuardian.UpdateImage_Run(timeElapsed, flip);
        }
    }

    // keep for later use
    public double getTimeElapsed() {
        return timeElapsed;
    }

    // lets actionPerformed method know to play attack animation
    public void allowRequestSliceUpdate(Vec2 mcl){
        requestSliceUpdate = true;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestRunUpdate = false;
    }

    // lets actionPerformed method know to stop attack animation
    public void denyRequestSliceUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
        requestRunUpdate = false;
    }

    // lets actionPerformed method know to stop walk animation
    public void denyRequestWalkUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
        requestRunUpdate = false;
    }

    // lets actionPerformed method know to play walk animation
    public void allowRequestWalkUpdate(boolean b){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = true;
        frostGuardian.stopWalking = false;
        requestRunUpdate = false;
        this.flip = b;
    }

    // lets actionPerformed method know to stop jump animation
    public void denyRequestJumpUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
        requestJumpUpdate = false;
        requestRunUpdate = false;
    }

    // lets actionPerformed method know to play jump animation
    public void allowRequestJumpUpdate(int direction){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestJumpUpdate = true;
        requestRunUpdate = false;
        this.direction = direction;
    }

    // lets actionPerformed method know to stop death animation
    public void denyRequestDeathUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestDeathUpdate = false;
        requestRunUpdate = false;
        frostGuardianSpawner.reduceNumberOfFrostGuardians();
        frostGuardian.frostGuardianTimer.stop();
    }

    // lets actionPerformed method know to play death animation
    public void allowRequestDeathUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestDeathUpdate = true;
        requestRunUpdate = false;
    }

    public void denyRequestRunUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
        requestRunUpdate = false;
    }

    public void allowRequestRunUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestRunUpdate = true;
    }
}

