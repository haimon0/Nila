package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Level4 extends GameLevel{
    private SoundClip backgroundMusic;

    private final String pathToBackground = "data/night.gif", pathToForeground = "data/leavesXWheel.png";
    public static Timer katanaTimer;

    private KatanaSpawner katanaSpawner;

    private Game game;

    private WorldBoundary worldBoundaryDown, worldBoundaryLeft, worldBoundaryRight;

    @Override
    public WorldBoundary getWorldBoundaryRight() {
        return worldBoundaryRight;
    }

    @Override
    public void setWorldBoundaryRight(WorldBoundary worldBoundaryRight) {
        this.worldBoundaryRight = worldBoundaryRight;
    }

    @Override
    public WorldBoundary getWorldBoundaryLeft() {
        return worldBoundaryLeft;
    }

    @Override
    public void setWorldBoundaryLeft(WorldBoundary worldBoundaryLeft) {
        this.worldBoundaryLeft = worldBoundaryLeft;
    }

    @Override
    public WorldBoundary getWorldBoundaryDown() {
        return worldBoundaryDown;
    }

    @Override
    public void setWorldBoundaryDown(WorldBoundary worldBoundaryDown) {
        this.worldBoundaryDown = worldBoundaryDown;
    }

    public SoundClip getBackgroundMusic() {
        return backgroundMusic;
    }

    public Level4(Game game){
        super(game);
        this.game = game;
        worldBoundaryDown = super.getWorldBoundaryDown();
        worldBoundaryDown.setPosition(new Vec2(0, -14));
        worldBoundaryLeft = super.getWorldBoundaryLeft();
        worldBoundaryRight = super.getWorldBoundaryRight();
        GameView.setBackground(pathToBackground);
        GameView.setForeground(pathToForeground);
        getSamurai().getSamuraiAnimation().setLevel(this);
        // debugging view
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

    public KatanaSpawner getKatanaSpawner() {
        return katanaSpawner;
    }

    @Override
    public void populate() {
        getSamurai().setPosition(new Vec2(-31.5f, -11));

        katanaSpawner = new KatanaSpawner(this, getSamurai());

        katanaTimer = new Timer(50, katanaSpawner);
        katanaTimer.start();
    }


    public void addKatana(float x, float y){
        Katana katana = new Katana(this, katanaSpawner);
        katana.setPosition(new Vec2(x, y));
        katanaSpawner.increaseTotalKatanas();
    }
    public void addKatanaSpawner(int totalKatanas, int numberOfKatanas){
        katanaSpawner = new KatanaSpawner(this, getSamurai());
        katanaSpawner.setTotalKatanas(totalKatanas);
        katanaSpawner.setNumberOfKatanas(numberOfKatanas);
        katanaSpawner.setGreenLight(false);
        katanaTimer = new Timer(50, katanaSpawner);
        katanaTimer.start();
    }

    public void playMusic(){
        try {
            backgroundMusic = new SoundClip("data/Music/reRe.wav");   // Open an audio input stream
            backgroundMusic.loop();
            // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }

    @Override
    public void virtualDestroy() {
        katanaTimer.stop();
        for (StaticBody s: this.getStaticBodies()) {
            s.destroy();
        }
        for (DynamicBody d: this.getDynamicBodies()) {;
            if (d instanceof Samurai) {
                ((Samurai) d).getSamuraiTimer().stop();
            }else if (d instanceof Katana) {
                ((Katana) d).katanaTimer.stop();
            }
            d.destroy();
        }
    }
}
