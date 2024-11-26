package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;

public abstract class GameLevel extends World {
    private final Samurai samurai = new Samurai(this); // make the samurai the player controls
    private Game game;
    public WorldBoundary getWorldBoundaryDown() {
        return worldBoundaryDown;
    }

    private WorldBoundary worldBoundaryDown, worldBoundaryRight, worldBoundaryLeft;

    public WorldBoundary getWorldBoundaryLeft() {
        return worldBoundaryLeft;
    }

    public void setWorldBoundaryLeft(WorldBoundary worldBoundaryLeft) {
        this.worldBoundaryLeft = worldBoundaryLeft;
    }

    public void setWorldBoundaryDown(WorldBoundary worldBoundaryDown) {
        this.worldBoundaryDown = worldBoundaryDown;
    }

    public WorldBoundary getWorldBoundaryRight() {
        return worldBoundaryRight;
    }

    public void setWorldBoundaryRight(WorldBoundary worldBoundaryRight) {
        this.worldBoundaryRight = worldBoundaryRight;
    }

    public GameLevel(Game game){
        this.game = game;
        /* Create world boundaries so objects can't leave screen */
        Shape worldBoundaryShapeVertical = new BoxShape(0.5f, 30);
        Shape worldBoundaryShapeHorizontal = new BoxShape(45f, 0.5f);
        // Shape worldBoundaryShapeHorizontal = new BoxShape(35f, 0.5f);
        // originally 35f for half width worldBoundaryShapeHorizontal

        worldBoundaryDown = new WorldBoundary(this, worldBoundaryShapeHorizontal);
        worldBoundaryDown.setPosition(new Vec2(0f, -15f));

        worldBoundaryRight = new WorldBoundary(this, worldBoundaryShapeVertical);
        worldBoundaryRight.setPosition(new Vec2(41.5f, 0)); // to fit window set x as 31.5f

        worldBoundaryLeft = new WorldBoundary(this, worldBoundaryShapeVertical);
        worldBoundaryLeft.setPosition(new Vec2(-41.5f, 0));
    }

    public Samurai getSamurai(){
        return samurai;
    }

    public Game getGame() {
        return game;
    }

    public abstract boolean isComplete();

    public abstract String getLevelName();

    public abstract void populate();

    public abstract void playMusic();

    public abstract void virtualDestroy();

    public abstract SoundClip getBackgroundMusic();
}