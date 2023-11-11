package Tetriminos;

import java.awt.Color;

public class BarMino extends Supermino {
    public BarMino() {
        create(Color.cyan);
    }

    public void setXandY(int x, int y) {
        // creating the shape of the bar tetrimino
        b[0].x = x;
        b[0].y = y;
        b[1].x = b[0].x - Block.SIZE;
        b[1].y = b[0].y;
        b[2].x = b[0].x + Block.SIZE;
        b[2].y = b[0].y;
        b[3].x = b[0].x + (Block.SIZE * 2);
        b[3].y = b[0].y;
    }

    public void getDirection1() {
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x + (Block.SIZE * 2);
        tempB[3].y = b[0].y;

        updateXandY(1);
    }

    public void getDirection2() {
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y + (Block.SIZE * 2);

        updateXandY(2);
    }

    public void getDirection3() { 
        getDirection1();
    }

    public void getDirection4() { 
        getDirection2();
    }
}