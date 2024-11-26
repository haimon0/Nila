package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArrowAnimation implements ActionListener {
    private int timeElapsed = 0;
    private Arrow arrow;

    public ArrowAnimation(Arrow arrow) {
        this.arrow = arrow;
    }

    public void setRequestFlyingUpdate(boolean requestFlyingUpdate) {
        this.requestFlyingUpdate = requestFlyingUpdate;
    }

    private boolean requestFlyingUpdate = true;

    @Override
    public void actionPerformed(ActionEvent e) {
        timeElapsed++;
        if(requestFlyingUpdate){
            arrow.UpdateImage_Flying(timeElapsed);
        }
    }
}
