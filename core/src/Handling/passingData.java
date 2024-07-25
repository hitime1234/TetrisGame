package Handling;
import Blocker.basicBlock;
import Blocker.board;
import com.mygdx.game.*;

import java.util.ArrayList;

public class passingData {
    public ArrayList data1;
    public board data2;

    //for the purposes of allowing data to be shared between threads and classes
    public passingData(ArrayList<basicBlock> hold1, board hold2){
        //sets data from Tetris game
        data1 = hold1;
        data2 = hold2;
    }

    public void setData1(ArrayList data1) {
        //sets data
        this.data1 = data1;
    }

    public ArrayList getData1() {
        //sets data
        return data1;
    }

    public board getData2() {
        //returns data if needed
        return data2;
    }
}

