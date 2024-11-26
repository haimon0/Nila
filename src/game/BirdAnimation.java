package game;

import org.jbox2d.common.Vec2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BirdAnimation implements ActionListener {
    private static int timeElapsed;
    private static boolean requestFlyingRight, requestFlyingLeft;

    private static Bird bird;

    private static int xVelocity = 5;
    private static float yVelocity = 0.5F;

    // bird is passed in and is initially set to fly right
    public BirdAnimation(Bird bird, boolean requestFlyingRightUpdate){
        BirdAnimation.bird = bird;
        BirdAnimation.requestFlyingRight = requestFlyingRightUpdate;
    }

    /* action performed will keep calling at a rate set by birdTimer,
       it will flip bird image and change direction that it is
       flying if needed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        timeElapsed += 1;
        bird.UpdateImage_Flying(timeElapsed);
        if (requestFlyingRight){
            xVelocity = 5;
            bird.setLinearVelocity(new Vec2(xVelocity, yVelocity));
            bird.flipImage(false);
        } else if (requestFlyingLeft) {
            xVelocity = -5;
            bird.setLinearVelocity(new Vec2(xVelocity, yVelocity));
            bird.flipImage(true);
        }


        if (bird.getPosition().y >= 16){
            yVelocity = (float) -0.5;
            bird.setLinearVelocity(new Vec2(xVelocity, yVelocity));
        }
        else if (bird.getPosition().y <= 14){
            yVelocity = 0.5F;
            bird.setLinearVelocity(new Vec2(xVelocity, yVelocity));
        }
    }

    // keep for later use
    public static double getTimeElapsed() {
        return timeElapsed;
    }

    // lets the actionPerformed method know that bird need to fly right
    public static void updateRequestFlyRight(){
        requestFlyingRight = true;
        requestFlyingLeft = false;
    }

    // lets the actionPerformed method know that bird need to fly left
    public static void updateRequestFlyLeft(){
        requestFlyingRight = false;
        requestFlyingLeft = true;
    }

}

