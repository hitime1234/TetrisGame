package Blocker;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class queue {
    private RNGblock Builder;
    private List<basicBlock> objects = new ArrayList<>();
    private List<basicBlock> holdBlock = new ArrayList<>();
    private List<basicBlock> BAG = new ArrayList<>();
    public Random rand;
    private int Type =1;
    private int index = 0;
    private int GenIndex =0;


    public int ClassicBlockGeneration(ShapeRenderer Drawing,int speed){
        int block = 0;
        //This uses the bag randomizer method
        //were a bag is picked in order then randomized
        BAG = new ArrayList<>();
        //something goes here
        //https://stackoverflow.com/questions/29551450/tetris-shape-generation-algorithm-in-java/
        //https://harddrop.com/forums/index.php?showtopic=1925
        for (int i=0;i<7;i++) {
            RNGblock hold = new RNGblock(Drawing, i, speed);
            basicBlock BUILT = hold.getHold();
            BAG.add(BUILT);
        }

        Collections.shuffle(BAG);
        for (int i=0;i<BAG.size();i++) {
            objects.add(BAG.get(i));
        }
        return 1;
    }


    public queue(ShapeRenderer Drawing,int speed,int Type) {
        this.Type = Type;

        rand = new Random();
        index = 0;
        switch (this.Type) {
            case 1:
                //does it twice to make sure there is enough to cover the board for init
                ClassicBlockGeneration(Drawing,speed);
                ClassicBlockGeneration(Drawing,speed);
                break;
            case 0:
                for (int i = 0; i < 20; i++) {
                //PURE Random using basic seed random number generation
                //this is simpler method
                //rand.nextInt(0)
                RNGblock hold = new RNGblock(Drawing, rand.nextInt(6), speed);
                //RNGblock hold =new RNGblock(Drawing, 4, speed);
                basicBlock BUILT = hold.getHold();
                objects.add(BUILT);
                }

            break;

        }
    }

    public void generate(ShapeRenderer Drawing,int speed){
        for (int i = 0; i < 20; i++) {
            //rand.nextInt(0)
            RNGblock hold = new RNGblock(Drawing, rand.nextInt(6), speed);
            //RNGblock hold =new RNGblock(Drawing, 4, speed);
            basicBlock BUILT = hold.getHold();
            objects.add(i,BUILT);
        }
    }

    public basicBlock holdBlockSwap(basicBlock oldBlock,ShapeRenderer Drawing,int speed){
        if (holdBlock.size() == 0){
            oldBlock.setY(500);
            oldBlock.setX((300));
            holdBlock.add(oldBlock);
            return DeQueue(Drawing, speed);
        }
        else{
            basicBlock Temp = holdBlock.get(0);
            holdBlock.remove(0);
            oldBlock.setY(500);
            oldBlock.setX(300);
            holdBlock.add(oldBlock);
            return Temp;
        }
    }

    public basicBlock getHold(){
        try {
            return holdBlock.get(0);
        }catch(Exception e){}
        return null;
    }


    public void addQueue(basicBlock ob){
        objects.add(ob);
    }
    public basicBlock DeQueue(ShapeRenderer Drawing,int speed){
        basicBlock hold = objects.get(index);
        objects.remove(index);
        switch (Type) {
            case 1:
                if (GenIndex > 5){
                    ClassicBlockGeneration(Drawing,speed);
                    GenIndex =0;
                }
                GenIndex++;
                index++;
                break;
            case 0:
                RNGblock newBlock = new RNGblock(Drawing, rand.nextInt(6), speed);
                basicBlock BUILT = newBlock.getHold();
                objects.add(index, BUILT);
                index++;
                GenIndex++;
                if (GenIndex > 19) {
                    generate(Drawing, speed);
                    GenIndex =0;
                }
                break;
        }
        return hold;
    }
}
