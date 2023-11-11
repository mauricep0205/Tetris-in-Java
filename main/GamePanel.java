package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    PlayManager pm;
    public static Sound music = new Sound();
    public static Sound soundEffect = new Sound();

    public GamePanel() {
        // Panel settings
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        // KeyListener
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        pm = new PlayManager();
    }

    public void initGame() { // Initializing the game
        gameThread = new Thread(this);
        gameThread.start();

        music.play(0, true);
        music.loop();
    }

    @Override
    public void run() {
        // Game loop
        double frameTimer;
        double frames = 0;
		long currentTime;
		long lastCheck = System.nanoTime();
		frameTimer = 1000000000.0 / FPS;
		
        while (gameThread != null) {
            currentTime = System.nanoTime();

            frames += (currentTime - lastCheck) / frameTimer;
            lastCheck = currentTime;

            if (frames >= 1) {
                update();
                repaint();
                frames--;
            }
		}
    }

    private void update() {
        if (KeyHandler.pausePressed == false && pm.gameOver == false) {
            pm.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        pm.draw(g2);
    }
}