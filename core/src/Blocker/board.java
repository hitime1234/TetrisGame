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
        for (int i=0;i< vector.length;i++){
            //DrawX(vector[i][0],holdX,holdY);
            //DrawY(vector[i][1],holdX,holdY);
        }
    }


    //broke Code
    /**
    public void DrawX(int start,int end,int Height){
        System.out.println(start + " "+ end + " "+Height);
        int loop;
        System.out.println(end);
        if ((start-end) <0){
            loop = end-start;
            for (int i=0;i<loop;i++){
                Boarder[Height][end-1] = 1;
            }
        }
        else{
            loop = start -end;
            for (int i=0;i<loop;i++){
                Boarder[Height][end+i] = 1;
            }
        }

    }
    public void DrawY(int start,int end,int place){
        System.out.println(start + " "+ end + " "+place);
        int loop;
        if ((start-end) <0){
            loop = end-start;
            for (int i=0;i<loop;i++){
                Boarder[end-i][place] = 1;
            }

        }
        else{
            loop = start -end;
            for (int i=0;i<loop;i++){
                Boarder[end+i][place] = 1;
            }
        }

    }
    */
}

