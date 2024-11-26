package game;

import org.jbox2d.common.Vec2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForeGround implements ActionListener {
    private int timeElapsed;

    /* called every 1 second as specified by foreGroundTimer,
       updates screen on bootup to show title card and controls,
       locks player controls until game screen is shown
    * */
    @Override
    public void actionPerformed(ActionEvent e) {
        timeElapsed++;
        // change back to  timeElapsed == 10 after testing
        //Level1.arrow.startWalking(15);
        if(timeElapsed == 1){
            GameView.setBootupComplete(true);
            PlayerController.setLock(false);
            RaptorAnimation.allowRequestRoarUpdate();
        }
    }
}
