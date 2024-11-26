package game;

import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;
import java.util.ArrayList;
import java.util.Arrays;

public class MoonArrow extends Arrow{
    private ArrowCollisionListener arrowCollisionListener;

    private static final Shape boxShape = new BoxShape(2, 1f);

    private final Vec2 target, position;
    public MoonArrow(World w, Vec2 target, Vec2 position) {
        super(w, target, position, new ArrayList<>(Arrays.asList("data/Arrows/moonArrow/pulse1.png", "data/Arrows/moonArrow/pulse2.png","data/Arrows/moonArrow/pulse3.png","data/Arrows/moonArrow/pulse4.png")), boxShape);
        this.target = target;
        this.position = position;
        launchArrow();
    }

    public void launchArrow(){
        arrowCollisionListener = new ArrowCollisionListener();
        this.addCollisionListener(arrowCollisionListener);
        float angleX = (float) Math.abs(Math.toDegrees(Math.atan((target.y - this.position.y) / (target.x - this.position.x))));
        int xForce = 7500;
        if(angleX <= 5){
            xForce*=0.90;
        } else if (angleX <=10) {
            xForce*=0.80;
        }
        else if (angleX <=20) {
            xForce*=0.70;
        }else if (angleX <=30) {
            xForce*=0.60;
        }else if (angleX <=40) {
            xForce*=0.50;
        }else if (angleX <=50) {
            xForce*=0.40;
        }else if (angleX <=60) {
            xForce*=0.30;
        }else if (angleX <=70) {
            xForce*=0.20;
        }else if (angleX <=80) {
            xForce*=0.10;
        }else if (angleX <=90) {
            xForce*=0.05;
        }
        int yForce = 7500-xForce;
        float distanceX = target.x- this.position.x;
        float distanceY = target.y- this.position.y;
        if(distanceX<0){
            xForce*=-1;
        }

        if(distanceY<0){
            yForce*=-1;
        }
        this.applyForce(new Vec2(xForce, yForce));
        this.setGravityScale(0);
    }
}
