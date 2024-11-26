package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;



public class Samurai extends Walker {
    private ArrayList<String> idleAnimation, sliceAnimation, walkAnimation, jumpAnimation, deathAnimation;
    private int idleAnimationSize, sliceAnimationSize, walkAnimationSize, jumpAnimationSize, deathAnimationSize, idleAnimationPointer = 0, sliceAnimationPointer = 0, walkAnimationPointer = 0, jumpAnimationPointer = 0, deathAnimationPointer = 0;

    public Timer getSamuraiTimer() {
        return samuraiTimer;
    }

    private static final Shape samuraiShape = new BoxShape(1, 2);

    private int arrowType = 0;
    // create custom shapes for attack hitboxes
    private final PolygonShape attackFixtureShapeRight = new PolygonShape(2.1f, 0.1f, 1.9f, 4.3f, 4.1f,3f, 5.2f,1.4f, 5.7f,0.2f, 3.1f,0f);
    static final PolygonShape attackFixtureShapeLeft = new PolygonShape(-2.1f, 0.1f, -1.9f, 4.3f, -4.1f,3f, -5.2f,1.4f, -5.7f,0.2f, -3.1f,0f);
    public SolidFixture attackFixture;

    private int health = 100;

    private int healthSize = 300;

    public SamuraiAnimation getSamuraiAnimation() {
        return samuraiAnimation;
    }

    public boolean stopWalking = false;

    private World world;

    private Timer samuraiTimer;

    private SamuraiAnimation samuraiAnimation;

    public Samurai(World world){
        super(world, samuraiShape);

        this.world = world;

        samuraiAnimation = new SamuraiAnimation(this, true);

        // create and start a timer that loops every 50ms, used to update samurai animation
        samuraiTimer = new Timer(50, samuraiAnimation);
        samuraiTimer.start();

        // creates new array lists for different animations and adds paths to images

        idleAnimation = new ArrayList<>();

        idleAnimation.add("data/ArcherWalk/Walk(0).png");

        idleAnimationSize = idleAnimation.size();

        sliceAnimation = new ArrayList<>();

        sliceAnimation.add("data/ArcherFire/Shot(0).png");
        sliceAnimation.add("data/ArcherFire/Shot(1).png");
        sliceAnimation.add("data/ArcherFire/Shot(2).png");

        sliceAnimationSize = sliceAnimation.size();

        walkAnimation = new ArrayList<>();

        walkAnimation.add("data/ArcherWalk/Walk(1).png");
        walkAnimation.add("data/ArcherWalk/Walk(2).png");
        walkAnimation.add("data/ArcherWalk/Walk(3).png");
        walkAnimation.add("data/ArcherWalk/Walk(4).png");
        walkAnimation.add("data/ArcherWalk/Walk(5).png");



        walkAnimationSize = walkAnimation.size();

        jumpAnimation = new ArrayList<>();

        jumpAnimation.add("data/ArcherWalk/Walk(2).png");





        jumpAnimationSize = jumpAnimation.size();

        deathAnimation = new ArrayList<>();

        deathAnimation.add("data/ArcherDead/Dead(0).png");




        deathAnimationSize = deathAnimation.size();
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Idle(int timeElapsed, boolean flip){
        if(!flip) {
            if(timeElapsed % 5 ==0){
                if(idleAnimationPointer<idleAnimationSize){
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(idleAnimation.get(idleAnimationPointer)), 6, 0, new Vec2(0,0));
                    idleAnimationPointer++;
                } else {
                    idleAnimationPointer = 0;
                }
            }
        }else{
            if(timeElapsed % 5 ==0){
                if(idleAnimationPointer<idleAnimationSize){
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(idleAnimation.get(idleAnimationPointer)), 6, 0, new Vec2(0,0));
                    image.flipHorizontal();
                    idleAnimationPointer++;
                } else {
                    idleAnimationPointer = 0;
                }
            }
        }
    }

    public void setArrowType(int arrowType) {
        this.arrowType = arrowType;
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Slice(double timeElapsed, boolean flip, Vec2 mcl){
        if(!flip){
            if(timeElapsed % 3 ==0){
                if(sliceAnimationPointer<sliceAnimationSize){
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(sliceAnimation.get(sliceAnimationPointer)), 6, 0, new Vec2(0,0f));
                    sliceAnimationPointer++;
                } else {
                    switch (arrowType) {
                        case 0 -> new NormalArrow(world, mcl, this.getPosition());
                        case 1 -> new MoonArrow(world, mcl, this.getPosition());
                    }
                    sliceAnimationPointer = 0;
                    samuraiAnimation.denyRequestSliceUpdate();
                }
            }
        }else {
            if (timeElapsed % 3 == 0) {
                if (sliceAnimationPointer < sliceAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(sliceAnimation.get(sliceAnimationPointer)), 6, 0, new Vec2(0, 0f));
                    image.flipHorizontal();
                    sliceAnimationPointer++;
                } else {
                    switch (arrowType) {
                        case 0 -> new NormalArrow(world, mcl, this.getPosition());
                        case 1 -> new MoonArrow(world, mcl, this.getPosition());
                    }

                    sliceAnimationPointer = 0;
                    samuraiAnimation.denyRequestSliceUpdate();
                }
            }
        }
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Walk(int timeElapsed, boolean flip) {
        if(!stopWalking) {
            if (timeElapsed % 5 == 0 | walkAnimationPointer==0) {
                if (!flip) {
                    if (walkAnimationPointer < walkAnimationSize) {
                        removeAllImages();
                        new AttachedImage(this, new BodyImage(walkAnimation.get(walkAnimationPointer)), 6, 0, new Vec2(0, 0f));
                        walkAnimationPointer++;
                    } else {
                        walkAnimationPointer = 0;
                        samuraiAnimation.denyRequestWalkUpdate();
                    }
                } else {
                    if (walkAnimationPointer < walkAnimationSize) {
                        removeAllImages();
                        AttachedImage image = new AttachedImage(this, new BodyImage(walkAnimation.get(walkAnimationPointer)), 6, 0, new Vec2(0, 0f));
                        image.flipHorizontal();
                        walkAnimationPointer++;
                    } else {
                        walkAnimationPointer = 0;
                        samuraiAnimation.denyRequestWalkUpdate();
                    }
                }
            }
        }else{
            stopWalking = false;
            walkAnimationPointer = 0;
            samuraiAnimation.denyRequestWalkUpdate();
        }
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Jump(int timeElapsed, boolean flip) {
        if(timeElapsed % 5 ==0){
            if(!flip){
                if (jumpAnimationPointer < jumpAnimationSize) {
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 6, 0, new Vec2(0, 0f));
                    jumpAnimationPointer++;
                } else {
                    jumpAnimationPointer = 0;
                    samuraiAnimation.denyRequestJumpUpdate();
                }
            }
            else {
                if (jumpAnimationPointer < jumpAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 6, 0, new Vec2(0, 0f));
                    image.flipHorizontal();
                    jumpAnimationPointer++;
                } else {
                    jumpAnimationPointer = 0;
                    samuraiAnimation.denyRequestJumpUpdate();
                }
            }
        }
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Dying(int timeElapsed, boolean flip) {
        if(timeElapsed % 5 ==0){
            if(!flip){
                if (deathAnimationPointer < deathAnimationSize) {
                    this.setPosition(new Vec2(this.getPosition().x, -11));
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 3, 0, new Vec2(0, -3f));
                    deathAnimationPointer++;
                } else {
                    deathAnimationPointer = 0;
                    samuraiAnimation.denyRequestDeathUpdate();
                    new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 3, 0, new Vec2(0, -3f));
                }
            }
            else {
                if (deathAnimationPointer < deathAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 3, 0, new Vec2(0, -3f));
                    image.flipHorizontal();
                    deathAnimationPointer++;
                } else {
                    deathAnimationPointer = 0;
                    samuraiAnimation.denyRequestDeathUpdate();
                    new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 3, 0, new Vec2(0, -3f));
                }
            }
        }
    }

    // allows attack fixture to be destroyed by other classes
    public void destroyAttackFixture(){
        //attackFixture.destroy();
    }


    public void setHealth(int health) {
        this.health = health;
    }

    public void setHealthSize(int health) {
        this.healthSize = health;
    }

    // called by enemy classes to decrease player health
    public void takenDamage(int damage){
        if(health<=0){
            PlayerController.setLock(true);
            PlayerMouseListener.setLock(true);
            this.setLinearVelocity(new Vec2(0, 0));
            samuraiAnimation.allowRequestDeathUpdate();
        }
        health -= damage;
        healthSize -= damage*3;
    }

    // updates screen accordingly
    public int getHealthSize(){
        return healthSize;
    }

    // health can be retrieved by other classes
    public int getHealth() {
        return health;
    }
}
