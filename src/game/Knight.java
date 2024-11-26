package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;



public class Knight extends Walker {
    private ArrayList<String> idleAnimation, sliceAnimation, walkAnimation, jumpAnimation, deathAnimation, runAnimation;
    private int idleAnimationSize, sliceAnimationSize, walkAnimationSize, jumpAnimationSize, deathAnimationSize, runAnimationSize, idleAnimationPointer = 0, sliceAnimationPointer = 0, walkAnimationPointer = 0, jumpAnimationPointer = 0, deathAnimationPointer = 0, runAnimationPointer=0;
    private static final Shape knightShape = new BoxShape(2, 4);
    private int health = 100;

    public boolean stopWalking = false;

    public Timer knightTimer;

    public KnightAnimation knightAnimation;

    private GameLevel level;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Knight(GameLevel level, KnightSpawner knightSpawner) {
        super(level, knightShape);

        this.level = level;
        knightSpawner.reduceTotalKnights();
        knightAnimation = new KnightAnimation(this, true, knightSpawner, level);
        knightTimer = new Timer(50, knightAnimation);
        knightTimer.start();

        // creates new array lists for different animations and adds paths to images

        idleAnimation = new ArrayList<>();

        idleAnimation.add("data/Knight/idle0.png");
        idleAnimation.add("data/Knight/idle1.png");
        idleAnimation.add("data/Knight/idle2.png");
        idleAnimation.add("data/Knight/idle3.png");

        idleAnimationSize = idleAnimation.size();

        sliceAnimation = new ArrayList<>();

        sliceAnimation.add("data/Knight/attack0.png");
        sliceAnimation.add("data/Knight/attack1.png");
        sliceAnimation.add("data/Knight/attack2.png");
        sliceAnimation.add("data/Knight/attack3.png");
        sliceAnimation.add("data/Knight/attack4.png");


        sliceAnimationSize = sliceAnimation.size();

        walkAnimation = idleAnimation;


        walkAnimationSize = walkAnimation.size();

        jumpAnimation = new ArrayList<>();

        jumpAnimation.add("data/Knight/hurt0.png");
        jumpAnimation.add("data/Knight/hurt1.png");



        jumpAnimationSize = jumpAnimation.size();

        deathAnimation = new ArrayList<>();

        deathAnimation.add("data/Knight/death0.png");
        deathAnimation.add("data/Knight/death1.png");
        deathAnimation.add("data/Knight/death2.png");
        deathAnimation.add("data/Knight/death3.png");
        deathAnimation.add("data/Knight/death4.png");
        deathAnimation.add("data/Knight/death5.png");

        deathAnimationSize = deathAnimation.size();

        runAnimation = new ArrayList<>();

        runAnimation.add("data/Knight/run1.png");
        runAnimation.add("data/Knight/run2.png");
        runAnimation.add("data/Knight/run3.png");
        runAnimation.add("data/Knight/run4.png");
        runAnimation.add("data/Knight/run5.png");
        runAnimation.add("data/Knight/run6.png");
        runAnimation.add("data/Knight/run7.png");

        runAnimationSize = runAnimation.size();
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Idle(int timeElapsed, boolean flip) {
        if (!flip) {
            if (timeElapsed % 5 == 0) {
                if (idleAnimationPointer < idleAnimationSize) {
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(idleAnimation.get(idleAnimationPointer)), 8, 0, new Vec2(0, 0));
                    idleAnimationPointer++;
                } else {
                    idleAnimationPointer = 0;
                }
            }
        } else {
            if (timeElapsed % 5 == 0) {
                if (idleAnimationPointer < idleAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(idleAnimation.get(idleAnimationPointer)), 10, 0, new Vec2(0, -1));
                    image.flipHorizontal();
                    idleAnimationPointer++;
                } else {
                    idleAnimationPointer = 0;
                }
            }
        }
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Slice(double timeElapsed, boolean flip) {
        if (!flip) {
            if (timeElapsed % 1 == 0) {
                if (sliceAnimationPointer < sliceAnimationSize) {
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(sliceAnimation.get(sliceAnimationPointer)), 8, 0, new Vec2(0, 0f));
                    sliceAnimationPointer++;
                } else {
                    level.getSamurai().takenDamage(2);
                    sliceAnimationPointer = 0;
                    knightAnimation.denyRequestSliceUpdate();
                }
            }
        } else {
            if (timeElapsed % 1 == 0) {
                if (sliceAnimationPointer < sliceAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(sliceAnimation.get(sliceAnimationPointer)), 8, 0, new Vec2(0, -1f));
                    image.flipHorizontal();
                    sliceAnimationPointer++;
                } else {
                    level.getSamurai().takenDamage(2);
                    sliceAnimationPointer = 0;
                    knightAnimation.denyRequestSliceUpdate();
                }
            }
        }
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Walk(int timeElapsed, boolean flip) {
        if(timeElapsed % 3 == 0){
            if (!stopWalking) {
                if (!flip) {
                    if (walkAnimationPointer < walkAnimationSize) {
                        removeAllImages();
                        new AttachedImage(this, new BodyImage(walkAnimation.get(walkAnimationPointer)), 8, 0, new Vec2(0, 0f));
                        walkAnimationPointer++;
                    } else {
                        walkAnimationPointer = 0;
                        if(knightAnimation.slowed){
                            knightAnimation.incrementSlowedReps();
                        }
                        knightAnimation.denyRequestWalkUpdate();
                    }
                } else {
                    if (walkAnimationPointer < walkAnimationSize) {
                        removeAllImages();
                        AttachedImage image = new AttachedImage(this, new BodyImage(walkAnimation.get(walkAnimationPointer)), 8, 0, new Vec2(0, -1f));
                        image.flipHorizontal();
                        walkAnimationPointer++;
                    } else {
                        walkAnimationPointer = 0;
                        if(knightAnimation.slowed){
                            knightAnimation.incrementSlowedReps();
                        }
                        knightAnimation.denyRequestWalkUpdate();
                    }
                }
            } else {
                stopWalking = false;
                walkAnimationPointer = 0;
                knightAnimation.denyRequestWalkUpdate();
            }
        }
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Jump(int timeElapsed, boolean flip) {
        if(timeElapsed % 4 == 0){
            if (!flip) {
                if (jumpAnimationPointer < jumpAnimationSize) {
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 8, 0, new Vec2(0, 0));
                    jumpAnimationPointer++;
                } else {
                    jumpAnimationPointer = 0;
                    knightAnimation.slowed = true;
                    knightAnimation.setSlowedReps(0);
                    knightAnimation.denyRequestJumpUpdate();
                }
            } else {
                if (jumpAnimationPointer < jumpAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 8, 0, new Vec2(0, -1));
                    image.flipHorizontal();
                    jumpAnimationPointer++;
                } else {
                    jumpAnimationPointer = 0;
                    knightAnimation.slowed = true;
                    knightAnimation.setSlowedReps(0);
                    knightAnimation.denyRequestJumpUpdate();
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
                    new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 8, 0, new Vec2(0, 0f));
                    deathAnimationPointer++;
                } else {
                    deathAnimationPointer = 0;
                    new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 8, 0, new Vec2(0, -1f));
                    this.destroy();
                    knightAnimation.denyRequestDeathUpdate();
                }
            } else {
                if (deathAnimationPointer < deathAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 8, 0, new Vec2(0, 0f));
                    image.flipHorizontal();
                    deathAnimationPointer++;
                } else {
                    deathAnimationPointer = 0;
                    new AttachedImage(this, new BodyImage(deathAnimation.get(deathAnimationPointer)), 8, 0, new Vec2(0, -1f)).flipHorizontal();
                    this.destroy();
                    knightAnimation.denyRequestDeathUpdate();
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
            knightAnimation.allowRequestDeathUpdate();
        }
    }

    public void UpdateImage_Run(int timeElapsed, boolean flip) {
        if (!flip) {
            if (runAnimationPointer < runAnimationSize) {
                removeAllImages();
                new AttachedImage(this, new BodyImage(runAnimation.get(runAnimationPointer)), 8, 0, new Vec2(0, 0f));
                runAnimationPointer++;
            } else {
                runAnimationPointer = 0;
                knightAnimation.denyRequestRunUpdate();
            }
        } else {
            if (runAnimationPointer < runAnimationSize) {
                removeAllImages();
                AttachedImage image = new AttachedImage(this, new BodyImage(runAnimation.get(runAnimationPointer)), 8, 0, new Vec2(0, -1f));
                image.flipHorizontal();
                runAnimationPointer++;
            } else {
                runAnimationPointer = 0;
                knightAnimation.denyRequestRunUpdate();
            }
        }
    }
}
