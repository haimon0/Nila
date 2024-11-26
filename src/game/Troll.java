package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.ArrayList;



public class Troll extends Walker {
    private ArrayList<String> idleAnimation, sliceAnimation, walkAnimation, jumpAnimation, deathAnimation, runAnimation;
    private int idleAnimationSize, sliceAnimationSize, walkAnimationSize, jumpAnimationSize, deathAnimationSize, runAnimationSize, allAnimationSize, idleAnimationPointer = 0, sliceAnimationPointer = 0, walkAnimationPointer = 0, jumpAnimationPointer = 0, deathAnimationPointer = 0, runAnimationPointer=0, allAnimationPointer=0;
    private static final Shape trollShape = new BoxShape(2, 4);
    private int health = 100;

    public boolean stopWalking = false;

    public Timer trollTimer;

    public TrollAnimation trollAnimation;

    private GameLevel level;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Troll(GameLevel level, TrollSpawner trollSpawner) {
        super(level, trollShape);

        this.level = level;
        trollSpawner.reduceTotalTrolls();
        trollAnimation = new TrollAnimation(this, true, trollSpawner, level);
        trollTimer = new javax.swing.Timer(50, trollAnimation);
        trollTimer.start();

        // creates new array lists for different animations and adds paths to images

        idleAnimation = new ArrayList<>();

        idleAnimation.add("data/Troll/TrollIdle/Idle_000.png");
        idleAnimation.add("data/Troll/TrollIdle/Idle_001.png");
        idleAnimation.add("data/Troll/TrollIdle/Idle_002.png");
        idleAnimation.add("data/Troll/TrollIdle/Idle_003.png");
        idleAnimation.add("data/Troll/TrollIdle/Idle_004.png");
        idleAnimation.add("data/Troll/TrollIdle/Idle_005.png");
        idleAnimation.add("data/Troll/TrollIdle/Idle_006.png");
        idleAnimation.add("data/Troll/TrollIdle/Idle_007.png");
        idleAnimation.add("data/Troll/TrollIdle/Idle_008.png");
        idleAnimation.add("data/Troll/TrollIdle/Idle_009.png");

        idleAnimationSize = idleAnimation.size();

        sliceAnimation = new ArrayList<>();

        sliceAnimation.add("data/Troll/TrollAttack/Attack_000.png");
        sliceAnimation.add("data/Troll/TrollAttack/Attack_001.png");
        sliceAnimation.add("data/Troll/TrollAttack/Attack_002.png");
        sliceAnimation.add("data/Troll/TrollAttack/Attack_003.png");
        sliceAnimation.add("data/Troll/TrollAttack/Attack_004.png");
        sliceAnimation.add("data/Troll/TrollAttack/Attack_005.png");
        sliceAnimation.add("data/Troll/TrollAttack/Attack_006.png");
        sliceAnimation.add("data/Troll/TrollAttack/Attack_007.png");
        sliceAnimation.add("data/Troll/TrollAttack/Attack_008.png");
        sliceAnimation.add("data/Troll/TrollAttack/Attack_009.png");


        sliceAnimationSize = sliceAnimation.size();

        walkAnimation = new ArrayList<>();

        walkAnimation.add("data/Troll/TrollWalk/Walk_000.png");
        walkAnimation.add("data/Troll/TrollWalk/Walk_001.png");
        walkAnimation.add("data/Troll/TrollWalk/Walk_002.png");
        walkAnimation.add("data/Troll/TrollWalk/Walk_003.png");
        walkAnimation.add("data/Troll/TrollWalk/Walk_004.png");
        walkAnimation.add("data/Troll/TrollWalk/Walk_005.png");
        walkAnimation.add("data/Troll/TrollWalk/Walk_006.png");
        walkAnimation.add("data/Troll/TrollWalk/Walk_007.png");
        walkAnimation.add("data/Troll/TrollWalk/Walk_008.png");
        walkAnimation.add("data/Troll/TrollWalk/Walk_009.png");


        walkAnimationSize = walkAnimation.size();

        jumpAnimation = new ArrayList<>();

        jumpAnimation.add("data/Troll/TrollHurt/Hurt_000.png");
        jumpAnimation.add("data/Troll/TrollHurt/Hurt_001.png");
        jumpAnimation.add("data/Troll/TrollHurt/Hurt_002.png");
        jumpAnimation.add("data/Troll/TrollHurt/Hurt_003.png");
        jumpAnimation.add("data/Troll/TrollHurt/Hurt_004.png");
        jumpAnimation.add("data/Troll/TrollHurt/Hurt_005.png");
        jumpAnimation.add("data/Troll/TrollHurt/Hurt_006.png");
        jumpAnimation.add("data/Troll/TrollHurt/Hurt_007.png");
        jumpAnimation.add("data/Troll/TrollHurt/Hurt_008.png");
        jumpAnimation.add("data/Troll/TrollHurt/Hurt_009.png");



        jumpAnimationSize = jumpAnimation.size();

        deathAnimation = new ArrayList<>();

        deathAnimation.add("data/Troll/TrollDead/Dead_000.png");
        deathAnimation.add("data/Troll/TrollDead/Dead_001.png");
        deathAnimation.add("data/Troll/TrollDead/Dead_002.png");
        deathAnimation.add("data/Troll/TrollDead/Dead_003.png");
        deathAnimation.add("data/Troll/TrollDead/Dead_004.png");
        deathAnimation.add("data/Troll/TrollDead/Dead_005.png");
        deathAnimation.add("data/Troll/TrollDead/Dead_006.png");
        deathAnimation.add("data/Troll/TrollDead/Dead_007.png");
        deathAnimation.add("data/Troll/TrollDead/Dead_008.png");
        deathAnimation.add("data/Troll/TrollDead/Dead_009.png");


        deathAnimationSize = deathAnimation.size();

        runAnimation = new ArrayList<>();

        runAnimation.add("data/Troll/TrollRun/Run_000.png");
        runAnimation.add("data/Troll/TrollRun/Run_001.png");
        runAnimation.add("data/Troll/TrollRun/Run_002.png");
        runAnimation.add("data/Troll/TrollRun/Run_003.png");
        runAnimation.add("data/Troll/TrollRun/Run_004.png");
        runAnimation.add("data/Troll/TrollRun/Run_005.png");
        runAnimation.add("data/Troll/TrollRun/Run_006.png");
        runAnimation.add("data/Troll/TrollRun/Run_007.png");
        runAnimation.add("data/Troll/TrollRun/Run_008.png");
        runAnimation.add("data/Troll/TrollRun/Run_009.png");

        runAnimationSize = runAnimation.size();
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Idle(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, idleAnimation, idleAnimationSize, 6, 10, 0, 1, false);
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Slice(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, sliceAnimation, sliceAnimationSize, 5, 17, 3, 1, false);
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Walk(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, walkAnimation, walkAnimationSize, 4, 10, 0, 1, stopWalking);
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Jump(int timeElapsed, boolean flip) {
        if (!flip) {
            if (jumpAnimationPointer < jumpAnimationSize) {
                removeAllImages();
                new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 10, 0, new Vec2(0, 0));
                jumpAnimationPointer++;
            } else {
                jumpAnimationPointer = 0;
                trollAnimation.slowed = true;
                trollAnimation.setSlowedReps(0);
                trollAnimation.denyRequestJumpUpdate();
            }
        } else {
            if (jumpAnimationPointer < jumpAnimationSize) {
                removeAllImages();
                AttachedImage image = new AttachedImage(this, new BodyImage(jumpAnimation.get(jumpAnimationPointer)), 10, 0, new Vec2(0, 0));
                image.flipHorizontal();
                jumpAnimationPointer++;
            } else {
                jumpAnimationPointer = 0;
                trollAnimation.slowed = true;
                trollAnimation.setSlowedReps(0);
                trollAnimation.denyRequestJumpUpdate();
            }
        }
    }

    // iterates through array and flips image depending on characters orientation
    public void UpdateImage_Dying(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, deathAnimation, deathAnimationSize, 2, 12, -1, 1, false);
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
            trollAnimation.allowRequestDeathUpdate();
        }
    }

    public void UpdateImage_Run(int timeElapsed, boolean flip) {
        animateCharacter(timeElapsed, flip, runAnimation, runAnimationSize, 1, 17, 3, 1, false);
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
                            trollAnimation.denyRequestRunUpdate();
                            break;
                        case 2:
                            this.destroy();
                            trollAnimation.denyRequestDeathUpdate();
                            break;
                        case 3:
                            trollAnimation.slowed = true;
                            trollAnimation.setSlowedReps(0);
                            trollAnimation.denyRequestJumpUpdate();
                            break;
                        case 4:
                            if(!deny){
                                if(trollAnimation.slowed){
                                    trollAnimation.incrementSlowedReps();
                                }
                                trollAnimation.denyRequestWalkUpdate();
                            }
                            else{
                                stopWalking = false;
                                trollAnimation.denyRequestWalkUpdate();
                            }
                            break;

                        case 5:
                            level.getSamurai().takenDamage(10);
                            trollAnimation.denyRequestSliceUpdate();
                            break;
                    }
                }
            }
    }

    public Timer getTrollTimer() {
        return trollTimer;
    }
}
