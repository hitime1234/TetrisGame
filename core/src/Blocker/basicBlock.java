package Blocker;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.graalvm.compiler.nodes.NodeView;

import java.util.Vector;

public class basicBlock {
    int x;
    int y;
    int speed;
    ShapeRenderer draw;
    private final int[][] Vector;
    int Shape = -1;
    private int length = 25;
    private int height = 25;
    private BasicCube[] cube;

    public basicBlock(ShapeRenderer draw, int x, int y, int speed, int[][] vector, int shape) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.draw = draw;
        Vector = vector;
        this.Shape = shape;



        switch (shape) {
            //cube
            case 0:
                cube = new BasicCube[4];
                cube[0] = new BasicCube(x - (0), y - (height), length, height, FindVector(x - (0), y - (height), length, height));
                cube[1] = new BasicCube(x + (length), y - (height), length, height, FindVector(x + (length), y - (height), length, height));
                cube[2] = new BasicCube(x - (0), y + (0), length, height, FindVector(x - (0), y + (0), length, height));
                cube[3] = new BasicCube(x + (length), y + (0), length, height, FindVector(x + (length), y + (0), length, height));
                break;

                //T-Man
            case 1:
                cube = new BasicCube[4];
                cube[0] = new BasicCube(x,y+25,25,25,FindVector(x,y+25,25,25));
                cube[1] = new BasicCube(x,y, 25,25,FindVector(x,y, 25,25));
                cube[2] = new BasicCube(x-25,y,25,25,FindVector(x-25,y,25,25));
                cube[3] = new BasicCube(x+25,y,25,25,FindVector(x+25,y,25,25));
                break;

            //L Block
            case 2:
                cube = new BasicCube[4];
                cube[0] = new BasicCube(x,y+25,25,25,FindVector(x,y+25,25,25));
                cube[1] = new BasicCube(x,y,25,25,FindVector(x,y,25,25));
                cube[2] = new BasicCube(x+25,y,25,25,FindVector(x,y,25,25));
                cube[3] = new BasicCube(x,y+50,25,25,FindVector(x,y+50,25,25));
                break;


            //line
            case 3:
                cube = new BasicCube[4];
                cube[0] = new BasicCube(x,y-25,25,25,FindVector(x,y-25,25,25));
                cube[1] = new BasicCube(x,y,25,25,FindVector(x,y,25,25));
                cube[2] = new BasicCube(x,y+25,25,25,FindVector(x,y+25,25,25));
                cube[3] = new BasicCube(x,y+50,25,25,FindVector(x,y+50,25,25));
                break;

            //Diag Right
            case 4:
                cube = new BasicCube[4];
                cube[0] = new BasicCube(x+25,y,25,25,FindVector(x+25,y,25,25));
                cube[1] = new BasicCube(x,y,25,25,FindVector(x,y,25,25));
                cube[2] = new BasicCube(x+25,y+25,25,25,FindVector(x+25,y+25,25,25));
                cube[3] = new BasicCube(x+50,y+25,25,25,FindVector(x+50,y+25,25,25));
                break;

            //J block
            case 5:
                cube = new BasicCube[4];
                cube[0] = new BasicCube(x,y+25,25,25,FindVector(x,y+25,25,25));
                cube[1] = new BasicCube(x,y,25,25,FindVector(x,y,25,25));
                cube[2] = new BasicCube(x-25,y,25,25,FindVector(x,y,25,25));
                cube[3] = new BasicCube(x,y+50,25,25,FindVector(x,y+50,25,25));
                break;

            //diag Left
            case 6:
                cube = new BasicCube[4];
                cube = new BasicCube[4];
                cube[0] = new BasicCube(x-25,y,25,25,FindVector(x+25,y,25,25));
                cube[1] = new BasicCube(x,y,25,25,FindVector(x,y,25,25));
                cube[2] = new BasicCube(x-25,y+25,25,25,FindVector(x+25,y+25,25,25));
                cube[3] = new BasicCube(x-50,y+25,25,25,FindVector(x+50,y+25,25,25));
                break;

            default:
                length = 0;
                height = 0;
                break;
        }
    }

    public void flip(){
        for (int i=0;i<getNumberOCubes();i++){

        }
    }

    private int[][] FindVector(int Nx, int Ny, int nlength, int nheight) {
        Vector[0][0] = Nx;
        Vector[0][1] = Ny;
        Vector[1][0] = Nx + nlength;
        Vector[1][1] = Ny;
        Vector[2][0] = Nx;
        Vector[2][1] = Ny + nheight;
        Vector[3][0] = Nx + nlength;
        Vector[3][1] = Ny + nheight;
        return Vector;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        int hold = y;
        cube[0].setY(y*2 - (height));
        cube[1].setY(y*2 - (height));
        cube[2].setY(y*2);
        cube[3].setY(y*2);
    }


    public void moveX(int x) {
        for (int i = 0; i < getNumberOCubes(); i++) {
            cube[i].setX(cube[i].getX() + x);
        }
    }

    public int getX() {
        return cube[1].getX();
    }

    public int getY() {
        return cube[1].getY();
    }

    public int GetLowestY(){
        int hold = 0;
        hold = cube[0].getY();
        for (int i=0;i<getNumberOCubes();i++){
            if (cube[i].getY()<hold){
                hold = cube[i].getY();
            }
        }
        return hold;
    }

    public int getNumberOCubes() {
        return cube.length;
    }

    public BasicCube[] getCube() {
        return cube;
    }

    public boolean RemoveCube(int index) {
        int count = 0;
        boolean Result = false;
        for (int i = 0; i < getNumberOCubes(); i++) {
            if (cube[i] == null) {
                count++;
            }
            if(cube[i].getY() == index) {
                System.out.println(cube[i].getY());
                cube[i] = null;
            }
        }
        if (count == getNumberOCubes()) {
            Result = true;
        }
        return Result;
    }

    public int[][] getVector(int index) {
        switch (Shape) {
            case 0:
                /**
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
                 break;

                 old system with only one shape at a time
                 */
                return cube[index].getVector();
            case 3:
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
                break;
            default:
                break;
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

    public void pass() {
        y = y - speed;
        for (int i = 0; i < getNumberOCubes(); i++) {
            cube[i].setY(cube[i].getY() - speed);
        }
    }

    public void draw() {
        for (int i = 0; i < getNumberOCubes(); i++) {
            System.out.println("i:" + i);
            int xHold = cube[i].getX();
            int yHold = cube[i].getY();
            int BLength = cube[i].getLength();
            int BHeight = cube[i].getHeight();
            draw.begin(ShapeRenderer.ShapeType.Filled);
            draw.rect(xHold, yHold, BLength, BHeight);
            draw.end();
        }
    }
}
