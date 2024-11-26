package game;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Game {
    private final JFrame frame = new JFrame("Nila");
    private SoundClip backgroundMusic;
    private JPanel panelContainer = new JPanel();
    private CustomJpanel panel1 = new CustomJpanel("data/menu.png");
    private JPanel panel2 = new JPanel();

    private CustomJpanel panel3 = new CustomJpanel("data/deathScreen.png");

    private CustomJpanel panel4 = new CustomJpanel("data/fini.png");

    private CustomJpanel panel5 = new CustomJpanel("data/controls.png");

    private CustomJpanel panel6 = new CustomJpanel("data/leaderBoard.png");

    private CustomJpanel panel7 = new CustomJpanel("data/volume.png");
    private JButton newGameButton = new JButton("NEW GAME");
    private JButton continueButton = new JButton("CONTINUE");
    private JButton loadSaveButton = new JButton("LOAD SAVE");

    private JButton button4 = new JButton("SAVE");

    private JButton button5 = new JButton("MENU");

    private Icon reloadIcon = new ImageIcon("data/reload.png");
    private JButton button6 = new JButton(reloadIcon);

    private JButton button7 = new JButton("MENU");
    private JButton button8 = new JButton(reloadIcon);

    private JButton button9 = new JButton("MENU");

    private JButton controlsButton = new JButton("CONTROLS");

    private JButton button11 = new JButton("BACK");

    private JButton leaderboardButton = new JButton("LEADERBOARD");

    private JButton button13 = new JButton("BACK");

    private Icon settingsIcon = new ImageIcon("data/settingsIcon.png");

    private JButton settingsButton = new JButton(settingsIcon);

    private JButton button14 = new JButton("BACK");

    private CardLayout cl = new CardLayout();

    private GameLevel level;
    private GameView view;
    private PlayerController controller;

    private PlayerMouseListener mouseListener;

    private Timer buttonTimer;

    private double volume = 0.5;

    private boolean paused = false;
    
    private JSlider volumeControl;


    /** Initialise a new Game. */
    public Game() {
        panelContainer.setLayout(cl);

        newGameButton.setBounds(540, 250, 200, 50);
        newGameButton.setOpaque(true);
        newGameButton.setContentAreaFilled(true);
        newGameButton.setBorderPainted(true);
        newGameButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 30));
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setBackground(Color.black);

        continueButton.setBounds(540, 310, 200, 50);
        continueButton.setOpaque(true);
        continueButton.setContentAreaFilled(true);
        continueButton.setBorderPainted(true);
        continueButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        continueButton.setFont(new Font("Arial", Font.PLAIN, 30));
        continueButton.setForeground(Color.BLACK);
        continueButton.setBackground(Color.pink);

        loadSaveButton.setBounds(540, 370, 200, 50);
        loadSaveButton.setOpaque(true);
        loadSaveButton.setContentAreaFilled(true);
        loadSaveButton.setBorderPainted(true);
        loadSaveButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        loadSaveButton.setFont(new Font("Arial", Font.PLAIN, 30));
        loadSaveButton.setForeground(Color.WHITE);
        loadSaveButton.setBackground(Color.black);

        controlsButton.setBounds(540, 430, 200, 50);
        controlsButton.setOpaque(true);
        controlsButton.setContentAreaFilled(true);
        controlsButton.setBorderPainted(true);
        controlsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        controlsButton.setFont(new Font("Arial", Font.PLAIN, 30));
        controlsButton.setForeground(Color.BLACK);
        controlsButton.setBackground(Color.pink);

        leaderboardButton.setBounds(1150, 10, 100, 50);
        leaderboardButton.setOpaque(true);
        leaderboardButton.setContentAreaFilled(true);
        leaderboardButton.setBorderPainted(true);
        leaderboardButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        leaderboardButton.setFont(new Font("Arial", Font.PLAIN, 10));
        leaderboardButton.setForeground(Color.WHITE);
        leaderboardButton.setBackground(Color.black);

        settingsButton.setBounds(30, 625, 50, 50);
        settingsButton.setOpaque(true);
        settingsButton.setBorderPainted(true);
        settingsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        settingsButton.setFont(new Font("Arial", Font.PLAIN, 10));
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setBackground(Color.pink);


        button11.setBounds(100, 10, 1061, 35);
        button11.setOpaque(true);
        button11.setContentAreaFilled(true);
        button11.setBorderPainted(true);
        button11.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        button11.setFont(new Font("Arial", Font.PLAIN, 10));
        button11.setForeground(Color.WHITE);
        button11.setBackground(Color.black);

        button13.setBounds(50, 50, 200, 620);
        button13.setOpaque(true);
        button13.setContentAreaFilled(true);
        button13.setBorderPainted(true);
        button13.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        button13.setFont(new Font("Arial", Font.PLAIN, 10));
        button13.setForeground(Color.WHITE);
        button13.setBackground(Color.black);

        button14.setBounds(1050, 50, 200, 620);
        button14.setOpaque(true);
        button14.setContentAreaFilled(true);
        button14.setBorderPainted(true);
        button14.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        button14.setFont(new Font("Arial", Font.PLAIN, 10));
        button14.setForeground(Color.WHITE);
        button14.setBackground(Color.black);

        volumeControl = new JSlider(0, 200, 25);
        volumeControl.setBounds(275, 250, 700, 100);
        volumeControl.setPaintTicks(true);
        volumeControl.setMinorTickSpacing(10);
        volumeControl.setPaintTrack(true);
        volumeControl.setMajorTickSpacing(25);
        volumeControl.setPaintLabels(true);
        volumeControl.setFont(new Font("Arial", 1, 20));
        volumeControl.setBorder(null);
        volumeControl.setBackground(Color.black);
        volumeControl.setForeground(Color.white);



        ButtonManager buttonManager = new ButtonManager(newGameButton, continueButton, loadSaveButton, controlsButton, leaderboardButton, settingsButton, button14, button13, volumeControl, button11);
        buttonTimer = new Timer(1, buttonManager);
        buttonTimer.start();

        panel1.setLayout(null);
        panel1.add(newGameButton);
        panel1.add(continueButton);
        panel1.add(loadSaveButton);
        panel1.add(controlsButton);
        panel1.add(leaderboardButton);
        panel1.add(settingsButton);
        panel1.setBackground(Color.red);

        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setBounds(0,0, 1280, 720);

        panel2.setOpaque(true);
        panel2.setBounds(0,0,1280, 720);

        button4.setOpaque(true);
        button4.setFont(new Font("1", 1, 7));
        button4.setBounds(1200,15,50,50);
        button4.setBackground(Color.black);
        button4.setBorderPainted(true);
        button4.setBorder(BorderFactory.createLineBorder(Color.pink, 1));
        button4.setForeground(Color.pink);

        button5.setOpaque(true);
        button5.setFont(new Font("1", 1, 7));
        button5.setBounds(1100,15,50,50);
        button5.setBackground(Color.black);
        button5.setBorderPainted(true);
        button5.setBorder(BorderFactory.createLineBorder(Color.red, 1));
        button5.setForeground(Color.red);

        button6.setOpaque(true);
        button6.setFont(new Font("1", 1, 7));
        button6.setBounds(1000,15,50,50);
        button6.setBackground(Color.black);
        button6.setBorderPainted(true);
        button6.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
        button6.setForeground(Color.blue);

        button7.setOpaque(true);
        button7.setFont(new Font("1", 1, 20));
        button7.setBounds(510,450,100,100);
        button7.setBackground(Color.black);
        button7.setBorderPainted(true);
        button7.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        button7.setForeground(Color.white);

        button8.setOpaque(true);
        button8.setFont(new Font("1", 1, 7));
        button8.setBounds(650,450,100,100);
        button8.setBackground(Color.black);
        button8.setBorderPainted(true);
        button8.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        button8.setForeground(Color.white);

        button9.setOpaque(true);
        button9.setFont(new Font("1", 1, 7));
        button9.setBounds(1200,25,50,50);
        button9.setBackground(Color.black);
        button9.setBorderPainted(true);
        button9.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        button9.setForeground(Color.white);


        JTextField textField = new JTextField("                              enter username:");
        textField.setBounds(415, 600, 500, 30);
        textField.setFont(new Font("Calibri", 1, 24));
        textField.setForeground(Color.blue);
        textField.setBackground(Color.black);
        textField.setCaretColor(Color.blue);
        textField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                textField.setText("");
            }
        });
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    new LeaderboardReaderWriter();
                    try {
                        LeaderboardReaderWriter.write(level, "leaderBoard.hs", textField.getText());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    textField.setText("enter username: ");
                    button5.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        jLayeredPane.add(panel2, JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(button4, JLayeredPane.DRAG_LAYER);
        jLayeredPane.add(button5, JLayeredPane.DRAG_LAYER);
        jLayeredPane.add(button6, JLayeredPane.DRAG_LAYER);


        panel3.add(button7);
        panel3.add(button8);

        panel4.add(button9);
        panel4.add(textField);

        panel5.add(button11);

        panel6.add(button13);

        panel7.add(button14);
        panel7.add(volumeControl);

        panel3.setLayout(null);
        panel4.setLayout(null);
        panel5.setLayout(null);
        panel6.setLayout(null);
        panel7.setLayout(null);

        panelContainer.add(panel1, "1");
        panelContainer.add(jLayeredPane, "2");
        panelContainer.add(panel3, "3");
        panelContainer.add(panel4, "4");
        panelContainer.add(panel5, "5");
        panelContainer.add(panel6, "6");
        panelContainer.add(panel7, "7");
        cl.show(panelContainer, "1");

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // start our game world simulation!
                //1. make an empty game world
                level.virtualDestroy();
                level.stop();
                level = new Level1(Game.this);

                //2. make a view to look into the game world

                view.setWorld(level);
                view.setSamurai(level.getSamurai());
                view.setGameLevel(level);
                controller.updateSamurai(level.getSamurai());
                controller.setLevel(level);
                mouseListener.updateSamurai(level.getSamurai());
                mouseListener.setLevel(level);


                level.populate();
                if(backgroundMusic != null){
                    backgroundMusic.stop();
                }
                PlayerController.lock = false;
                PlayerMouseListener.setLock(false);
                if(!(volume == 0.00)){
                    level.playMusic();
                    level.getBackgroundMusic().setVolume(volume);
                }
                level.start();
                buttonTimer.stop();
                cl.show(panelContainer, "2");
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = new File("data/recentSave/mostRecentSave.hs");
                if(f.exists()){
                    try {
                        BufferedReader brTest = new BufferedReader(new FileReader(f));
                        String text = brTest .readLine();
                        level.virtualDestroy();
                        level = GameSaverLoader.load(text, Game.this);

                        view.setWorld(level);
                        view.setSamurai(level.getSamurai());
                        view.setGameLevel(level);
                        controller.updateSamurai(level.getSamurai());
                        controller.setLevel(level);
                        mouseListener.updateSamurai(level.getSamurai());
                        mouseListener.setLevel(level);


                        if(backgroundMusic != null){
                            backgroundMusic.stop();
                        }
                        PlayerController.lock = false;
                        PlayerMouseListener.setLock(false);
                        if(!(volume == 0.00)){
                            level.playMusic();
                            level.getBackgroundMusic().setVolume(volume);
                        }
                        level.start();
                        buttonTimer.stop();
                        cl.show(panelContainer, "2");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        loadSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameSaverLoader();
                try {
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(".hs", "hs");
                    JFileChooser j = new JFileChooser();
                    j.setFileFilter(filter);
                    j.setCurrentDirectory(new File("data/saveFiles"));
                    int response = j.showOpenDialog(null);
                    if(response == JFileChooser.APPROVE_OPTION){
                        level.virtualDestroy();
                        level = GameSaverLoader.load("data/saveFiles/"+j.getSelectedFile().getName(), Game.this);
                        view.setWorld(level);
                        view.setSamurai(level.getSamurai());
                        view.setGameLevel(level);
                        controller.updateSamurai(level.getSamurai());
                        controller.setLevel(level);
                        mouseListener.updateSamurai(level.getSamurai());
                        mouseListener.setLevel(level);
                        if(backgroundMusic != null){
                            backgroundMusic.stop();
                        }
                        PlayerController.lock = false;
                        PlayerMouseListener.setLock(false);
                        if(!(volume == 0.00)){
                            level.playMusic();
                            level.getBackgroundMusic().setVolume(volume);
                        }
                        level.start();
                        buttonTimer.stop();
                        cl.show(panelContainer, "2");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String filename = new SimpleDateFormat("dd-MM-yyyy@HH.mm.ss").format(new Date().getTime());
                    GameSaverLoader.save(level, filename + ".hs");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(level.getBackgroundMusic() != null){
                    level.getBackgroundMusic().stop();
                }
                level.virtualDestroy();
                level.stop();
                PlayerController.lock = true;
                PlayerMouseListener.setLock(true);
                buttonTimer.start();
                cl.show(panelContainer, "1");
                try {
                    if(!(volume == 0.00)) {
                        backgroundMusic = new SoundClip("data/Music/justBreathing.wav");   // Open an audio input stream
                        backgroundMusic.loop();
                        backgroundMusic.setVolume(volume);
                    }
                    // Set it to continous playback (looping)
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException f) {
                    //code in here will deal with any errors
                    //that might occur while loading/playing sound
                    System.out.println(f);
                }
            }
        });

        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(level.getBackgroundMusic() != null){
                    level.getBackgroundMusic().stop();
                }
                level.virtualDestroy();
                level.stop();
                buttonTimer.stop();
                if(level instanceof Level1){

                    level.virtualDestroy();
                    level.stop();
                    level = new Level1(Game.this);

                    view.setWorld(level);
                    view.setSamurai(level.getSamurai());
                    view.setGameLevel(level);
                    controller.updateSamurai(level.getSamurai());
                    controller.setLevel(level);
                    mouseListener.updateSamurai(level.getSamurai());
                    mouseListener.setLevel(level);


                    level.populate();
                    if(backgroundMusic != null){
                        backgroundMusic.stop();
                    }
                    if(!(volume == 0.00)){
                        level.playMusic();
                        level.getBackgroundMusic().setVolume(volume);
                    }
                    level.start();
                }else{
                    File f = new File("data/recentSave/mostRecentSave.hs");
                    if(f.exists()){
                        try {
                            BufferedReader brTest = new BufferedReader(new FileReader(f));
                            String text = brTest .readLine();
                            level = GameSaverLoader.load(text, Game.this);
                            view.setWorld(level);
                            view.setSamurai(level.getSamurai());
                            view.setGameLevel(level);
                            controller.updateSamurai(level.getSamurai());
                            controller.setLevel(level);
                            mouseListener.updateSamurai(level.getSamurai());
                            mouseListener.setLevel(level);
                            if(backgroundMusic != null){
                                backgroundMusic.stop();
                            }
                            PlayerController.lock = false;
                            PlayerMouseListener.setLock(false);
                            if(!(volume == 0.00)){
                                level.playMusic();
                                level.getBackgroundMusic().setVolume(volume);
                            }
                            level.start();
                            cl.show(panelContainer, "2");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }

                PlayerController.lock = false;
                PlayerMouseListener.setLock(false);
                cl.show(panelContainer, "2");
            }
        });

        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button5.doClick();
            }
        });

        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button6.doClick();
            }
        });

        button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("enter username:");
                button5.doClick();
            }
        });

        controlsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelContainer, "5");
            }
        });

        button11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelContainer, "1");
            }
        });

        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> leaderBoard;
                ArrayList<Integer> sortHighScore = new ArrayList<>();
                ArrayList<String> sortedUsernames = new ArrayList<>();
                ArrayList<String> sortUsernames = new ArrayList<>();
                int display = 0;

                int y = 120;
                JLabel leaderBoardTitle = new JLabel("LEADERBOARD");
                leaderBoardTitle.setFont(new Font("Arial", Font.PLAIN, 30));
                leaderBoardTitle.setForeground(Color.white);
                leaderBoardTitle.setBounds(535, 35, 300, 50);

                JLabel displayUser = new JLabel("USERNAME");
                displayUser.setFont(new Font("Arial", Font.PLAIN, 20));
                displayUser.setForeground(Color.white);
                displayUser.setBounds(430, 75, 300, 50);

                JLabel displayScore = new JLabel("SCORE");
                displayScore.setFont(new Font("Arial", Font.PLAIN, 20));
                displayScore.setForeground(Color.white);
                displayScore.setBounds(730, 75, 300, 50);

                panel6.add(leaderBoardTitle);
                panel6.add(displayUser);
                panel6.add(displayScore);


                new LeaderboardReaderWriter();
                try {
                    leaderBoard = LeaderboardReaderWriter.read("data/leaderBoard/leaderBoard.hs");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                for(HashMap.Entry<String, String> entry : leaderBoard.entrySet()){
                    sortUsernames.add(entry.getKey());
                    sortHighScore.add(Integer.valueOf(entry.getValue()));
                }

                ArrayList<Integer> sortHighScoreCopy = (ArrayList<Integer>) sortHighScore.clone();
                Collections.sort(sortHighScore);
                Collections.reverse(sortHighScore);

                int value;
                for(int i = 0; i<sortHighScore.size(); i++){
                    if(display < 20) {
                        value = sortHighScore.get(i);
                        for (int j = 0; j < sortHighScoreCopy.size(); j++) {
                            if (sortHighScoreCopy.get(j) == value) {
                                sortedUsernames.add(sortUsernames.get(j));
                                sortHighScoreCopy.set(j, -1);
                            }
                        }

                        display++;
                    }
                }


                for (int k = 0; k<sortedUsernames.size(); k++) {
                        JLabel username = new JLabel(sortedUsernames.get(k));

                        username.setFont(new Font("Arial", Font.PLAIN, 15));
                        username.setBackground(Color.black);
                        username.setForeground(Color.white);
                        username.setBounds(430, y, 300, 50);

                        JLabel highScore = new JLabel(String.valueOf(sortHighScore.get(k)*7770));

                        highScore.setFont(new Font("Arial", Font.PLAIN, 15));
                        highScore.setForeground(Color.white);
                        highScore.setBounds(730, y, 300, 50);

                        y+=30;

                        panel6.add(username);
                        panel6.add(highScore);
                }
                cl.show(panelContainer, "6");
            }
        });

        button13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelContainer, "1");
            }
        });

        button14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelContainer, "1");
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelContainer, "7");
            }
        });

        volumeControl.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                volume = volumeControl.getValue();
                volume /= 100;
                if(volume == 0.00){
                    paused = true;
                    backgroundMusic.pause();
                }else {
                    if(paused){
                        backgroundMusic.resume();
                        paused = false;
                    }
                    backgroundMusic.setVolume(volume);
                }
            }
        });



        //1. make an empty game world
        level = new BlankLevel(this);

        //2. make a view to look into the game world
        view = new GameView(level,1280, 720);


        //3. add controller to see keyboard inputs
        controller = new PlayerController(level.getSamurai(), level, view);
        mouseListener = new PlayerMouseListener(level.getSamurai(), level, view);
        view.addMouseListener(mouseListener);
        view.addKeyListener(controller);

        //4. create a Java window (frame) and add the game
        //   view to it
        panel2.setBorder(null);
        panel2.add(view);
        cl.setHgap(-5);
        cl.setVgap(-5);

        frame.add(panelContainer);

        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);

        frame.setSize(1280, 720);

        GiveFocus giveFocus = new GiveFocus(view);

        // grabs the focus of os to allow for keyboard inputs
        view.requestFocus();

        try {
            backgroundMusic = new SoundClip("data/Music/justBreathing.wav");   // Open an audio input stream
            backgroundMusic.loop();
            if(volume == 0.00){
                backgroundMusic.pause();
            }else{
                backgroundMusic.setVolume(volume);
            }
            // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }

    public GameView getView() {
        return view;
    }

    public void goToNextLevel(int samuraiHealth) {
        if(level instanceof Level1){
            level.virtualDestroy();
            level.stop();
            level = new Level2(this);
            if(!(volume == 0.00)){
                level.playMusic();
                level.getBackgroundMusic().setVolume(volume);
            }
            level.populate();
            view.setWorld(level);
            view.setSamurai(level.getSamurai());
            view.setGameLevel(level);
            controller.updateSamurai(level.getSamurai());
            controller.setLevel(level);
            mouseListener.updateSamurai(level.getSamurai());
            mouseListener.setLevel(level);
            level.start();
            level.getSamurai().setHealth(samuraiHealth);
            level.getSamurai().setHealthSize(samuraiHealth*3);
        }else if(level instanceof Level2){
            level.virtualDestroy();
            level.stop();
            level = new Level3(this);
            if(!(volume == 0.00)){
                level.playMusic();
                level.getBackgroundMusic().setVolume(volume);
            }
            level.populate();
            view.setWorld(level);
            view.setSamurai(level.getSamurai());
            view.setGameLevel(level);
            controller.updateSamurai(level.getSamurai());
            controller.setLevel(level);
            mouseListener.updateSamurai(level.getSamurai());
            mouseListener.setLevel(level);
            level.start();
            level.getSamurai().setHealth(samuraiHealth);
            level.getSamurai().setHealthSize(samuraiHealth*3);
        } else if(level instanceof Level3){
            level.virtualDestroy();
            level.stop();
            level = new Level4(this);
            if(!(volume == 0.00)){
                level.playMusic();
                level.getBackgroundMusic().setVolume(volume);
            }
            level.populate();
            view.setWorld(level);
            view.setSamurai(level.getSamurai());
            view.setGameLevel(level);
            controller.updateSamurai(level.getSamurai());
            controller.setLevel(level);
            mouseListener.updateSamurai(level.getSamurai());
            mouseListener.setLevel(level);
            level.start();
            level.getSamurai().setHealth(samuraiHealth);
            level.getSamurai().setHealthSize(samuraiHealth*3);
        }else if(level instanceof Level4){
            level.virtualDestroy();
            level.stop();
            level = new Level5(this);
            if(!(volume == 0.00)){
                level.playMusic();
                level.getBackgroundMusic().setVolume(volume);
            }
            level.populate();
            view.setWorld(level);
            view.setSamurai(level.getSamurai());
            view.setGameLevel(level);
            controller.updateSamurai(level.getSamurai());
            controller.setLevel(level);
            mouseListener.updateSamurai(level.getSamurai());
            mouseListener.setLevel(level);
            level.start();
            level.getSamurai().setHealth(samuraiHealth);
            level.getSamurai().setHealthSize(samuraiHealth*3);
        }
        else if (level instanceof Level5) {
            level.virtualDestroy();
            level.stop();
            cl.show(panelContainer, "4");
        }
    }

    public void showDeathScreen() {
        cl.show(panelContainer, "3");
    }

    /** Run the game. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game();
            }
        });

    }
}
