package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Used to spawn in demons. Sets spawn location of demon, and allows
 * player to move on to the next level after defeating set number of demons
 */

public class DemonSpawner implements ActionListener {
    private Samurai samurai;
    private GameLevel level;

    private boolean greenLight = true;
    private int numberOfDemons = 0;
    private int totalDemons = 1;

    private int timeElapsed = 0;


    /**
     * has access to level, so it can stop running after a demon has been defeated
     * lets samurai animation know that a demon has been defeated so the player can
     * stop taking damage and move on to next level
     * @param l - passes in current level
     * @param s - passes in player character
     */
    DemonSpawner(GameLevel l, Samurai s){
        level = l;
        samurai = s;
    }

    /**
     *  increases total number of demons to be spawned for this level
     *  (used to load in state and not spawn excess demons)
     */
    public void increaseTotalDemons(){
        totalDemons++;
    }

    /**
     *  reduce number of demons that are currently on screen
     */
    public void reduceNumberOfDemons() {
        numberOfDemons --;
    }

    public int getTotalDemons() {
        return totalDemons;
    }

    /**
     *  reduces total number of demons to be spawned for this level
     *  (used to load in state and not spawn excess demons)
     */
    public void reduceTotalDemons(){
        totalDemons--;
    }

    /**
     * sets total demons that still need to be spawned after load state
     * @param totalDemons - total demons amount of demons that still need to be spawned in
     */
    public void setTotalDemons(int totalDemons) {
        this.totalDemons = totalDemons;
    }

    public int getNumberOfDemons() {
        return numberOfDemons;
    }

    /**
     * sets number of demons that don't need to be spawned after load state as they are on screen
     * @param numberOfDemons - demons that are already on screen after load state do not need to be repeat spawned
     */
    public void setNumberOfDemons(int numberOfDemons) {
        this.numberOfDemons = numberOfDemons;
    }

    /**
     *  used in load state, to stop spawning in demons until player is fully loaded in
     */
    public void setGreenLight(boolean greenLight) {
        this.greenLight = greenLight;
    }

    /**
     * Spawns in demons from opposite sides of the screen, alternating in spawn points,
     * so two are not in line of each other. Once no more demons need to be spawned in,
     * player is allowed to move to next level.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(greenLight){
            if(timeElapsed > 1){
                if(totalDemons != 0 && numberOfDemons==0){
                    Demon demon = new Demon(level, this);
                    demon.setPosition(new Vec2(30, -12));
                    numberOfDemons++;
                }
                if(numberOfDemons == 0){
                    samurai.getSamuraiAnimation().setAllEnemysDefeated(true);
                    try {
                        GameSaverLoader.save(level, "autoSave.hs");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Level2.demonTimer.stop();
                }
            }
        }
        timeElapsed++;
    }
}
