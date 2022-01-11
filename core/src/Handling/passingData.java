package Handling;
import Blocker.basicBlock;
import Blocker.board;
import com.mygdx.game.*;

import java.util.ArrayList;

public class passingData {
    public ArrayList data1;
    public board data2;
    public passingData(ArrayList<basicBlock> hold1, board hold2){
        data1 = hold1;
        data2 = hold2;
    }

    public void setData1(ArrayList data1) {
        this.data1 = data1;
    }

    public ArrayList getData1() {
        return data1;
    }

    public board getData2() {
        return data2;
    }
}

