package Blocker;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Vector;

public class basicBlock {
    int x;
    int y;
    int speed;
    ShapeRenderer draw;
    private final int[][] Vector;
    int Shape = -1;
    private  int length;
    private  int height;

    public basicBlock(ShapeRenderer draw, int x, int y, int speed,int[][] vector,int shape){
        this.x = x;
        this.y = y;
        this.speed =speed;
        this.draw = draw;
        Vector = vector;
        this.Shape = shape;
        switch (shape){
            case 0:
                length = 50;
                height = 50;
                break;

            default:
                length = 0;
                height = 0;
        }
    }



    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] getVector() {
        if (Shape ==0) {
            //X axis
            Vector[0][0] = x;
            //Y axis
            Vector[0][1] = y;

            //[1,0]
            Vector[1][0] = x+50; Vector[1][1] = y;

            //[0,1]
            Vector[2][0] = x; Vector[2][1] = y+50;

            //[1,1]
            Vector[3][0] = x+50; Vector[3][1] = y+50;
        }
        return Vector;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void pass(){
        y = y - speed;
    }

    public void draw(){
        draw.begin(ShapeRenderer.ShapeType.Filled);
        draw.rect(x,y,50,50);
        draw.end();

    }
}
