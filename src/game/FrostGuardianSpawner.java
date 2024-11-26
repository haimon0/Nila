package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FrostGuardianSpawner implements ActionListener {
    private Samurai samurai;
    private GameLevel level;
    private int numberOfFrostGuardians = 0, totalFrostGuardians = 1, timeElapsed = 0;

    private boolean greenLight = true;


    FrostGuardianSpawner(GameLevel l, Samurai s){
        level = l;
        samurai = s;
    }

    public void reduceNumberOfFrostGuardians() {
        numberOfFrostGuardians --;
    }

    public void reduceTotalFrostGuardian(){
        totalFrostGuardians--;
    }

    public void increaseTotalFrostGuardians() {
        totalFrostGuardians ++;
    }

    public int getTotalFrostGuardians() {
        return totalFrostGuardians;
    }

    public void setTotalFrostGuardians(int totalFrostGuardians) {
        this.totalFrostGuardians = totalFrostGuardians;
    }

    public int getNumberOfFrostGuardians() {
        return numberOfFrostGuardians;
    }

    public void setNumberOfFrostGuardians(int numberOfFrostGuardians) {
        this.numberOfFrostGuardians = numberOfFrostGuardians;
    }

    public void setGreenLight(boolean greenLight) {
        this.greenLight = greenLight;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(greenLight){
            if(timeElapsed > 1){
                if(totalFrostGuardians != 0 && numberOfFrostGuardians == 0){
                    FrostGuardian frostGuardian = new FrostGuardian(level, this);
                    frostGuardian.setPosition(new Vec2(30, -12));
                    numberOfFrostGuardians++;
                }
                if(numberOfFrostGuardians == 0){
                    samurai.getSamuraiAnimation().setAllEnemysDefeated(true);
                    try {
                        GameSaverLoader.save(level, "autoSave.hs");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Level3.frostGuardianTimer.stop();
                }
            }
        }
        timeElapsed++;
    }
}
