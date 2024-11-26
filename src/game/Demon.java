package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;


/**
 * Creates boss-type enemy Demon, this enemy can knock up player and is immune to knock-back
 */
public class Demon extends Walker {
    private ArrayList<String> idleAnimation, sliceAnimation, jumpAnimation, deathAnimation, runAnimation;
    private int idleAnimationSize, sliceAnimationSize, jumpAnimationSize, deathAnimationSize, runAnimationSize, allAnimationSize, allAnimationPointer = 0, idleAnimationPointer = 0, sliceAnimationPointer = 0, walkAnimationPointer = 0, jumpAnimationPointer = 0, deathAnimationPointer = 0, runAnimationPointer=0;
    private static final Shape demonShape = new BoxShape(7.5f, 15);
    private int health = 100;

    public boolean stopWalking = false;

    public Timer demonTimer;

    public DemonAnimation demonAnimation;

    private GameLevel level;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Creates enemy boss demon and assigns it a large hit box. Paths to images are taken from the data directory and stored
     * in arraylists. These arraylist are then iterated through to produce an animation. Constructor creates a demon animation
     * object to handle demon visuals and AI. This is then assigned to a demon timer which will update the state of the demon every
     * 50ms
     *
     * @param level - Passes in current level the demon is in, this is used to decrement player health or knock them up
     * @param demonSpawner - spawner that spawned this demon in, used to notify spawner when demon is dead, allow player to exit level
     */

    public Demon(GameLevel level, DemonSpawner demonSpawner) {
        super(level, demonShape);

        this.level = level;
        demonSpawner.reduceTotalDemons();
        demonAnimation = new DemonAnimation(this, true, demonSpawner, level);
        demonTimer = new javax.swing.Timer(50, demonAnimation);
        demonTimer.start();

        // creates new array lists for different animations and adds paths to images

        idleAnimation = new ArrayList<>();

        idleAnimation.add("data/Demon/01_demon_idle/demon_idle_1.png");
        idleAnimation.add("data/Demon/01_demon_idle/demon_idle_2.png");
        idleAnimation.add("data/Demon/01_demon_idle/demon_idle_3.png");
        idleAnimation.add("data/Demon/01_demon_idle/demon_idle_4.png");
        idleAnimation.add("data/Demon/01_demon_idle/demon_idle_5.png");
        idleAnimation.add("data/Demon/01_demon_idle/demon_idle_6.png");

        idleAnimationSize = idleAnimation.size();

        sliceAnimation = new ArrayList<>();

        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_1.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_2.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_3.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_4.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_5.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_6.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_7.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_8.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_9.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_10.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_11.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_12.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_13.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_14.png");
        sliceAnimation.add("data/Demon/03_demon_cleave/demon_cleave_15.png");




        sliceAnimationSize = sliceAnimation.size();

        jumpAnimation = new ArrayList<>();

        jumpAnimation.add("data/Demon/04_demon_take_hit/demon_take_hit_1.png");
        jumpAnimation.add("data/Demon/04_demon_take_hit/demon_take_hit_2.png");
        jumpAnimation.add("data/Demon/04_demon_take_hit/demon_take_hit_3.png");
        jumpAnimation.add("data/Demon/04_demon_take_hit/demon_take_hit_4.png");
        jumpAnimation.add("data/Demon/04_demon_take_hit/demon_take_hit_5.png");



        jumpAnimationSize = jumpAnimation.size();

        deathAnimation = new ArrayList<>();

        deathAnimation.add("data/Demon/05_demon_death/demon_death_1.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_2.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_3.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_4.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_5.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_6.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_7.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_8.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_9.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_10.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_11.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_12.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_13.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_14.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_15.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_16.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_17.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_18.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_19.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_20.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_21.png");
        deathAnimation.add("data/Demon/05_demon_death/demon_death_22.png");







        deathAnimationSize = deathAnimation.size();

        runAnimation = new ArrayList<>();

        runAnimation.add("data/Demon/02_demon_walk/demon_walk_1.png");
        runAnimation.add("data/Demon/02_demon_walk/demon_walk_2.png");
        runAnimation.add("data/Demon/02_demon_walk/demon_walk_3.png");
        runAnimation.add("data/Demon/02_demon_walk/demon_walk_4.png");
        runAnimation.add("data/Demon/02_demon_walk/demon_walk_5.png");
        runAnimation.add("data/Demon/02_demon_walk/demon_walk_6.png");
        runAnimation.add("data/Demon/02_demon_walk/demon_walk_7.png");
        runAnimation.add("data/Demon/02_demon_walk/demon_walk_8.png");
        runAnimation.add("data/Demon/02_demon_walk/demon_walk_9.png");
        runAnimation.add("data/Demon/02_demon_walk/demon_walk_10.png");
        runAnimation.add("data/Demon/02_demon_walk/demon_walk_11.png");
        runAnimation.add("data/Demon/02_demon_walk/demon_walk_12.png");



        runAnimationSize = runAnimation.size();
    }

    /**
     * Passes the arraylist containing idle animation to animate character to be shown on screen
     */
    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Idle(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, idleAnimation, idleAnimationSize, 5, 60, 15, 2, false);
    }

    /**
     * Passes the arraylist containing slice animation to animate character to be shown on screen
     */
    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Slice(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, sliceAnimation, sliceAnimationSize, 5, 60, 15, 2, false);
    }

    /**
     * Passes the arraylist containing walk animation to animate character to be shown on screen
     */
    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Walk(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, idleAnimation, idleAnimationSize, 4, 60, 15, 2, stopWalking);
    }

    /**
     * Iterates through the jump animation but doesn't make a call to animate character, as jump as precedence over other animations
     */
    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Jump(int timeElapsed, boolean flip) {
        if(timeElapsed % 2 == 0) {
            if (!flip) {
                if (jumpAnimationPointer < jumpAnimationSize) {
                    removeAllImages();
                    new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 60, 0, new Vec2(0, 15f));
                    jumpAnimationPointer++;
                } else {
                    jumpAnimationPointer = 0;
                    demonAnimation.slowed = true;
                    demonAnimation.setSlowedReps(0);
                    demonAnimation.denyRequestJumpUpdate();
                }
            } else {
                if (jumpAnimationPointer < jumpAnimationSize) {
                    removeAllImages();
                    AttachedImage image = new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 60, 0, new Vec2(0, 15f));
                    image.flipHorizontal();
                    jumpAnimationPointer++;
                } else {
                    jumpAnimationPointer = 0;
                    demonAnimation.slowed = true;
                    demonAnimation.setSlowedReps(0);
                    demonAnimation.denyRequestJumpUpdate();
                }
            }
        }
    }

    /**
     * Passes the arraylist containing death animation to animate character to be shown on screen
     */
    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Dying(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, deathAnimation, deathAnimationSize, 2, 60, 15, 2, false);
    }

    /**
     * called by arrow collision listener when it came in contact with this demon.
     * This method decreases the health, upon reaching 0, the demon stops walking
     * and plays its death animation
     */
    // called by enemy classes to decrease player health
    public void takenDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            this.startWalking(0);
            demonAnimation.allowRequestDeathUpdate();
        }
    }

    /**
     * Passes the arraylist containing run animation to animate character to be shown on screen
     */
    public void UpdateImage_Run(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, runAnimation, runAnimationSize, 1, 60, 15, 2, false);
    }

    /**
     * Method will display any array passed into it as an animation. It also handles
     * any interaction between player and demon depending on the animation -> if demon
     * completes its attack animation and player is within attack range it will reduce health of player,
     * if demon has finished its death animation it will delete this object and let the animation and spawner know.
     *
     * @param timeElapsed - used to control the frame rate of animations
     * @param flip - used to flip animations, so demon can face both left and right
     * @param allAnimation - generic arraylist so all animations can be handled by one method
     * @param allAnimationSize - used to point to the end of the animation arraylist passed in
     * @param action - used to define what animation has been passed in
     * @param scale - adjusts size of animation displayed
     * @param alignY - adjusts alignment in Y axis of animation displayed
     * @param fps - works with timeElapsed to adjust frame rate to required setting
     * @param deny - stops updating the animation if a higher precedence action is required
     */
    public void animateCharacter(int timeElapsed, boolean flip, ArrayList<String> allAnimation, int allAnimationSize, int action, int scale, int alignY, int fps, boolean deny) {
        if (timeElapsed % fps == 0 && !deny) {
            if (allAnimationPointer < allAnimationSize) {
                if(allAnimationPointer == 10 && action == 5){
                    level.getSamurai().takenDamage(10);
                    level.getSamurai().applyForce(new Vec2(0, 30000));
                    level.getSamurai().setGravityScale(2);
                }
                removeAllImages();
                if (!flip) {
                    new AttachedImage(this, new BodyImage(allAnimation.get(allAnimationPointer)), scale, 0, new Vec2(0, alignY));
                } else {
                    AttachedImage image = new AttachedImage(this, new BodyImage(allAnimation.get(allAnimationPointer)), scale, 0, new Vec2(0, alignY));
                    image.flipHorizontal();
                }
                allAnimationPointer++;
            } else {
                allAnimationPointer = 0;
                switch (action) {
                    case 1:
                        demonAnimation.denyRequestRunUpdate();
                        break;
                    case 2:
                        this.destroy();
                        demonAnimation.denyRequestDeathUpdate();
                        break;
                    case 3:
                        demonAnimation.slowed = true;
                        demonAnimation.setSlowedReps(0);
                        demonAnimation.denyRequestJumpUpdate();
                        break;
                    case 4:
                        if (!deny) {
                            if (demonAnimation.slowed) {
                                demonAnimation.incrementSlowedReps();
                            }
                            demonAnimation.denyRequestWalkUpdate();
                        } else {
                            stopWalking = false;
                            demonAnimation.denyRequestWalkUpdate();
                        }
                        break;

                    case 5:
                        level.getSamurai().setGravityScale(1);
                        demonAnimation.denyRequestSliceUpdate();
                        break;
                }
            }
        }
    }
}
