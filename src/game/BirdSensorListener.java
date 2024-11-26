package game;

import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;

public class BirdSensorListener implements SensorListener {
    private WorldBoundary previousWorldBoundary;
    private boolean newDirection = false;

    /* upon contact with a body this will be called if the
       body is a world boundary it will request to change
       the direction the bird is flying. It makes sure that the
       colliding boundary isn't the same as previous boundary
       before updating so bird doesn't get stuck in one direction
     */
    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if(sensorEvent.getContactBody() != previousWorldBoundary){
            if(sensorEvent.getContactBody() instanceof WorldBoundary){
                previousWorldBoundary = (WorldBoundary) sensorEvent.getContactBody();
                if(newDirection){
                    BirdAnimation.updateRequestFlyRight();
                    newDirection = false;
                }
                else {
                    BirdAnimation.updateRequestFlyLeft();
                    newDirection = true;
                }
            }
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent) {
    }
}
