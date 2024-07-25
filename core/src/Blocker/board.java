package Blocker;


import Handling.BugFixThread;

import java.util.ArrayList;

public class board {
    public int[][] Boarder;

    public board(){
        //creates new array for board
        Boarder = new int[2000][2000];
        BuildArray();
    }
    public void BuildArray(){
        //initialises the array with all zeros
        for (int x=0;x<800;x++){
            for (int y=0;y<800;y++){
                Boarder[y][x] = 0;
            }
        }
        //bug fix for the bottom of the screen
        for (int x=0;x<800;x++){
            //adds a board at the bottom to prevent blocks clipping outside the boundary
                Boarder[25][x] = 1;
        }
    }


    public void Output(){
        //debug output for the tetris game board
        for (int x=0;x<600;x++){
            System.out.print("[");
            for (int y=20;y<800;y++){
                System.out.print(Boarder[y][x] + ",");
            }
            System.out.print("]");
            System.out.println();
        }
    }

    //adds/draws block to the board
    public void add(basicBlock block,ArrayList<basicBlock> Dump2){
        int Type = block.Shape;
        BasicCube[] hold = block.getCube();
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
            DrawTypeRectangle(block,Dump2);
    }

    public int[] CheckClear(){
        //check for if a row of blocks has filled a line
        int[] array = new int[50];
        int lineCleared = 0;
        int count = 0;
        int index = 0;
        //creates an array of lines that have been filled
        for (int y=1;y<30;y++) {
            count = 0;
            for (int i = 10; i < 30; i++) {
                if (Boarder[26 * y][25 * i] == 1) {
                    count++;
                }
            }
            if (count == 10) {
                array[index] = y*25;
                index++;
            }
        }
        return array;
    }


    public int[] CheckClearBlanks(){
        //check if there are any blank spots on the board prevent bugs
        int[] array = new int[50];
        int lineCleared = 0;
        int count = 0;
        int index = 0;
        int previous = 0;
        for (int y=1;y<30;y++) {
            count = 0;
            for (int i = 10; i < 20; i++) {
                if (Boarder[26 * y][25 * i] == 0) {
                    count++;
                }
            }


            if (previous == 10 && count < 10) {
                array[index] = y*25;
                index++;
            }
            previous = count;
        }
        return array;
    }


    public boolean Check(basicBlock SomethingDumb){
        //collision detection
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
                //checks for intersection
                if (Boarder[vector[0][1]][vector[0][0] + i] == 1) {
                    hold = true;
                } else if (Boarder[vector[2][1]][vector[2][0] + i] == 1) {
                    hold = true;
                }
            }


            //y axis
            for (int i = 0; i < height; i++) {
                //checks for intersection
                if (Boarder[vector[0][1] + i][vector[0][0] + 1] == 1) {
                    hold = true;
                }
                if (Boarder[vector[1][1] + i][vector[1][0] - 1] == 1) {
                    hold = true;
                }
            }

            //come back to this part of the code because false detection were happening

            /**
            if (hold1 && hold == false) {
                SomethingDumb.setY(SomethingDumb.getY() - 25);
            } else if (hold2 && hold == false) {
                SomethingDumb.setY(SomethingDumb.getY() + 25);
            }
             */
            //and casuing rebounces inside block
            //no IDEA what this is supposed to do
        }
        return hold;
    }

    //not in use
    public boolean CheckNoEdit(basicBlock SomethingDumb){
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
        }
        return hold;
    }



    public void ClearRow(int line,int newStart){
        //sets a number of lines to empty
            for (int i=0;i<800;i++){
                for (int x=newStart;x<((line*25)+newStart);x++) {
                    Boarder[x][i] = 0;
                }
            }
    }







    public void DrawTypeRectangle(basicBlock block,ArrayList Dump2){
        //bottom axis
        try {
            //uses the values from the cube to calculate the length and height and draws lines on 2d array
            for (int x = 0; x < block.getNumberOCubes(); x++) {
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
        } catch (ArrayIndexOutOfBoundsException e){
             //moves cube around to fix the error
             UnderFlow(Dump2);
             DrawTypeRectangle(block,Dump2);
        }

    }

    public void UnderFlow(ArrayList<basicBlock> DUMP){
        //rebuilds the board to fix the array and to successfully place a block.
        for (int i=0;i<DUMP.size();i++){
            for (int y=0;y<DUMP.get(i).cube.length;y++) {
                if (DUMP.get(i).cube[y] != null) {
                    if (DUMP.get(i).cube[y].getY() < 25) {
                        BuildArray();
                        for (int x = 0; x < DUMP.size(); x++) {
                            DUMP.get(x).moveY(-25);
                            DrawTypeRectangle(DUMP.get(x),DUMP);
                        }
                        i = DUMP.size() + 2;
                        break;
                    }
                }
            }
        }
    }


}


