package Blocker;

import com.mygdx.game.gameConstants;

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

    public int CheckClear(){
        int lineCleared = 0;
        int count = 0;
        for (int i=4;i<24;i++) {
            if (Boarder[25][25*i] == 1){
                count++;
            }
        }
        System.out.println(count);
        if (count == 20){
            return 25;
        }
        return lineCleared;
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
                for (int x=0;x<=line*2+25;x++) {
                    Boarder[x][i] = 0;
                }
            }
    }

    public void DrawTypeRectangle(basicBlock block){
        //bottom axis
        for (int x=0;x<block.getNumberOCubes();x++) {
            BasicCube[] holder = block.getCube();
            int holdX = holder[x].getX();
            int holdY = holder[x].getY();
            int length = holder[x].getLength();
            int height = holder[x].getLength();
            int[][] vector = holder[x].getVector();
            //x axis



            for (int i = 0; i < length-1; i++) {
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


