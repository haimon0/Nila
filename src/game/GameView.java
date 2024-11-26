package game;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class GameView extends UserView {
    private static Image background;
    private static Image foreground = new ImageIcon("turtleRockForeground.png").getImage();;

    private final Image deathScreen = new ImageIcon("data/deathScreen.png").getImage();
    private static final Image healthBar0 = new ImageIcon("data/HealthBar/HealthBar(0).png").getImage();
    private static boolean stopDrawing = false, bootupComplete = false, noForeground = false;
    private int width = 300;

    private Samurai samurai;

    private GameLevel gameLevel;

    public GameView(GameLevel gameLevel, int width, int height) {
        super(gameLevel, width, height);
        this.gameLevel = gameLevel;
        samurai = gameLevel.getSamurai();
    }

    // add background to game and set size to 1280x720
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, 1280, 720, this);
    }

    public static void setBackground(String pathToBackground) {
        background = new ImageIcon(pathToBackground).getImage();
    }

    public void setSamurai(Samurai samurai) {
        this.samurai = samurai;
    }

    /* Draw bootup screen, then draw health bar and keep updating,
           if player dies update with death screen. Health bar
           decreases by reducing the width of image */
    @Override
    protected void paintForeground(Graphics2D g) {
        if(!noForeground){
            g.drawImage(foreground, 0, 0, 1280, 720, this);
        }

        width = gameLevel.getSamurai().getHealthSize();
        if(width >= 0){
            g.drawImage(healthBar0, 32, 32, width, 20, this);
        }
    }

    // update bootup complete is bootup screen disappears
    public static void setBootupComplete(boolean bootupComplete) {
        GameView.bootupComplete = bootupComplete;
    }

    public static void setForeground(String pathToForeground) {
        if(pathToForeground == ""){
            noForeground = true;
        }else{
            foreground = new ImageIcon(pathToForeground).getImage();
            noForeground = false;
        }
    }

    public void setGameLevel(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }
}
