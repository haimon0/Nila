package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerMouseListener implements MouseListener {
    Samurai samurai;
    GameLevel level;

    GameView view;


    boolean antiSpam = false, flip;
    static boolean lock = false;

    public void setLevel(GameLevel level) {
        this.level = level;
    }

    PlayerMouseListener(Samurai samurai, GameLevel level, GameView view){
        this.samurai = samurai;
        this.level = level;
        this.view = view;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!lock){
            if(!antiSpam){
                antiSpam = true;
                    /* removes raptor collision listener temporarily to prevent
                       player from taking damage when attacking
                     */
                samurai.getSamuraiAnimation().keyReleasedP = true;
            }
        }
    }

    public static void setLock(boolean l) {
        lock = l;
    }

    public void updateSamurai(Samurai samurai) {
        this.samurai = samurai;
    }

    public void setView(GameView view) {
        this.view = view;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!lock){
            samurai.getSamuraiAnimation().keyReleasedP = true;
            if(view.viewToWorld(e.getPoint()).x - samurai.getPosition().x > 0){
                flip = false;
            }else{
                flip = true;
            }
            samurai.getSamuraiAnimation().allowRequestSliceUpdate(view.viewToWorld(e.getPoint()), flip);
            antiSpam = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
