package game;

import city.cs.engine.DynamicBody;
import city.cs.engine.SoundClip;
import city.cs.engine.StaticBody;

public class BlankLevel extends GameLevel{
    public BlankLevel(Game game) {
        super(game);
        super.getSamurai().getSamuraiTimer().stop();
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public String getLevelName() {
        return null;
    }

    @Override
    public void populate() {

    }

    @Override
    public void playMusic() {

    }

    @Override
    public void virtualDestroy() {
        for (StaticBody s: this.getStaticBodies()) {
            s.destroy();
        }
        for (DynamicBody d: this.getDynamicBodies()) {;
            if (d instanceof Samurai) {
                ((Samurai) d).getSamuraiTimer().stop();
            }
            d.destroy();
        }
    }

    @Override
    public SoundClip getBackgroundMusic() {
        return null;
    }
}
