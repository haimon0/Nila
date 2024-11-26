package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class KnightSpawner implements ActionListener {
    private Samurai samurai;
    private GameLevel level;
    private int numberOfKnights = 0, totalKnights = 5, timeElapsed = 0;

    public void setGreenLight(boolean greenLight) {
        this.greenLight = greenLight;
    }

    private boolean greenLight = true;

    boolean lastSpawnedRight = false;

    KnightSpawner(GameLevel l, Samurai s){
        level = l;
        samurai = s;
    }

    public void reduceNumberOfKnights() {
        numberOfKnights --;
    }

    public int getNumberOfKnights() {
        return numberOfKnights;
    }

    public void setNumberOfKnights(int numberOfKnights) {
        this.numberOfKnights = numberOfKnights;
    }

    public void reduceTotalKnights() {
        totalKnights --;
    }

    public void increaseTotalKnights() {
        totalKnights ++;
    }

    public void setTotalKnights(int totalKnights) {
        this.totalKnights = totalKnights;
    }

    public int getTotalKnights() {
        return totalKnights;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(greenLight){
            if(timeElapsed > 1){
                if(numberOfKnights<2 & totalKnights != 0){
                    Knight knight = new Knight(level, this);
                    if(!lastSpawnedRight){
                        lastSpawnedRight = true;
                        knight.setPosition(new Vec2(38, -12));
                    }else{
                        lastSpawnedRight = false;
                        knight.setPosition(new Vec2(-38, -12));
                    }
                    numberOfKnights++;
                }



                if(numberOfKnights == 0){
                    samurai.getSamuraiAnimation().setAllEnemysDefeated(true);
                    try {
                        GameSaverLoader.save(level, "autoSave.hs");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Level5.knightTimer.stop();
                }
            }
        }
        timeElapsed++;
    }
}
