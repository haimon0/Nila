package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Level2 extends GameLevel{
    private SoundClip backgroundMusic;
    private final String pathToBackground = "data/volcano.gif";

    public static Timer demonTimer;

    private DemonSpawner demonSpawner;

    private Game game;

    public SoundClip getBackgroundMusic() {
        return backgroundMusic;
    }

    public Level2(Game game) {
        super(game);
        this.game = game;
        GameView.setBackground(pathToBackground);
        GameView.setForeground("");
        getSamurai().getSamuraiAnimation().setLevel(this);

        // debugging view
        //JFrame debugView = new DebugViewer(this, 1280, 720);
    }

    public DemonSpawner getDemonSpawner() {
        return demonSpawner;
    }

    @Override
    public boolean isComplete() {
        if(backgroundMusic != null){
            backgroundMusic.stop();
        }
        game.goToNextLevel(getSamurai().getHealth());
        return true;
    }

    @Override
    public String getLevelName() {
        return getClass().getSimpleName();
    }

    @Override
    public void populate() {
        getSamurai().setPosition(new Vec2(-31.5f, -11));
        demonSpawner = new DemonSpawner(this, getSamurai());
        demonTimer = new Timer(50, demonSpawner);
        demonTimer.start();
        //JFrame debugView = new DebugViewer(this, 1280, 720);
    }

    public void addDemon(float x, float y){
        Demon demon = new Demon(this, demonSpawner);
        demon.setPosition(new Vec2(x, y));
        demonSpawner.increaseTotalDemons();

    }

    public void addDemonSpawner(int totalDemons, int numberOfDemons){
        demonSpawner = new DemonSpawner(this, getSamurai());
        demonSpawner.setNumberOfDemons(numberOfDemons);
        demonSpawner.setTotalDemons(totalDemons);
        demonSpawner.setGreenLight(false);
        demonTimer = new Timer(50, demonSpawner);
        demonTimer.start();
    }

    public void playMusic(){
        try {
            //game.getBackgroundMusic().stop();
            backgroundMusic = new SoundClip("data/Music/KillerLooks.wav");   // Open an audio input stream
            backgroundMusic.loop();
            backgroundMusic.setVolume(0.4);
            // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }

    @Override
    public void virtualDestroy() {
        demonTimer.stop();
        for (StaticBody s: this.getStaticBodies()) {
            s.destroy();
        }
        for (DynamicBody d: this.getDynamicBodies()) {;
            if (d instanceof Samurai) {
                ((Samurai) d).getSamuraiTimer().stop();
            } else if (d instanceof Demon) {
                ((Demon) d).demonTimer.stop();
            }
            d.destroy();
        }
    }
}
