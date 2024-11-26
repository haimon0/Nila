package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;

public class Bird extends DynamicBody {
    private static final Shape birdShape = new BoxShape(1, 1);

    private static ArrayList<String> flyingAnimation;

    private static boolean flyingAnimationFlipped = false;

    private static int flyingAnimationSize, flyingAnimationPointer = 0;

    public Bird(GameLevel level) {
        super(level, birdShape);

        // adds sensor to bird with a square shape

        Shape sensorShape = new BoxShape(2, 2);
        Sensor sensor = new Sensor(this, sensorShape, 1f);
        BirdSensorListener bsl = new BirdSensorListener();
        sensor.addSensorListener(bsl);

        // creates a new arraylist and adds paths to images to it

        flyingAnimation = new ArrayList<>();

        flyingAnimation.add("data/Blu/Blu(0).png");
        flyingAnimation.add("data/Blu/Blu(1).png");
        flyingAnimation.add("data/Blu/Blu(2).png");
        flyingAnimation.add("data/Blu/Blu(3).png");
        flyingAnimation.add("data/Blu/Blu(4).png");
        flyingAnimation.add("data/Blu/Blu(5).png");
        flyingAnimation.add("data/Blu/Blu(6).png");
        flyingAnimation.add("data/Blu/Blu(7).png");
        flyingAnimation.add("data/Blu/Blu(8).png");

        /* size of the array is saved as it is constant and
        does not need to be called repeatedly
         */

        flyingAnimationSize = flyingAnimation.size();
    }

    /* iterates through the flying image array every other timeElapsed increment
       and flips the image if requested
     */
    public void UpdateImage_Flying(int timeElapsed) {
        if (timeElapsed % 2 == 0) {
            if(!flyingAnimationFlipped){
                if (flyingAnimationPointer < flyingAnimationSize) {
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(flyingAnimation.get(flyingAnimationPointer)), 4, 0, new Vec2(0, 0));
                    flyingAnimationPointer++;
                } else {
                    flyingAnimationPointer = 0;
                }
            }
            else {
                if (flyingAnimationPointer < flyingAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(flyingAnimation.get(flyingAnimationPointer)), 4, 0, new Vec2(0, 0));
                    image.flipHorizontal();
                    flyingAnimationPointer++;
                } else {
                    flyingAnimationPointer = 0;
                }
            }
        }
    }

        // flips image upon request
    public void flipImage(Boolean flyingAnimationFlipped){
        Bird.flyingAnimationFlipped = flyingAnimationFlipped;
    }
}


