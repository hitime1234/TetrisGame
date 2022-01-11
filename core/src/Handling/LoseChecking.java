package Handling;

import Blocker.basicBlock;

import java.util.ArrayList;

public class LoseChecking extends Thread{
    public ArrayList<basicBlock> BlockArray;
    public boolean Lose = false;
    public LoseChecking (ArrayList<basicBlock> BlockList){
        BlockArray = BlockList;
    }

    @Override
    public void run() {
        LoseCheck();
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

    public void LoseCheck(){
        for (int i=0;i<BlockArray.size();i++){
            for (int x =0;x<4;x++) {
                if(BlockArray.get(i) != null){
                    if (BlockArray.get(i).cube[x] != null) {
                        if (BlockArray.get(i).cube[x].getY() >= 525) {
                            Lose = true;
                        }
                        }
                }
            }
        }
    }





}
