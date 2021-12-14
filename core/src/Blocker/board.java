package Blocker;

import com.mygdx.game.gameConstants;
import org.graalvm.compiler.lir.LIRInstruction;

import javax.sound.sampled.Line;
import java.util.Vector;

public class board {
    public int[][] Boarder;
    public board(){
        Boarder = new int[800][800];
        BuildArray();
    }
    public void BuildArray(){
        for (int x=0;x<800;x++){
            for (int y=0;y<800;y++){
                Boarder[y][x] = 0;
            }
        }
        //bug fix for the bottom of the screen
        for (int x=0;x<800;x++){
                Boarder[25][x] = 1;
        }
    }

    public void Output(){
        for (int x=0;x<Boarder.length;x++){
            System.out.print("[");
            for (int y=0;y<Boarder.length;y++){
                System.out.print(Boarder[y][x] + ",");
            }
            System.out.print("]");
            System.out.println("");
        }
    }

    //Something Dumb is just a random variable name
    public void add(basicBlock SomethingDumb){
        int Type = SomethingDumb.Shape;
        BasicCube[] hold = SomethingDumb.getCube();
            /**
             * new approached required
             * For example
             * there are SET shapes in tetris this good
             * meaning we can use a vector length calculator class
             */
            //for (int i=0;i< vector.length;i++){
            //DrawX(vector[i][0],holdX,holdY);
            //DrawY(vector[i][1],holdX,holdY);

            //}
            DrawTypeRectangle(SomethingDumb);
    }

    public int[] CheckClear(){
        int array[] = new int[50];
        int lineCleared = 0;
        int count = 0;
        int index = 0;
        for (int y=1;y<30;y++) {
            count = 0;
            for (int i = 4; i < 24; i++) {
                if (Boarder[26 * y][25 * i] == 1) {
                    count++;
                }
            }
            if (count == 20) {
                array[index] = y*25;
                index++;
            }
        }
        return array;
    }


    public boolean Check(basicBlock SomethingDumb){
        //bottom axis
        int Type = SomethingDumb.Shape;
        boolean hold = false;
        boolean hold1 = false;
        boolean hold2 = false;
        BasicCube[] holder = SomethingDumb.getCube();
        for (int x=0;x<SomethingDumb.getNumberOCubes();x++) {

            int holdX = holder[x].getX();
            int holdY = holder[x].getY();
            int length = holder[x].getLength();
            int height = holder[x].getLength();
            int[][] vector = holder[x].getVector();



            //x axis

            for (int i = 0; i < length; i++) {
                if (Boarder[vector[0][1]][vector[0][0] + i] == 1) {
                    hold1 = true;
                } else if (Boarder[vector[2][1]][vector[2][0] + i] == 1) {
                    hold2 = true;
                }
            }


            //y axis
            for (int i = 0; i < height; i++) {
                if (Boarder[vector[0][1] + i][vector[0][0] + 1] == 1) {
                    hold = true;
                }
                if (Boarder[vector[1][1] + i][vector[1][0] - 1] == 1) {
                    hold = true;
                }
            }


            if (hold1 && hold == false) {
                SomethingDumb.setX(SomethingDumb.getX() - 25);
            } else if (hold2 && hold == false) {
                SomethingDumb.setX(SomethingDumb.getX() + 25);
            }
        }
        return hold;
    }

    public void ClearRow(int line){
            for (int i=0;i<800;i++){
                for (int x=26;x<line+26;x++) {
                    Boarder[x][i] = 0;
                }
            }
    }

    public void DropBoard(int LineCleared,int newStart){
        int[][] TempBoard = new int[800][800];
        Output();
        for (int i =(newStart); i<800-(newStart); i++){
            for (int j=0;j<800;j++) {
                    TempBoard[(i)][j] = Boarder[i+newStart][j];
            }
        }
        for (int i=LineCleared;i<800;i++){
                TempBoard[25][i] = 1;
        }
        Boarder = TempBoard;
    }




    public void DrawTypeRectangle(basicBlock block){
        //bottom axis
        for (int x=0;x<block.getNumberOCubes();x++) {
            BasicCube[] holder = block.getCube();
            if (holder[x] != null) {
                int holdX = holder[x].getX();
                int holdY = holder[x].getY();
                int length = holder[x].getLength();
                int height = holder[x].getLength();
                int[][] vector = holder[x].getVector();
                //x axis


                for (int i = 0; i < length - 1; i++) {
                    Boarder[vector[0][1]][vector[0][0] + i] = 1;
                    Boarder[vector[2][1]][vector[2][0] + i] = 1;
                }


                //y axis
                for (int i = 0; i <= height; i++) {
                    Boarder[vector[0][1] + i][vector[0][0]] = 1;
                    Boarder[vector[1][1] + i][vector[1][0] - 1] = 1;
                }

            }
        }

    }


}


