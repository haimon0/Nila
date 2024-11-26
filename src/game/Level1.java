package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Level1 extends GameLevel{
    private SoundClip backgroundMusic;
    private final String pathToBackground = "data/rainForest.gif", pathToForeground = "data/turtleRockForeground.png";
    private Timer trollTimer, foregroundTimer, birdTimer;
    private TrollSpawner trollSpawner;
    private Bird bird;
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
    public Timer getTrollTimer() {
        return trollTimer;
    }

    public Level1(Game game){
        super(game);
        this.game = game;
        worldBoundaryDown = super.getWorldBoundaryDown();
        worldBoundaryLeft = super.getWorldBoundaryLeft();
        worldBoundaryRight = super.getWorldBoundaryRight();
        GameView.setBackground(pathToBackground);
        GameView.setForeground(pathToForeground);
        getSamurai().setPosition(new Vec2(0, -14));
        getSamurai().getSamuraiAnimation().setLevel(this);

        // create and start a timer that loops every 1000ms, used to update the foreground
        foregroundTimer = new Timer(1000, new ForeGround());
        foregroundTimer.start();

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

    public TrollSpawner getTrollSpawner() {
        return trollSpawner;
    }

    @Override
    public void populate() {
        // make a bird that flies above
        bird = new Bird(this);
        bird.setPosition(new Vec2(20,11));

        // create and start a timer that loops every 50ms, used to update bird animation

        birdTimer = new Timer(50, new BirdAnimation(bird, true));
        birdTimer.start();

        trollSpawner = new TrollSpawner(this, getSamurai());

        trollTimer = new Timer(50, trollSpawner);
        trollTimer.start();
    }


    public void addBird(float x, float y){
        // make a bird that flies above
        bird = new Bird(this);
        bird.setPosition(new Vec2(x,y));

        birdTimer = new Timer(50, new BirdAnimation(bird, true));
        birdTimer.start();
    }

    public void addTroll(float x, float y){
        Troll troll = new Troll(this, trollSpawner);
        troll.setPosition(new Vec2(x, y));
        trollSpawner.increaseTotalTrolls();
    }
    public void addTrollSpawner(int totalTrolls, int numberOfTrolls){
        trollSpawner = new TrollSpawner(this, getSamurai());
        trollSpawner.setTotalTrolls(totalTrolls);
        trollSpawner.setNumberOfTrolls(numberOfTrolls);
        trollSpawner.setGreenLight(false);
        trollTimer = new Timer(50, trollSpawner);
        trollTimer.start();
    }

    public void playMusic(){
        try {
            backgroundMusic = new SoundClip("data/Music/gta4LS.wav");   // Open an audio input stream
            backgroundMusic.loop();
            // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }

    public void virtualDestroy(){
        trollTimer.stop();
        foregroundTimer.stop();
        birdTimer.stop();
        for (StaticBody s: this.getStaticBodies()) {
            s.destroy();
        }
        for (DynamicBody d: this.getDynamicBodies()) {
            if(d instanceof Troll){
                ((Troll) d).getTrollTimer().stop();
            } else if (d instanceof Samurai) {
                ((Samurai) d).getSamuraiTimer().stop();
            }
            d.destroy();
        }
    }
}
