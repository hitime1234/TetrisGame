package Blocker;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Vector;

public class basicBlock {
    int x;
    int y;
    int speed;
    ShapeRenderer draw;
    int[][] Vector;
    int Shape = -1;
    public basicBlock(ShapeRenderer draw, int x, int y, int speed,int[][] Vector,int shape){
        this.x = x;
        this.y = y;
        this.speed =speed;
        this.draw = draw;
        this.Vector = Vector;
        this.Shape = shape;
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
        return Vector;
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
