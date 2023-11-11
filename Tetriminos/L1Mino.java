package Tetriminos;

import java.awt.Color;

public class L1Mino extends Supermino {
    public L1Mino() {
        create(Color.orange);
    }

    public void setXandY(int x, int y) {
        // creating the shape of the L tetrimino
        b[0].x = x; // this is the middle block, since this block remains constant when rotating the tetrimino
        b[0].y = y;
        b[1].x = b[0].x; // block 1 is above block 0
        b[1].y = b[0].y - Block.SIZE;
        b[2].x = b[0].x; // block 2 is below block 0
        b[2].y = b[0].y + Block.SIZE;
        b[3].x = b[0].x + Block.SIZE; // block 3 is to the right of block 2
        b[3].y = b[0].y + Block.SIZE;
    }

    public void getDirection1() {
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y + Block.SIZE;

        updateXandY(1);
    }

    public void getDirection2() {
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x - Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x - Block.SIZE;
        tempB[3].y = b[0].y + Block.SIZE;

        updateXandY(2);
    }

    public void getDirection3() { 
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y + Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y - Block.SIZE;
        tempB[3].x = b[0].x - Block.SIZE;
        tempB[3].y = b[0].y - Block.SIZE;

        updateXandY(3);
    }

    public void getDirection4() { 
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y - Block.SIZE;

        updateXandY(4);
    }
}
