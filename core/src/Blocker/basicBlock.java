package Blocker;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.UnsupportedEncodingException;
import java.lang.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import static java.lang.Math.*;

public class basicBlock {
    public int x;
    public int y;
    public int speed;
    public ShapeRenderer draw;
    public int[][] Vector;
    public int Shape = -1;
    private int length = 25;
    private int height = 25;
    public BasicCube[] cube;
    private Boolean Damage = false;
    //this copium --jackus
    //fix your code god dammit



    public String WriteToCsv(int[][] myArray) throws UnsupportedEncodingException {
        StringBuilder res = new StringBuilder();
        for (int[] ints : myArray) {
            for (int anInt : ints) res.append(URLEncoder.encode(String.valueOf(anInt), "UTF-8")).append(",");
            res.append("\n");
        }
        return res.toString();
    }


    public int[][] read(String res) throws UnsupportedEncodingException {
        String[] rows = res.split("\n");
        int[][] myArray = new int[rows.length][];
        for (int iRow = 0; iRow < rows.length; iRow++) {
            String[] cols = rows[iRow].split(",");
            myArray[iRow] = new int[cols.length];
            for (int iCol = 0; iCol < cols.length; iCol++)
                myArray[iRow][iCol] = Integer.parseInt(URLDecoder.decode(cols[iCol], "UTF-8"));
        }
        return myArray;
    }




    public ArrayList CloneData() throws UnsupportedEncodingException {
        ArrayList<String> data = new ArrayList<>();
        data.add(WriteToCsv(Vector));
        data.add(String.valueOf(Shape));
        data.add(String.valueOf(length));
        data.add(String.valueOf(height));
        data.add(Boolean.toString(Damage));
        return data;
    }



    public void SetValues(int[][] Vector,int shape,int length,int height,boolean Damage){
        this.Vector = Vector;
        this.Shape = shape;
        this.length = length;
        this.height = height;
        this.Damage = Damage;
    }

    public boolean CheckGreaterX(int x){
        boolean result = false;
        for (int i=0;i<getNumberOCubes();i++) {
            if (cube[i].getX() >= x){
                result = true;
            }
        }
        return result;
    }

    public boolean CheckLessX(int x){
        boolean result = false;
        for (int i=0;i<getNumberOCubes();i++) {
            if (cube[i].getX() <= x){
                result = true;
            }
        }
        return result;
    }

    public int HeightOfBlock(){
        int TempHigh = cube[0].getY();
        int TempLow = cube[0].getY();
        for (int i=0;i<getNumberOCubes();i++){
            if (TempHigh <= cube[i].getY()){
                TempHigh = cube[i].getY();
            }
            if (TempLow >= cube[i].getY()){
                TempLow = cube[i].getY();
            }
        }
        return  TempHigh - TempLow;
    }

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
            //sent line piece
            case 7:
                cube = new BasicCube[9];
                cube[0] = new BasicCube(x,y,25,25,FindVector(x,y,25,25));
                cube[1] = new BasicCube(x+25,y,25,25,FindVector(x+25,y,25,25));
                cube[2] = new BasicCube(x+50,y,25,25,FindVector(x+50,y,25,25));
                cube[3] = new BasicCube(x+75,y,25,25,FindVector(x+75,y,25,25));
                cube[4] = new BasicCube(x+100,y,25,25,FindVector(x+100,y,25,25));
                cube[5] = new BasicCube(x+150,y,25,25,FindVector(x+150,y,25,25));
                cube[6] = new BasicCube(x+175,y,25,25,FindVector(x+175,y,25,25));
                cube[7] = new BasicCube(x+200,y,25,25,FindVector(x+200,y,25,25));
                cube[8] = new BasicCube(x+225,y,25,25,FindVector(x+225,y,25,25));
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

    public basicBlock clone()  {
       return new basicBlock(draw,x,y,speed,Vector,Shape);
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

                case 3:
                    switch (i){
                        case 0:
                            double rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX;
                            cube[i].PassAngle();
                            break;
                        case 1:
                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(tan(rad+((3*PI)/4)))))*25);
                            cube[i].PassAngle();
                            break;

                        case 2:
                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(tan(rad+((3*PI)/4)))))*-25*2);
                            cube[i].PassAngle();
                            break;

                        case 3:
                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(tan(rad+((3*PI)/4)))))*-25);
                            cube[i].PassAngle();
                            break;


                        default:
                            NewX = oldX;
                            break;
                    }
                    break;



            //diag
                case 4:
                    switch (i){
                        case 0:
                            double rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(tan(rad+((3*PI)/4)))))*-25);
                            cube[i].PassAngle();
                            break;

                        case 1:
                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(tan(rad+((3*PI)/4)))))*-25);
                            cube[i].PassAngle();
                            break;
                        case 2:
                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(tan(rad+((3*PI)/4)))))*-25);
                            cube[i].PassAngle();
                            break;


                        case 3:
                            rad = Math.toRadians(cube[i].getAngler());
                            NewX = oldX+(((int)( Math.round(tan(rad+((3*PI)/4)))))*25);
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

            //diag Left
            case 6:
                switch (i){
                    case 0:
                        double rad = Math.toRadians(cube[i].getAngler());
                        NewX = oldX;
                        cube[i].PassAngle();
                        break;

                    case 1:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewX = oldX;
                        cube[i].PassAngle();
                        break;
                    case 2:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewX = oldX;
                        cube[i].PassAngle();
                        break;


                    case 3:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewX = oldX+(((int)( Math.round(tan(rad+((3*PI)/4)))))*25*-2);
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
            case 3:
                switch (i){
                    case 0:
                        double rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY;
                        break;
                    case 1:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(tan(rad+((3*PI)/4)-PI/2))))*-25);
                        break;

                    case 2:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(tan(rad+((3*PI)/4)-PI/2))))*25*2);
                        break;

                    case 3:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(tan(rad+((3*PI)/4)-PI/2))))*25);
                        break;


                    default:
                        NewY = oldY;
                        break;
                }
                break;
            case 4:
                switch (i){
                    case 0:
                        double rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(tan(rad+((3*PI)/4)))))*-25);
                        break;
                    case 1:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(tan(rad+((3*PI)/4)-PI/2))))*-25);
                        break;
                    case 2:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(tan(rad+((3*PI)/4)))))*-25);
                        break;
                    case 3:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(tan(rad+((3*PI)/4)-PI/2))))*25);
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

            case 6:
                switch (i){
                    case 0:
                        double rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY;
                        break;
                    case 1:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(tan(rad+((3*PI)/4)-PI/2))))*-25);
                        break;
                    case 2:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY;
                        break;
                    case 3:
                        rad = Math.toRadians(cube[i].getAngler());
                        NewY = oldY+(((int)( Math.round(tan(rad+((3*PI)/4)-PI/2))))*-25);
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
        CubeCreator();
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
        this.x = this.x - x;
        for (int i = 0; i < getNumberOCubes(); i++) {
            if (cube[i] != null) {
                cube[i].setX(cube[i].getX() + x);
            }
        }
    }

    public void moveY(int x) {
        y = y - x;
        for (int i = 0; i < getNumberOCubes(); i++) {
            if (cube[i] != null) {
                cube[i].setY(cube[i].getY() - x);
            }
        }
    }

    public int getX() {
        return cube[1].getX();
    }

    public int RealX(){
        return this.x;
    }
    public int RealY(){
        return this.y;
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

    public Color getColor(){

        switch (Shape){
            //cube
            case 0:
                return Color.valueOf("#FFD500");
            case 1:
                return Color.valueOf("#800080");
            case 2:
                return Color.valueOf("#ff7f00");
            case 3:
                return Color.valueOf("#00ffff");
            case 4:
                return Color.valueOf("#00ff00");
            case 5:
                return Color.valueOf("#0000ff");
            case 6:
                return Color.valueOf("#ff0000");
            case 7:
                return Color.valueOf("#F91C97");

        }
        return Color.BLACK;
    }

    public void CreateCube(int xHold,int yHold,int BLength, int BHeight){
        for (int i=0;i<=5;i++) {
            //B L-R
            draw.line(xHold, yHold+i, xHold + BLength, yHold+i, getColor(), getColor());
            //L B-T
            draw.line(xHold+i, yHold, xHold+i, yHold+BHeight, getColor(), getColor());
            //R B-T
            draw.line(xHold+BLength-i, yHold, xHold+BLength-i, yHold+BHeight, getColor(), getColor());
            //T L-R
            draw.line(xHold, yHold+BHeight-i, xHold +BLength, yHold+BHeight-i, getColor(), getColor());
        }
        draw.setColor(Color.VIOLET);
        draw.setColor(Color.WHITE);
        draw.rect(xHold+5, yHold+5, BLength-10, BHeight-10);
        draw.setColor(Color.WHITE);
    }

    public void draw() {
        for (int i = 0; i < cube.length; i++) {
            try {
                if (cube[i] != null) {
                    if (Damage == true){
                        System.out.println("debug mode");
                    }
                    int xHold = cube[i].getX();
                    int yHold = cube[i].getY();
                    int BLength = cube[i].getLength();
                    int BHeight = cube[i].getHeight();
                    BHeight = (BHeight / 25)*30;
                    BLength = (BLength / 25)*30;
                    yHold = (yHold / 25)*30;
                    xHold = (xHold/25)*30;
                    draw.begin(ShapeRenderer.ShapeType.Filled);

                    //draw.rect(xHold, yHold, BLength, BHeight);
                    CreateCube(xHold, yHold, BLength, BHeight);
                    draw.end();
                }
            }
            catch (Exception e){
                System.err.println("render error");
            }
        }
    }

    public int getXHighest() {
        int hold = cube[0].getX();
        for (int i=0;i<getNumberOCubes();i++) {
            if (hold < cube[i].getX()){
                hold =cube[i].getX();
                int highestX = cube[i].getX();
            }
        }
        return hold;
    }
    public int getXLowest() {
        int hold = cube[0].getX();
        for (int i=0;i<getNumberOCubes();i++) {
            if (hold > cube[i].getX()){
                hold =cube[i].getX();
                int lowestX = cube[i].getX();
            }
        }
        return hold;
    }
    public int getYHighest() {
        int hold = cube[0].getY();
        for (int i=0;i<getNumberOCubes();i++) {
            if (hold < cube[i].getY()){
                hold =cube[i].getY();
                int highestY = cube[i].getY();
            }
        }
        return hold;
    }

    public int getYLowest() {
        int hold = cube[0].getY();
        for (int i=0;i<getNumberOCubes();i++) {
            if (hold > cube[i].getY()){
                hold =cube[i].getY();
                int lowestY = cube[i].getY();
            }
        }
        return hold;
    }
}
