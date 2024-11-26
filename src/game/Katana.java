package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;



public class Katana extends Walker {
    private ArrayList<String> idleAnimation, sliceAnimation, walkAnimation, jumpAnimation, deathAnimation, runAnimation;
    private int idleAnimationSize, sliceAnimationSize, walkAnimationSize, jumpAnimationSize, deathAnimationSize, runAnimationSize, allAnimationSize, allAnimationPointer = 0, idleAnimationPointer = 0, sliceAnimationPointer = 0, walkAnimationPointer = 0, jumpAnimationPointer = 0, deathAnimationPointer = 0, runAnimationPointer=0;
    private static final Shape katana = new BoxShape(2, 4);
    private int health = 100;

    public boolean stopWalking = false;

    public Timer katanaTimer;

    public KatanaAnimation katanaAnimation;

    private GameLevel level;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Katana(GameLevel level, KatanaSpawner katanaSpawner) {
        super(level, katana);

        this.level = level;
        katanaSpawner.reduceTotalKatanas();
        katanaAnimation = new KatanaAnimation(this, true, katanaSpawner, level);
        katanaTimer = new Timer(50, katanaAnimation);
        katanaTimer.start();

        // creates new array lists for different animations and adds paths to images

        idleAnimation = new ArrayList<>();

        idleAnimation.add("data/Samurai/SamuraiIdle/Idle(0).png");
        idleAnimation.add("data/Samurai/SamuraiIdle/Idle(1).png");
        idleAnimation.add("data/Samurai/SamuraiIdle/Idle(2).png");
        idleAnimation.add("data/Samurai/SamuraiIdle/Idle(3).png");
        idleAnimation.add("data/Samurai/SamuraiIdle/Idle(4).png");
        idleAnimation.add("data/Samurai/SamuraiIdle/Idle(5).png");

        idleAnimationSize = idleAnimation.size();

        sliceAnimation = new ArrayList<>();

        sliceAnimation.add("data/Samurai/SamuraiSlice/Attack_2(0).png");
        sliceAnimation.add("data/Samurai/SamuraiSlice/Attack_2(1).png");
        sliceAnimation.add("data/Samurai/SamuraiSlice/Attack_2(2).png");
        sliceAnimation.add("data/Samurai/SamuraiSlice/Attack_2(3).png");
        sliceAnimation.add("data/Samurai/SamuraiSlice/Attack_2(4).png");


        sliceAnimationSize = sliceAnimation.size();

        walkAnimation = idleAnimation;


        walkAnimationSize = walkAnimation.size();

        jumpAnimation = idleAnimation;



        jumpAnimationSize = jumpAnimation.size();

        deathAnimation = new ArrayList<>();

        deathAnimation.add("data/Samurai/SamuraiDead/Dead(0).png");
        deathAnimation.add("data/Samurai/SamuraiDead/Dead(1).png");
        deathAnimation.add("data/Samurai/SamuraiDead/Dead(2).png");
        deathAnimation.add("data/Samurai/SamuraiDead/Dead(3).png");
        deathAnimation.add("data/Samurai/SamuraiDead/Dead(4).png");
        deathAnimation.add("data/Samurai/SamuraiDead/Dead(5).png");

        deathAnimationSize = deathAnimation.size();

        runAnimation = new ArrayList<>();

        runAnimation.add("data/Samurai/SamuraiWalk/Walk(0).png");
        runAnimation.add("data/Samurai/SamuraiWalk/Walk(1).png");
        runAnimation.add("data/Samurai/SamuraiWalk/Walk(2).png");
        runAnimation.add("data/Samurai/SamuraiWalk/Walk(3).png");
        runAnimation.add("data/Samurai/SamuraiWalk/Walk(4).png");
        runAnimation.add("data/Samurai/SamuraiWalk/Walk(5).png");
        runAnimation.add("data/Samurai/SamuraiWalk/Walk(6).png");
        runAnimation.add("data/Samurai/SamuraiWalk/Walk(7).png");
        runAnimation.add("data/Samurai/SamuraiWalk/Walk(8).png");


        runAnimationSize = runAnimation.size();
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Idle(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, idleAnimation, idleAnimationPointer, 6, 7, -1, 5, false);
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Slice(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, sliceAnimation, sliceAnimationSize, 5, 10, 0, 1, false);
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Walk(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, walkAnimation, walkAnimationSize, 4, 7, -1, 3, stopWalking);
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Jump(int timeElapsed, boolean flip) {
        if(timeElapsed % 4 == 0){
            if (!flip) {
                if (jumpAnimationPointer < jumpAnimationSize) {
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 7, 0, new Vec2(0, -1));
                    jumpAnimationPointer++;
                } else {
                    jumpAnimationPointer = 0;
                    katanaAnimation.slowed = true;
                    katanaAnimation.setSlowedReps(0);
                    katanaAnimation.denyRequestJumpUpdate();
                }
            } else {
                if (jumpAnimationPointer < jumpAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 7, 0, new Vec2(0, -1));
                    image.flipHorizontal();
                    jumpAnimationPointer++;
                } else {
                    jumpAnimationPointer = 0;
                    katanaAnimation.slowed = true;
                    katanaAnimation.setSlowedReps(0);
                    katanaAnimation.denyRequestJumpUpdate();
                }
            }
        }
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Dying(int timeElapsed, boolean flip) {
        if(timeElapsed % 3 == 0){
            if (!flip) {
                if (deathAnimationPointer < deathAnimationSize) {
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 10, 0, new Vec2(0, 0f));
                    deathAnimationPointer++;
                } else {
                    deathAnimationPointer = 0;
                    new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 10, 0, new Vec2(0, 0f));
                    this.destroy();
                    katanaAnimation.denyRequestDeathUpdate();
                }
            } else {
                if (deathAnimationPointer < deathAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 10, 0, new Vec2(0, 0f));
                    image.flipHorizontal();
                    deathAnimationPointer++;
                } else {
                    deathAnimationPointer = 0;
                    new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 10, 0, new Vec2(0, 0f)).flipHorizontal();
                    this.destroy();
                    katanaAnimation.denyRequestDeathUpdate();
                }
            }
        }
    }

    // allows attack fixture to be destroyed by other classes
    public void destroyAttackFixture() {
        //attackFixture.destroy();
    }

    // called by enemy classes to decrease player health
    public void takenDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            this.startWalking(0);
            katanaAnimation.allowRequestDeathUpdate();
        }
    }

    public void UpdateImage_Run(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, runAnimation, runAnimationSize, 1, 10, 0, 1, false);
    }

    public void animateCharacter(int timeElapsed, boolean flip, ArrayList<String> allAnimation, int allAnimationSize, int action, int scale, int alignY, int fps, boolean deny) {
        if (timeElapsed % fps == 0 && !deny) {
            if (allAnimationPointer < allAnimationSize) {
                removeAllImages();
                if(!flip){
                    new AttachedImage(this, new BodyImage(allAnimation.get(allAnimationPointer)), scale, 0, new Vec2(0, alignY));
                }else{
                    AttachedImage image = new AttachedImage(this, new BodyImage(allAnimation.get(allAnimationPointer)), scale, 0, new Vec2(0, alignY));
                    image.flipHorizontal();
                }
                allAnimationPointer++;
            } else {
                allAnimationPointer = 0;
                switch (action){
                    case 1:
                        katanaAnimation.denyRequestRunUpdate();
                        break;
                    case 2:
                        this.destroy();
                        katanaAnimation.denyRequestDeathUpdate();
                        break;
                    case 3:
                        katanaAnimation.slowed = true;
                        katanaAnimation.setSlowedReps(0);
                        katanaAnimation.denyRequestJumpUpdate();
                        break;
                    case 4:
                        if(!deny){
                            if(katanaAnimation.slowed){
                                katanaAnimation.incrementSlowedReps();
                            }
                            katanaAnimation.denyRequestWalkUpdate();
                        }
                        else{
                            stopWalking = false;
                            katanaAnimation.denyRequestWalkUpdate();
                        }
                        break;

                    case 5:
                        level.getSamurai().takenDamage(10);
                        katanaAnimation.denyRequestSliceUpdate();
                        break;
                }
            }
        }
    }
}
