package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.DynamicBody;
import city.cs.engine.Walker;
import org.jbox2d.common.Vec2;

public class ArrowCollisionListener implements CollisionListener {
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if(collisionEvent.getReportingBody() instanceof Arrow){
            if(collisionEvent.getReportingBody() instanceof NormalArrow){
                if(collisionEvent.getOtherBody() instanceof Troll){
                    float arrowPosition = collisionEvent.getReportingBody().getPosition().y;
                    // if hit in the leg area deal 10 damage to troll

                    //change damage values
                    if(arrowPosition <= -6){
                        ((Troll) collisionEvent.getOtherBody()).takenDamage(100);
                    }
                    // if hit at chest area deal 20 damage to troll
                    else if(arrowPosition <= 0){
                        ((Troll) collisionEvent.getOtherBody()).takenDamage(300);
                    } else {
                        ((Troll) collisionEvent.getOtherBody()).takenDamage(500);
                    }
                } else if (collisionEvent.getOtherBody() instanceof Bird) {
                    collisionEvent.getOtherBody().destroy();
                }
                else if (collisionEvent.getOtherBody() instanceof Demon) {
                    ((Demon) collisionEvent.getOtherBody()).takenDamage(100);
                }
                else if (collisionEvent.getOtherBody() instanceof FrostGuardian) {
                    ((FrostGuardian) collisionEvent.getOtherBody()).takenDamage(100);
                }else if (collisionEvent.getOtherBody() instanceof Knight) {
                    ((Knight) collisionEvent.getOtherBody()).takenDamage(100);
                }
                else if (collisionEvent.getOtherBody() instanceof Katana) {
                    ((Katana) collisionEvent.getOtherBody()).takenDamage(100);
                }

            } else if (collisionEvent.getReportingBody() instanceof MoonArrow) {
                int direction;
                if(collisionEvent.getReportingBody().getLinearVelocity().x>0){
                    direction=-1;
                }else{
                    direction=1;
                }
                if(collisionEvent.getOtherBody() instanceof Troll){
                    ((Troll) collisionEvent.getOtherBody()).trollAnimation.allowRequestJumpUpdate(direction);
                } else if (collisionEvent.getOtherBody() instanceof Bird) {
                    ((Bird) collisionEvent.getOtherBody()).applyForce(new Vec2(50000*direction, 0));
                }
                else if (collisionEvent.getOtherBody() instanceof Demon) {
                    ((Demon) collisionEvent.getOtherBody()).demonAnimation.allowRequestJumpUpdate(direction);
                }
                else if (collisionEvent.getOtherBody() instanceof FrostGuardian) {
                    ((FrostGuardian) collisionEvent.getOtherBody()).frostGuardianAnimation.allowRequestJumpUpdate(direction);
                }else if (collisionEvent.getOtherBody() instanceof Knight) {
                    ((Knight) collisionEvent.getOtherBody()).knightAnimation.allowRequestJumpUpdate(direction);
                }
                else if (collisionEvent.getOtherBody() instanceof Katana) {
                    ((Katana) collisionEvent.getOtherBody()).katanaAnimation.allowRequestJumpUpdate(direction);
                }
            }
            ((Arrow) collisionEvent.getReportingBody()).getArrowTimer().stop();
            collisionEvent.getReportingBody().destroy();
        }
    }
}
