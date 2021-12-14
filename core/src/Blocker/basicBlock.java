package Blocker;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.lang.*;

import static java.lang.Math.*;

public class basicBlock {
    int x;
    int y;
    int speed;
    ShapeRenderer draw;
    private final int[][] Vector;
    int Shape = -1;
    private int length = 25;
    private int height = 25;
    public BasicCube[] cube;
    private Boolean Damage = false;
    //this copium --jackus
    //fix your code god dammit


    private void CubeCreator(){
        switch (Shape) {
            //cube
            case 0:
                cube = new BasicCube[4];
                cube[3] = new BasicCube(x - (0), y + (height), length, height, FindVector(x - (0), y + (height), length, height));
                cube[2] = new BasicCube(x + (length), y + (height), length, height, FindVector(x + (length), y + (height), length, height));
                cube[0] = new BasicCube(x - (0), y + (0), length, height, FindVector(x - (0), y + (0), length, height));
                cube[1] = new BasicCube(x + (length), y + (0), length, height, FindVector(x + (length), y + (0), length, height));
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
                cube[0] = new BasicCube(x,y+25,25,25,FindVector(x,y+25,25,25));
                cube[1] = new BasicCube(x,y,25,25,FindVector(x,y,25,25));
                cube[2] = new BasicCube(x,y+75,25,25,FindVector(x,y+75,25,25));
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



    public basicBlock(ShapeRenderer draw, int x, int y, int speed, int[][] vector, int shape) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.draw = draw;
        Vector = vector;
        this.Shape = shape;

        CubeCreator();
    }

    public void flip() {
        if (Shape != -1) {
            for (int i = 0; i < getNumberOCubes(); i++) {
                int newX = findingX(cube[i], i);
                int newY = findingY(cube[i],i);
                int storeAngle = cube[i].getAngler();
                cube[i]= new BasicCube(newX, newY, 25, 25, FindVector(newX, newY, 25, 25));
                cube[i].setAngler(storeAngle);
            }
        }
    }

    private int findingX(BasicCube basicCube, int i) {
        int oldX = basicCube.getX();
        int NewX = oldX;
        switch (Shape){
                //cube
                case 0:
                    NewX = oldX;
                    break;
                //T Man
                case 1:
                    switch (i){
                        case 0:
                            double rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(cos((rad+1)))))*25);
                            cube[i].PassAngle();
                            break;

                        case 2:
                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(sin(rad) +cos(rad))))*25);
                            cube[i].PassAngle();
                            break;

                        case 3:
                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(sin(rad) +cos((rad)))))*-25);
                            cube[i].PassAngle();
                            break;


                        default:
                            NewX = oldX;
                            break;
                    }
                    break;


                //L Piece
                case 2:
                    switch (i){
                        case 0:
                            double rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(cos((rad+1)))))*25);
                            cube[i].PassAngle();
                            break;

                        case 1:
                            NewX = oldX;
                            cube[i].PassAngle();
                            break;
                        case 2:

                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(sin(rad) +cos((rad)))))*-25);
                            cube[i].PassAngle();
                            break;


                        case 3:
                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(cos((rad+1)))))*25*2);
                            cube[i].PassAngle();
                            break;
                        default:
                            NewX =oldX;
                            break;
                    }
                    break;



            case 4:
                switch (i){
                    case 0:
                        double rad = Math.toRadians(cube[i].getAngler());
                        NewX = oldX+(((int)( Math.round(cos((rad+1)))))*25);
                        cube[i].PassAngle();
                        break;

                    case 1:
                        NewX = oldX;
                        cube[i].PassAngle();
                        break;
                    case 2:

                        rad = Math.toRadians(cube[i].getAngler());
                        NewX = oldX+(((int)( Math.round(sin(rad) +cos((rad)))))*25);
                        cube[i].PassAngle();
                        break;


                    case 3:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewX = oldX+(((int)( Math.round(cos((rad+1)))))*25*2);
                        cube[i].PassAngle();
                        break;
                    default:
                        NewX =oldX;
                        break;
                }
                break;





                    //J piece
                case 5:
                    switch (i){
                        case 0:
                            double rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(cos((rad+1)))))*25);
                            cube[i].PassAngle();
                            break;

                        case 1:
                            NewX = oldX;
                            cube[i].PassAngle();
                            break;
                        case 2:

                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(sin(rad) +cos((rad)))))*25);
                            cube[i].PassAngle();
                            break;


                        case 3:
                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(cos((rad+1)))))*25*2);
                            cube[i].PassAngle();
                            break;
                        default:
                            NewX =oldX;
                            break;
                    }
                    break;




        }
        return NewX;
    }

    private int findingY(BasicCube basicCube, int i) {
        int oldY = basicCube.getY();
        int NewY = oldY;
        switch (Shape){
            case 0:
                NewY = oldY;
                break;
            case 1:
                switch (i){
                    case 0:
                        double rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(cos((rad+1)))))*25);
                        break;
                    case 2:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(cos((rad-1)))))*25);
                        //NewY = oldY+(((int)( Math.round(sin(rad) +cos((rad)))))*25);
                        break;
                    case 3:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(cos(rad-1))*-25)));
                        break;

                    default:
                        NewY = oldY;
                        break;
                }
                break;
            case 2:
                switch (i){
                    case 0:
                        double rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(cos((rad+1)))))*25);

                        break;
                    case 1:
                        NewY = oldY;
                        break;
                    case 2:
                        rad = Math.toRadians(cube[2].getAngler());
                        NewY = oldY+(((int)Math.round(1/(sin(rad)+cos(rad))))*-25);
                        break;
                    case 3:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(cos((rad+1)))))*25*2);
                        break;
                    default:
                        NewY =oldY;
                        break;
                }
                break;

            case 4:
                switch (i){
                    case 0:
                        double rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(1/(sin(rad+PI/2)+cos(rad+PI/2))))*25));

                        break;
                    case 1:
                        NewY = oldY;
                        break;
                    case 2:
                        rad = Math.toRadians(cube[2].getAngler());
                        NewY = oldY+(((int)Math.round(1/(sin(rad)+cos(rad))))*25);
                        break;
                    case 3:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(cos((rad+1)))))*25*2);
                        break;
                    default:
                        NewY =oldY;
                        break;
                }
                break;




            case 5:
                switch (i){
                    case 0:
                        double rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(1/(sin(rad+PI/2)+cos(rad+PI/2))))*25));

                        break;
                    case 1:
                        NewY = oldY;
                        break;
                    case 2:
                        rad = Math.toRadians(cube[2].getAngler());
                        NewY = oldY+(((int)Math.round(1/(sin(rad)+cos(rad))))*25);
                        break;
                    case 3:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(cos((rad+1)))))*25*2);
                        break;
                    default:
                        NewY =oldY;
                        break;
                }
                break;



        }
        return NewY;
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
        this.y = y;
        this.x = cube[1].getX();
        CubeCreator();
    }

    public void dropY(int yDrop,int newStart){
        y = y - yDrop;
        for (int i=0;i<getNumberOCubes();i++){
            if (cube[i] != null) {
                if (cube[i].getY() >= newStart) {
                    cube[i].setY(cube[i].getY() - yDrop);
                }
            }
        }
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
        return false;
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
        y = y - 25;
        for (int i = 0; i < getNumberOCubes(); i++) {
            cube[i].setY(cube[i].getY() - 25);
        }
    }

    public void draw() {
        for (int i = 0; i < 4; i++) {
            try {
                if (cube[i] != null) {
                    if (Damage == true){
                        System.out.println("debug mode");
                    }
                    int xHold = cube[i].getX();
                    int yHold = cube[i].getY();
                    int BLength = cube[i].getLength();
                    int BHeight = cube[i].getHeight();
                    draw.begin(ShapeRenderer.ShapeType.Filled);
                    draw.rect(xHold, yHold, BLength, BHeight);
                    draw.end();
                }
            }
            catch (Exception e){
                System.err.println("render error");
            }
        }
    }
}
