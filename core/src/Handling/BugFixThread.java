package Handling;

import Blocker.basicBlock;
import Blocker.board;
import com.mygdx.game.*;

import javax.sound.sampled.Line;
import java.util.ArrayList;

public class BugFixThread extends Thread{

    //global board
    board DeadBlock;
    //global dump to be used by multiple methods
    ArrayList<basicBlock> DUMP;
    //gets data from TETRISGAME using passingDATA
    passingData data;
    //thread run status
    boolean run = true;

    //initialises data
    public BugFixThread(passingData dataThroughPut){
        //gets data from class
        DeadBlock = dataThroughPut.data2;
        DUMP = dataThroughPut.data1;
        data = dataThroughPut;
    }

    public void running(){
        run = false;
    }


    @Override
    public void run() {
        //checks if there are any errors
            lineChecksBlanks();
            //fixes underflow errors in the board
            UnderFlow();
            //updates board of the tetris game
            applyValues();
    }

    public int logicalArrayLength(basicBlock[] passedArray) {
        //gets the length of the array passed into it
        int count = 0;
        for (int i = 0; i < passedArray.length; i++) {
            if (passedArray[i] != null) {
                count++;
            }
        }
        return count;
    }
    public int IntLogicalArrayLength(int[] passedArray) {
        //gets the length of the array passed into it for integers array
        int count = 0;
        for (int i = 0; i < passedArray.length; i++) {
            if (passedArray[i] != 0) {
                count++;
            }
        }
        return count;
    }

    public void applyValues(){
        //updates Tetris games variables
        data.setData1(DUMP);
        data.data2 = (DeadBlock);
    }

    public void lineChecksBlanks(){
        //checks if there is a blank row and rebuilds the board
        int[] hold= DeadBlock.CheckClearBlanks();
        int LinesCleared = IntLogicalArrayLength(hold);
        int DUMPsIZE = DUMP.size();
        if (LinesCleared != 0) {
            DeadBlock.BuildArray();
            for (int i = 0; i < DUMPsIZE; i++) {
                DUMP.get(i).dropY(25, hold[0]);
                DeadBlock.DrawTypeRectangle(DUMP.get(i),DUMP);
            }
        }
    }
    public void UnderFlow(){
        //fixes errors on the board array and rebuilds it correctly
        for (int i=0;i<DUMP.size();i++){
            for (int y=0;y<DUMP.get(i).cube.length;y++) {
                if (DUMP.get(i).cube[y] != null) {
                    if (DUMP.get(i).cube[y].getY() < 25) {
                        DeadBlock.BuildArray();
                        for (int x = 0; x < DUMP.size(); x++) {
                            DUMP.get(x).moveY(-25);
                            DeadBlock.DrawTypeRectangle(DUMP.get(x),DUMP);
                        }
                        i = DUMP.size() + 2;
                        break;
                    }
                }
            }
        }
    }
}
