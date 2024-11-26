package game;

import city.cs.engine.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerController implements KeyListener {

    Samurai samurai;
    GameLevel level;

    GameView view;


    boolean antiSpam;

    static boolean lock = true;

    PlayerController(Samurai samurai, GameLevel level, GameView view){
        this.view = view;
        this.samurai = samurai;
        this.level = level;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void setLevel(GameLevel level) {
        this.level = level;
    }

    /* depending on the key pressed the player can move left, right, jump and attack.
    *  lock is used to ban keyboard inputs when player is in bootup screen or is dead.
    *  antiSpam is used to ban the spamming of the P key which can cause the player to fly if spammed
    * */
    @Override
    public void keyPressed(KeyEvent e) {
        if(!lock){
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_A){
                samurai.startWalking(-20);
                samurai.getSamuraiAnimation().allowRequestWalkUpdate(true);
                samurai.getSamuraiAnimation().keyReleasedAD = false;
                samurai.getSamuraiAnimation().noKeyPressed = false;
                if(antiSpam){
                    antiSpam = false;
                }
            }
            else if(code == KeyEvent.VK_D){
                samurai.startWalking(20);
                samurai.getSamuraiAnimation().allowRequestWalkUpdate(false);
                samurai.getSamuraiAnimation().keyReleasedAD = false;
                samurai.getSamuraiAnimation().noKeyPressed = false;
                if(antiSpam){
                    antiSpam = false;
                }
            }
            else if (code == KeyEvent.VK_W) {
                samurai.jump(10);
                samurai.getSamuraiAnimation().allowRequestJumpUpdate();
                if(antiSpam){
                    antiSpam = false;
                }
            }
            else if (code == KeyEvent.VK_1) {
                samurai.setArrowType(0);
                if(antiSpam){
                    antiSpam = false;
                }
            }
            else if (code == KeyEvent.VK_2) {
                samurai.setArrowType(1);
                if(antiSpam){
                    antiSpam = false;
                }
            }
        }
    }

    /* depending on key released the character will stop moving/attacking */
    @Override
    public void keyReleased(KeyEvent e) {
        if(!lock){
            int code = e.getKeyCode();
            if(code == KeyEvent.VK_A | code == KeyEvent.VK_D){
                samurai.startWalking(0);
                samurai.getSamuraiAnimation().keyReleasedAD = true;
            }
        }
    }

    public static void setLock(boolean lock) {
        PlayerController.lock = lock;
    }

    public void updateSamurai(Samurai samurai){
        this.samurai = samurai;
    }

}
