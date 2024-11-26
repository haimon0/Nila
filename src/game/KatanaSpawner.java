package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class KatanaSpawner implements ActionListener {
    private Samurai samurai;
    private GameLevel level;
    private int numberOfKatanas = 0, totalKatanas = 5, timeElapsed = 0;

    public void setGreenLight(boolean greenLight) {
        this.greenLight = greenLight;
    }

    private boolean greenLight = true;

    boolean lastSpawnedRight = false;

    KatanaSpawner(GameLevel l, Samurai s){
        level = l;
        samurai = s;
    }

    public void reduceNumberOfKatanas() {
        numberOfKatanas --;
    }

    public int getNumberOfKatanas() {
        return numberOfKatanas;
    }

    public void setNumberOfKatanas(int numberOfKatanas) {
        this.numberOfKatanas = numberOfKatanas;
    }

    public void reduceTotalKatanas() {
        totalKatanas --;
    }

    public void increaseTotalKatanas() {
        totalKatanas ++;
    }

    public void setTotalKatanas(int totalKatanas) {
        this.totalKatanas = totalKatanas;
    }

    public int getTotalKatanas() {
        return totalKatanas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(greenLight){
            if(timeElapsed > 1){
                if(numberOfKatanas<2 & totalKatanas != 0){
                    Katana katana = new Katana(level, this);
                    if(!lastSpawnedRight){
                        lastSpawnedRight = true;
                        katana.setPosition(new Vec2(38, -12));
                    }else{
                        lastSpawnedRight = false;
                        katana.setPosition(new Vec2(-38, -12));
                    }
                    numberOfKatanas++;
                }



                if(numberOfKatanas == 0){
                    samurai.getSamuraiAnimation().setAllEnemysDefeated(true);
                    try {
                        GameSaverLoader.save(level, "autoSave.hs");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Level4.katanaTimer.stop();
                }
            }
        }
        timeElapsed++;
    }
}
