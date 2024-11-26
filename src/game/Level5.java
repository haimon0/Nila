package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Level5 extends GameLevel{
    private SoundClip backgroundMusic;

    private final String pathToBackground = "data/lake.gif", pathToForeground = "turtleRockForeground.png";
    public static Timer knightTimer;

    private KnightSpawner knightSpawner;

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

    public Level5(Game game){
        super(game);
        this.game = game;
        worldBoundaryDown = super.getWorldBoundaryDown();
        worldBoundaryDown.setPosition(new Vec2(0, -13.5f));
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

    public KnightSpawner getKnightSpawner() {
        return knightSpawner;
    }

    @Override
    public void populate() {
        getSamurai().setPosition(new Vec2(-31.5f, -11f));

        knightSpawner = new KnightSpawner(this, getSamurai());

        knightTimer = new Timer(50, knightSpawner);
        knightTimer.start();
    }


    public void addKnight(float x, float y){
        Knight knight = new Knight(this, knightSpawner);
        knight.setPosition(new Vec2(x, y));
        knightSpawner.increaseTotalKnights();
    }
    public void addKnightSpawner(int totalKnights, int numberOfKnights){
        knightSpawner = new KnightSpawner(this, getSamurai());
        knightSpawner.setTotalKnights(totalKnights);
        knightSpawner.setNumberOfKnights(numberOfKnights);
        knightSpawner.setGreenLight(false);
        knightTimer = new Timer(50, knightSpawner);
        knightTimer.start();
    }

    public void playMusic(){
        try {
            backgroundMusic = new SoundClip("data/Music/gravityWall.wav");   // Open an audio input stream
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
        knightTimer.stop();
        for (StaticBody s: this.getStaticBodies()) {
            s.destroy();
        }
        for (DynamicBody d: this.getDynamicBodies()) {;
            if (d instanceof Samurai) {
                ((Samurai) d).getSamuraiTimer().stop();
            }else if (d instanceof Knight) {
                ((Knight) d).knightTimer.stop();
            }
            d.destroy();
        }
    }
}
