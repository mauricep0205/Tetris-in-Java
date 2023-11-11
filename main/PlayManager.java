package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import Tetriminos.BarMino;
import Tetriminos.Block;
import Tetriminos.L1Mino;
import Tetriminos.L2Mino;
import Tetriminos.SquareMino;
import Tetriminos.Supermino;
import Tetriminos.TMino;
import Tetriminos.Z1Mino;
import Tetriminos.Z2Mino;

public class PlayManager {
    // Main Playing Area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int leftX;
    public static int rightX;
    public static int topY;
    public static int bottomY;

    // Tetriminos
    Supermino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Supermino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    // Other integers
    public static int dropInterval = 60; // Tetrimino falls every 60 frames
    boolean gameOver;

    // Effects
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    // Score
    int lvl = 1, lines, score;

    public PlayManager() {
        // Setting up the frame of the play area
        leftX = (GamePanel.WIDTH / 2) - (WIDTH / 2);
        rightX = leftX + WIDTH;
        topY = 50;
        bottomY = topY + HEIGHT;

        MINO_START_X = leftX + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = topY + Block.SIZE;

        NEXTMINO_X = rightX + 175;
        NEXTMINO_Y = topY + 500;

        // Setting the starting Tetrimino
        currentMino = pickMino();
        currentMino.setXandY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXandY(NEXTMINO_X, NEXTMINO_Y);
    }

    private Supermino pickMino() {
        Supermino mino = null;
        int i = new Random().nextInt(7);

        switch(i) {
            case 0: mino = new L1Mino(); break;
            case 1: mino = new L2Mino(); break;
            case 2: mino = new SquareMino(); break;
            case 3: mino = new BarMino(); break;
            case 4: mino = new TMino(); break;
            case 5: mino = new Z1Mino(); break;
            case 6: mino = new Z2Mino(); break;
        }

        return mino;
    }

    public void update() {
        if (currentMino.active == false) {
            // If Tetrimino reaches the floor, it is no longer active and is replaced by next mino
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            // check if game is over
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                gameOver = true;
                GamePanel.music.stop();
                GamePanel.soundEffect.play(2, false);
            }

            currentMino.deactivating = false;

            currentMino = nextMino;
            currentMino.setXandY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXandY(NEXTMINO_X, NEXTMINO_Y);

            // After Tetrimino becomes inactive, check if line(s) can be deleted
            checkDelete();
        } else
            currentMino.update();
    }

    private void checkDelete() {
        int x = leftX;
        int y = topY;
        int blockCount = 0;
        int lineCount = 0;

        while (x < rightX && y < bottomY) {
            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y)
                    blockCount++;
            }
            
            x += Block.SIZE;

            if (x == rightX) {
                // If blockCount hits 12, the row is filled with blocks
                // Delete row(s)
                if (blockCount == 12) {
                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        if (staticBlocks.get(i).y == y)
                            staticBlocks.remove(i);
                    }

                    lineCount++;
                    lines++;
                    // Augment drop speed if line score hits a certain number, with 1 being the fastest
                    if (lines % 10 == 0 && dropInterval > 1) {
                        lvl++;
                        if (dropInterval > 10)
                            dropInterval -= 10;
                        else
                            dropInterval -= 1;
                    }

                    // Now that row(s) have been deleted, other blocks must come down
                    for (int i = 0; i < staticBlocks.size(); i++) {
                        if (staticBlocks.get(i).y < y)
                            staticBlocks.get(i).y += Block.SIZE;
                    }
                }

                blockCount = 0;
                x = leftX;
                y += Block.SIZE;
            }
        }

        // Score
        if (lineCount > 0) {
            GamePanel.soundEffect.play(1, false);
            int singleLineScore = 10 * lvl;
            score += singleLineScore * lineCount;
        }
    }

    public void draw(Graphics2D g2) {
        // Drawing the frame of the play area
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f)); // setting the boundary of the frame to 4
        g2.drawRect(leftX - 4, topY - 4, WIDTH + 8, HEIGHT + 8); // ensures the actual play area is contained within the boundary and doesn't go inside

        // Drawing frame for incoming Tetromino
        int x = rightX + 100;
        int y = bottomY - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        // Drawing frame for score
        g2.drawRect(x, topY, 250, 300);
        x += 40;
        y = topY + 90;
        g2.drawString("LEVEL: " + lvl, x, y); y += 70;
        g2.drawString("LINES: " + lines, x, y); y += 70;
        g2.drawString("SCORE: " + score, x, y);

        // Drawing current Tetrimino
        if (currentMino != null)
            currentMino.draw(g2);

        // Drawing next Tetrimino
        nextMino.draw(g2);

        // Draw static blocks
        for (int i = 0; i < staticBlocks.size(); i++)
            staticBlocks.get(i).draw(g2);

        // Draw effects
        if (effectCounterOn) {
            effectCounter++;
            g2.setColor(Color.gray);
            for (int i = 0; i < effectY.size(); i++)
                g2.fillRect(leftX, effectY.get(i), WIDTH, Block.SIZE);

            if (effectCounter == 12) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        // Pause or game over text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameOver) {
            x = leftX + 25;
            y = topY + 320;
            g2.drawString("GAME OVER", x, y);
        } else if (KeyHandler.pausePressed) {
            x = leftX + 80;
            y = topY + 320;
            g2.drawString("PAUSED", x, y);
        }

        // Positioning game title
        x = 45;
        y = topY + 320;
        g2.setColor(Color.white);
        g2.setFont(new Font("Comic Sans", Font.ITALIC, 60));
        g2.drawString("Tetris in Java", x, y);
    }
}
