package game;

import city.cs.engine.*;

public class WorldBoundary extends StaticBody {
    // create invisible boundaries so objects don't leave screen
    public WorldBoundary(World w, Shape s) {
        super(w, s);
        addImage(new BodyImage("data/Empty.png"));
    }
}
