package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;


public class RaptorCollisionListener implements CollisionListener {
    // if player collides with raptor, they will take increased damage
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if(collisionEvent.getReportingBody() instanceof Raptor & collisionEvent.getOtherBody() instanceof Samurai){
            ((Samurai) collisionEvent.getOtherBody()).takenDamage(5);
        }
        else if(collisionEvent.getReportingBody() instanceof Samurai & collisionEvent.getOtherBody() instanceof Raptor){
            ((Samurai) collisionEvent.getReportingBody()).takenDamage(5);
        }
    }
}
