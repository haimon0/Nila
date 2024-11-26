package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TrollSpawner implements ActionListener {
    private Samurai samurai;
    private GameLevel level;
    private int numberOfTrolls = 0, totalTrolls = 4, timeElapsed = 0;

    public void setGreenLight(boolean greenLight) {
        this.greenLight = greenLight;
    }

    private boolean greenLight = true;

    boolean lastSpawnedRight = false;

    TrollSpawner(GameLevel l, Samurai s){
        level = l;
        samurai = s;
    }

    public void reduceNumberOfTrolls() {
        numberOfTrolls --;
    }

    public int getNumberOfTrolls() {
        return numberOfTrolls;
    }

    public void setNumberOfTrolls(int numberOfTrolls) {
        this.numberOfTrolls = numberOfTrolls;
    }

    public void reduceTotalTrolls() {
        totalTrolls --;
    }

    public void increaseTotalTrolls() {
        totalTrolls ++;
    }

    public void setTotalTrolls(int totalTrolls) {
        this.totalTrolls = totalTrolls;
    }

    public int getTotalTrolls() {
        return totalTrolls;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(greenLight){
            if(timeElapsed > 1){
                if(numberOfTrolls<2 & totalTrolls != 0){
                    Troll troll = new Troll(level, this);
                    if(!lastSpawnedRight){
                        lastSpawnedRight = true;
                        troll.setPosition(new Vec2(38, -12));
                    }else{
                        lastSpawnedRight = false;
                        troll.setPosition(new Vec2(-38, -12));
                    }
                    numberOfTrolls++;
                }



                if(numberOfTrolls == 0){
                    samurai.getSamuraiAnimation().setAllEnemysDefeated(true);
                    try {
                        GameSaverLoader.save(level, "autoSave.hs");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if(level instanceof Level1){
                        ((Level1) level).getTrollTimer().stop();
                    }
                }
            }
        }
        timeElapsed++;
    }
}
