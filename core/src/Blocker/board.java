package Blocker;

import com.mygdx.game.gameConstants;

import java.util.Vector;

public class board {
    public int[][] Boarder;
    public board(){
        Boarder = new int[gameConstants.screenWidth][gameConstants.screenHeight];
        BuildArray();
    }
    public void BuildArray(){
        for (int x=0;x<gameConstants.screenWidth;x++){
            for (int y=0;y<gameConstants.screenHeight;y++){
                Boarder[x][y] = 0;
            }
        }
    }

    public void Output(){
        for (int x=0;x<gameConstants.screenWidth;x++){
            System.out.print("[");
            for (int y=0;y<gameConstants.screenHeight;y++){
                System.out.print(Boarder[x][y] + ",");
            }
            System.out.print("]");
            System.out.println("");
        }
    }

    //Something Dumb is just a random variable name
    public void add(basicBlock SomethingDumb){
        int holdX = SomethingDumb.getY();
        int holdY = SomethingDumb.getX();
        int Type = SomethingDumb.Shape;
        int[][] vector = SomethingDumb.getVector();

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

    public boolean Check(basicBlock SomethingDumb){
        //bottom axis
        int length = SomethingDumb.getLength();
        int height = SomethingDumb.getHeight();
        int vector[][] = SomethingDumb.getVector();
        boolean hold = false;
        //x axis
        for (int i =0;i<length;i++){
            if (Boarder[vector[0][1]][vector[0][0]+i] == 1){
                hold = true;
            }
            else if(Boarder[vector[2][1]][vector[2][0]+i] == 1){
                hold = true;
            }
        }

        //y axis
        for (int i =0;i<height;i++){
            if (Boarder[vector[0][1]+i][vector[0][0]] == 1){hold = true;}
            if (Boarder[vector[1][1]+i][vector[1][0]] == 1){hold = true;}
        }
        return hold;
    }

    public void DrawTypeRectangle(basicBlock hold){
        //bottom axis
        int length = hold.getLength();
        int height = hold.getHeight();
        int vector[][] = hold.getVector();
        //x axis
        for (int i =0;i<length;i++){
            Boarder[vector[0][1]][vector[0][0]+i] = 1;
            Boarder[vector[2][1]][vector[2][0]+i] = 1;
        }

        //y axis
        for (int i =0;i<height;i++){
            Boarder[vector[0][1]+i][vector[0][0]] = 1;
            Boarder[vector[1][1]+i][vector[1][0]] = 1;
        }
    }


}

