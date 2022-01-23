package Handling;

import Blocker.basicBlock;
import Blocker.board;
import com.mygdx.game.*;

import javax.sound.sampled.Line;
import java.util.ArrayList;

public class BugFixThread extends Thread{

    board DeadBlock;
    ArrayList<basicBlock> DUMP;
    passingData data;
    boolean run = true;

    public BugFixThread(passingData dataThroughPut){
        DeadBlock = dataThroughPut.data2;
        DUMP = dataThroughPut.data1;
        data = dataThroughPut;
    }

    public void running(){
        run = false;
    }


    @Override
    public void run() {
            lineChecksBlanks();
            UnderFlow();
            applyValues();
    }

    public int logicalArrayLength(basicBlock[] passedArray) {
        int count = 0;
        for (int i = 0; i < passedArray.length; i++) {
            if (passedArray[i] != null) {
                count++;
            }
        }
        return count;
    }
    public int IntLogicalArrayLength(int[] passedArray) {
        int count = 0;
        for (int i = 0; i < passedArray.length; i++) {
            if (passedArray[i] != 0) {
                count++;
            }
        }
        return count;
    }

    public void applyValues(){
        data.setData1(DUMP);
        data.data2 = (DeadBlock);
    }

    public void lineChecksBlanks(){
        int[] hold= DeadBlock.CheckClearBlanks();
        int LinesCleared = IntLogicalArrayLength(hold);
        int DUMPsIZE = DUMP.size();
        if (LinesCleared != 0) {
            DeadBlock.BuildArray();
            for (int i = 0; i < DUMPsIZE; i++) {
                DUMP.get(i).dropY(25, hold[0]);
                DeadBlock.DrawTypeRectangle(DUMP.get(i));
            }
        }
    }
    public void UnderFlow(){
        for (int i=0;i<DUMP.size();i++){
            for (int y=0;y<DUMP.get(i).cube.length;y++) {
                if (DUMP.get(i).cube[y] != null) {
                    if (DUMP.get(i).cube[y].getY() < 25) {
                        DeadBlock.BuildArray();
                        for (int x = 0; x < DUMP.size(); x++) {
                            DUMP.get(x).moveY(-25);
                            DeadBlock.DrawTypeRectangle(DUMP.get(x));
                        }
                        i = DUMP.size() + 2;
                        break;
                    }
                }
            }
        }
    }
}
