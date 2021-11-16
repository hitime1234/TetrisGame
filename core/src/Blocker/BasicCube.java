package Blocker;

import java.util.Vector;

public class BasicCube {
    private int length = 25;
    private int height = 25;
    private int x = 0;
    private int y = 0;
    private int Vector[][];

    public BasicCube(int x,int y,int length,int height,int Vector[][]){
        this.y = y;
        this.x = x;
        this.length = length;
        this.height = height;
        this.Vector = Vector;
    }

    public int[][] getVector() {
        //X axis
        Vector[0][0] = x;
        //Y axis
        Vector[0][1] = y;

        //[1,0]
        Vector[1][0] = x + length;
        Vector[1][1] = y;

        //[0,1]
        Vector[2][0] = x;
        Vector[2][1] = y + height;

        //[1,1]
        Vector[3][0] = x + length;
        Vector[3][1] = y + height;
        return Vector;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
