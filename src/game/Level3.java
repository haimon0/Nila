package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Level3 extends GameLevel{
    private final String pathToBackground = "data/snowy2.jpg";
    private SoundClip backgroundMusic;
    public static Timer frostGuardianTimer;

    private FrostGuardianSpawner frostGuardianSpawner;

    private Game game;

    public SoundClip getBackgroundMusic() {
        return backgroundMusic;
    }

    public Level3(Game game) {
        super(game);
        this.game = game;
        GameView.setBackground(pathToBackground);
        GameView.setForeground("");
        getSamurai().getSamuraiAnimation().setLevel(this);
        //JFrame debugView = new DebugViewer(this, 1280, 720);
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
        getSamurai().setPosition(new Vec2(-31.5f, -14));
        frostGuardianSpawner = new FrostGuardianSpawner(this, getSamurai());
        frostGuardianTimer = new Timer(50, frostGuardianSpawner);
        frostGuardianTimer.start();
    }

    public void addFrostGuardian(float x, float y){
        FrostGuardian frostGuardian = new FrostGuardian(this, frostGuardianSpawner);
        frostGuardian.setPosition(new Vec2(x, y));
        frostGuardianSpawner.increaseTotalFrostGuardians();

    }

    public FrostGuardianSpawner getFrostGuardianSpawner() {
        return frostGuardianSpawner;
    }

    public void addFrostGuardianSpawner(int totalFrostGuardians, int numberOfFrostGuardians){
        frostGuardianSpawner = new FrostGuardianSpawner(this, getSamurai());
        frostGuardianSpawner.setNumberOfFrostGuardians(numberOfFrostGuardians);
        frostGuardianSpawner.setTotalFrostGuardians(totalFrostGuardians);
        frostGuardianSpawner.setGreenLight(false);
        frostGuardianTimer = new Timer(50, frostGuardianSpawner);
        frostGuardianTimer.start();
    }

    public void playMusic(){
        try {
            //game.getBackgroundMusic().stop();
            backgroundMusic = new SoundClip("data/Music/neurotica.wav");   // Open an audio input stream
            backgroundMusic.loop();
            backgroundMusic.setVolume(0.2);
            // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }

    @Override
    public void virtualDestroy() {
        frostGuardianTimer.stop();
        for (StaticBody s: this.getStaticBodies()) {
            s.destroy();
        }
        for (DynamicBody d: this.getDynamicBodies()) {;
            if (d instanceof Samurai) {
                ((Samurai) d).getSamuraiTimer().stop();
            }else if (d instanceof FrostGuardian) {
                ((FrostGuardian) d).frostGuardianTimer.stop();
            }
            d.destroy();
        }
    }
}
