package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;



public class FrostGuardian extends Walker {
    private ArrayList<String> idleAnimation, sliceAnimation, walkAnimation, jumpAnimation, deathAnimation, runAnimation;
    private int idleAnimationSize, sliceAnimationSize, walkAnimationSize, jumpAnimationSize, deathAnimationSize, runAnimationSize, allAnimationSize, idleAnimationPointer = 0, sliceAnimationPointer = 0, walkAnimationPointer = 0, jumpAnimationPointer = 0, deathAnimationPointer = 0, runAnimationPointer = 0, allAnimationPointer = 0;
    private static final Shape trollShape = new BoxShape(2.5f, 4.5f);

    public SolidFixture attackFixture;

    private int health = 100;

    public boolean stopWalking = false;

    public Timer frostGuardianTimer;

    public FrostGuardianAnimation frostGuardianAnimation;

    private GameLevel level;

    public FrostGuardian(GameLevel level, FrostGuardianSpawner frostGuardianSpawner) {
        super(level, trollShape);
        frostGuardianSpawner.reduceTotalFrostGuardian();
        this.level = level;
        frostGuardianAnimation = new FrostGuardianAnimation(this, true, frostGuardianSpawner, level);
        frostGuardianTimer = new javax.swing.Timer(50, frostGuardianAnimation);
        frostGuardianTimer.start();

        // creates new array lists for different animations and adds paths to images

        idleAnimation = new ArrayList<>();

        idleAnimation.add("data/FrostGuardian/idle/idle_1.png");
        idleAnimation.add("data/FrostGuardian/idle/idle_2.png");
        idleAnimation.add("data/FrostGuardian/idle/idle_3.png");
        idleAnimation.add("data/FrostGuardian/idle/idle_4.png");
        idleAnimation.add("data/FrostGuardian/idle/idle_5.png");
        idleAnimation.add("data/FrostGuardian/idle/idle_6.png");


        idleAnimationSize = idleAnimation.size();

        sliceAnimation = new ArrayList<>();

        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_1.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_2.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_3.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_4.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_5.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_6.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_7.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_8.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_9.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_10.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_11.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_12.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_13.png");
        sliceAnimation.add("data/FrostGuardian/1_atk/1_atk_14.png");


        sliceAnimationSize = sliceAnimation.size();

        walkAnimation = new ArrayList<>();

        walkAnimation.add("data/FrostGuardian/walk/walk_1.png");
        walkAnimation.add("data/FrostGuardian/walk/walk_2.png");
        walkAnimation.add("data/FrostGuardian/walk/walk_3.png");
        walkAnimation.add("data/FrostGuardian/walk/walk_4.png");
        walkAnimation.add("data/FrostGuardian/walk/walk_5.png");
        walkAnimation.add("data/FrostGuardian/walk/walk_6.png");
        walkAnimation.add("data/FrostGuardian/walk/walk_7.png");
        walkAnimation.add("data/FrostGuardian/walk/walk_8.png");
        walkAnimation.add("data/FrostGuardian/walk/walk_9.png");
        walkAnimation.add("data/FrostGuardian/walk/walk_10.png");




        walkAnimationSize = walkAnimation.size();

        jumpAnimation = new ArrayList<>();

        jumpAnimation.add("data/FrostGuardian/take_hit/take_hit_1.png");
        jumpAnimation.add("data/FrostGuardian/take_hit/take_hit_2.png");
        jumpAnimation.add("data/FrostGuardian/take_hit/take_hit_3.png");
        jumpAnimation.add("data/FrostGuardian/take_hit/take_hit_4.png");
        jumpAnimation.add("data/FrostGuardian/take_hit/take_hit_5.png");
        jumpAnimation.add("data/FrostGuardian/take_hit/take_hit_6.png");
        jumpAnimation.add("data/FrostGuardian/take_hit/take_hit_7.png");


        jumpAnimationSize = jumpAnimation.size();

        deathAnimation = new ArrayList<>();

        deathAnimation.add("data/FrostGuardian/death/death_1.png");
        deathAnimation.add("data/FrostGuardian/death/death_2.png");
        deathAnimation.add("data/FrostGuardian/death/death_3.png");
        deathAnimation.add("data/FrostGuardian/death/death_4.png");
        deathAnimation.add("data/FrostGuardian/death/death_5.png");
        deathAnimation.add("data/FrostGuardian/death/death_6.png");
        deathAnimation.add("data/FrostGuardian/death/death_7.png");
        deathAnimation.add("data/FrostGuardian/death/death_8.png");
        deathAnimation.add("data/FrostGuardian/death/death_9.png");
        deathAnimation.add("data/FrostGuardian/death/death_10.png");
        deathAnimation.add("data/FrostGuardian/death/death_11.png");
        deathAnimation.add("data/FrostGuardian/death/death_12.png");
        deathAnimation.add("data/FrostGuardian/death/death_13.png");
        deathAnimation.add("data/FrostGuardian/death/death_14.png");
        deathAnimation.add("data/FrostGuardian/death/death_15.png");
        deathAnimation.add("data/FrostGuardian/death/death_16.png");


        deathAnimationSize = deathAnimation.size();


        runAnimation = new ArrayList<>();

        runAnimation.add("data/FrostGuardian/run/walk_1.png");
        runAnimation.add("data/FrostGuardian/run/walk_2.png");
        runAnimation.add("data/FrostGuardian/run/walk_3.png");
        runAnimation.add("data/FrostGuardian/run/walk_4.png");
        runAnimation.add("data/FrostGuardian/run/walk_5.png");
        runAnimation.add("data/FrostGuardian/run/walk_6.png");
        runAnimation.add("data/FrostGuardian/run/walk_7.png");
        runAnimation.add("data/FrostGuardian/run/walk_8.png");
        runAnimation.add("data/FrostGuardian/run/walk_9.png");
        runAnimation.add("data/FrostGuardian/run/walk_10.png");




        runAnimationSize = runAnimation.size();
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Idle(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, idleAnimation, idleAnimationSize, 6, 17, 2, 5, false);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Jump(int timeElapsed, boolean flip) {
        if(timeElapsed % 2 == 0){
            if (!flip) {
                if (jumpAnimationPointer < jumpAnimationSize) {
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 17, 0, new Vec2(0, 2f));
                    jumpAnimationPointer++;
                } else {
                    jumpAnimationPointer = 0;
                    frostGuardianAnimation.slowed = true;
                    frostGuardianAnimation.setSlowedReps(0);
                    frostGuardianAnimation.denyRequestJumpUpdate();
                }
            } else {
                if (jumpAnimationPointer < jumpAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 17, 0, new Vec2(0, 2f));
                    image.flipHorizontal();
                    jumpAnimationPointer++;
                } else {
                    jumpAnimationPointer = 0;
                    frostGuardianAnimation.slowed = true;
                    frostGuardianAnimation.setSlowedReps(0);
                    frostGuardianAnimation.denyRequestJumpUpdate();
                }
            }
        }
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Slice(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, sliceAnimation, sliceAnimationSize, 5, 17, 2, 1, false);
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Walk(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, walkAnimation, walkAnimationSize, 4, 17, 2, 1, stopWalking);
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Dying(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, deathAnimation, deathAnimationSize, 2, 17, 2, 2, false);
    }

    public void UpdateImage_Run(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, runAnimation, runAnimationSize, 1, 17, 2, 1, false);

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
            frostGuardianAnimation.allowRequestDeathUpdate();
        }
    }

    public void animateCharacter(int timeElapsed, boolean flip, ArrayList<String> allAnimation, int allAnimationSize, int action, int scale, int alignY, int fps, boolean deny) {
        if (timeElapsed % fps == 0 && !deny) {
            if (allAnimationPointer < allAnimationSize) {
                removeAllImages();
                if(!flip){
                    new AttachedImage(this, new BodyImage(allAnimation.get(allAnimationPointer)), scale, 0, new Vec2(0, alignY));
                    if(allAnimationPointer == 10 && action == 5){
                        level.getSamurai().takenDamage(10);
                        level.getSamurai().applyForce(new Vec2(-10000000, 300000));
                    }
                }else{
                    AttachedImage image = new AttachedImage(this, new BodyImage(allAnimation.get(allAnimationPointer)), scale, 0, new Vec2(0, alignY));
                    image.flipHorizontal();
                    if(allAnimationPointer == 10 && action == 5){
                        level.getSamurai().takenDamage(10);
                        level.getSamurai().applyForce(new Vec2(10000000, 300000));
                    }
                }
                allAnimationPointer++;
            } else {
                allAnimationPointer = 0;
                switch (action){
                    case 1:
                        frostGuardianAnimation.denyRequestRunUpdate();
                        break;
                    case 2:
                        this.destroy();
                        frostGuardianAnimation.denyRequestDeathUpdate();
                        break;
                    case 3:
                        frostGuardianAnimation.slowed = true;
                        frostGuardianAnimation.setSlowedReps(0);
                        frostGuardianAnimation.denyRequestJumpUpdate();
                        break;
                    case 4:
                        if(!deny){
                            if(frostGuardianAnimation.slowed){
                                frostGuardianAnimation.incrementSlowedReps();
                            }
                            frostGuardianAnimation.denyRequestWalkUpdate();
                        }
                        else{
                            stopWalking = false;
                            frostGuardianAnimation.denyRequestWalkUpdate();
                        }
                        break;

                    case 5:
                        frostGuardianAnimation.denyRequestSliceUpdate();
                        break;
                }
            }
        }
    }
}
