package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ButtonManager implements ActionListener {
    private JButton button1, button2, button3, button10, button12, button13, button14, button15, button11;
    private JSlider slider;

    private int x = 0, y = 0, z = 30, timeElapsed = 0;

    public ButtonManager(JButton jb1, JButton jb2, JButton jb3, JButton jb10, JButton jb12, JButton settings, JButton jb14, JButton jb13, JSlider s, JButton jb11){
        button1 = jb1;
        button2 = jb2;
        button3 = jb3;
        button10 = jb10;
        button11 = jb11;
        button12 = jb12;
        button13 = jb13;
        button14 = jb14;
        button15 = settings;
        slider = s;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        button1.setBounds(540, 250, 200, 50);
        timeElapsed++;
        z += 5;

        if (z >= 255) {
            z = 50;
        }


        Color color = new Color(0, 0, z);
        Color randomColor = new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        button1.setBorder(BorderFactory.createLineBorder(color, 3));
        if(timeElapsed % 100 == 0){
            button2.setBackground(randomColor);

            button10.setBackground(randomColor);

            button15.setBackground(randomColor);

            button3.setForeground(randomColor);
            button3.setBorder(BorderFactory.createLineBorder(randomColor, 3));

            button11.setForeground(randomColor);
            button11.setBorder(BorderFactory.createLineBorder(randomColor, 3));

            button12.setForeground(randomColor);
            button12.setBorder(BorderFactory.createLineBorder(randomColor, 3));

            button13.setForeground(randomColor);
            button13.setBorder(BorderFactory.createLineBorder(randomColor, 3));

            button14.setForeground(randomColor);
            button14.setBorder(BorderFactory.createLineBorder(randomColor, 3));

            slider.setForeground(randomColor);
        }
    }
}
