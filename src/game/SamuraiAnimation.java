package game;
import org.jbox2d.common.Vec2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SamuraiAnimation implements ActionListener {
    private int timeElapsed;
    private boolean requestIdleUpdate, requestSliceUpdate = false, requestWalkUpdate = false, requestJumpUpdate = false, requestDeathUpdate, flip = false;
    public boolean keyReleasedAD, keyReleasedP, noKeyPressed = true;

    public Vec2 mouseClickLocation;

    private Samurai samurai;

    private boolean allEnemysDefeated = false;

    public void setSamurai(Samurai samurai) {
        this.samurai = samurai;
    }

    private GameLevel level;

    public void setLevel(GameLevel level) {
        this.level = level;
    }

    public SamuraiAnimation(Samurai samurai, boolean requestIdleUpdate){
        this.samurai = samurai;
        this.requestIdleUpdate = requestIdleUpdate;
    }

    public void setAllEnemysDefeated(boolean allEnemysDefeated) {
        this.allEnemysDefeated = allEnemysDefeated;
    }

    // actionPerformed is called every 50ms and updates samurai animation by request
    @Override
    public void actionPerformed(ActionEvent e) {
        if(samurai.getPosition().x > 31.5 && allEnemysDefeated){
            allEnemysDefeated = false;
            level.isComplete();
        }
        timeElapsed += 1;
        if(keyReleasedAD & !noKeyPressed){
            requestWalkUpdate = false;
            samurai.stopWalking = true;
            requestIdleUpdate = true;
        }else if(!keyReleasedAD & !noKeyPressed) {
            requestWalkUpdate = true;
            samurai.stopWalking = false;
            requestIdleUpdate = false;
        }
        //System.out.println(samurai.getPosition().y);
        if (requestDeathUpdate) {
            samurai.UpdateImage_Dying(timeElapsed, flip);
        }
        else if (requestSliceUpdate) {
            samurai.UpdateImage_Slice(timeElapsed, flip, mouseClickLocation);
        }
        else if(requestIdleUpdate & samurai.getLinearVelocity().equals(new Vec2(0,0))){
            samurai.UpdateImage_Idle(timeElapsed, flip);
        } else if (requestWalkUpdate) {
            samurai.UpdateImage_Walk(timeElapsed, flip);
        } else if (requestJumpUpdate) {
            samurai.UpdateImage_Jump(timeElapsed, flip);
        }
    }

    // keep for later use
    public double getTimeElapsed() {
        return timeElapsed;
    }

    // lets actionPerformed method know to play attack animation
    public void allowRequestSliceUpdate(Vec2 mcl, Boolean flip){
        mouseClickLocation = mcl;
        requestSliceUpdate = true;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        this.flip = flip;
    }

    // lets actionPerformed method know to stop attack animation
    public void denyRequestSliceUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
    }

    // lets actionPerformed method know to stop walk animation
    public void denyRequestWalkUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
    }

    // lets actionPerformed method know to play walk animation
    public void allowRequestWalkUpdate(boolean b){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = true;
        samurai.stopWalking = false;
        this.flip = b;
    }

    // lets actionPerformed method know to stop jump animation
    public void denyRequestJumpUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
        requestJumpUpdate = false;
    }

    // lets actionPerformed method know to play jump animation
    public void allowRequestJumpUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestJumpUpdate = true;
    }

    // lets actionPerformed method know to stop death animation
    public void denyRequestDeathUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = true;
        requestWalkUpdate = false;
        requestDeathUpdate = false;
        level.getGame().showDeathScreen();
    }

    // lets actionPerformed method know to play death animation
    public void allowRequestDeathUpdate(){
        requestSliceUpdate = false;
        requestIdleUpdate = false;
        requestWalkUpdate = false;
        requestDeathUpdate = true;
    }


}
