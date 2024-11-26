package game;

import javax.swing.*;
import java.awt.*;

public class CustomJpanel extends JPanel {
    final Image bgImage;

    CustomJpanel(String pathToBackground){
        bgImage = new ImageIcon(pathToBackground).getImage();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);
    }
}
