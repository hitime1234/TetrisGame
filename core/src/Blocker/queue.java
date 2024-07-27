package Blocker;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class queue {
    //values for class
    private RNGblock Builder;
    private final List<basicBlock> objects = new ArrayList<>();
    private final List<basicBlock> holdBlock = new ArrayList<>();
    private List<basicBlock> BAG = new ArrayList<>();
    public Random rand;
    private int Type =1;
    private int index = 0;
    private int GenIndex =0;

    //gets an index block from the queue
    public basicBlock getObjects(int i) {
        return objects.get(i);
    }

    //gets the size of the queue
    public int getIndex() {
        return index;
    }


    //classic mode works by using a randomly sorted bucket to determine blocks
    public int ClassicBlockGeneration(ShapeRenderer Drawing, int speed){
        int block = 0;
        //This uses the bag randomizer method
        //were a bag is picked in order then randomized
        BAG = new ArrayList<>();
        //something goes here
        //https://stackoverflow.com/questions/29551450/tetris-shape-generation-algorithm-in-java/
        //https://harddrop.com/forums/index.php?showtopic=1925
        /*
            WTF IS THIS -Ren
         */
            for (int x = 0; x < 7; x++) {
                RNGblock hold = new RNGblock(Drawing, x, speed);
                basicBlock BUILT = hold.getHold();
                BAG.add(BUILT);
            }


            Collections.shuffle(BAG);
            for (int y = 0; y < BAG.size(); y++) {
                objects.add(BAG.get(y));
            }

        System.out.println("Size:" + (objects.size()-index));
        return 1;
    }


    //creates queue/constructor
    public queue(ShapeRenderer Drawing,int speed,int Type) {
        this.Type = Type;
        rand = new Random();
        index = 0;
        //this is based on the choice made in the game launcher
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
            //normal random generated blocks
            //rand.nextInt(0)
            RNGblock hold = new RNGblock(Drawing, rand.nextInt(6), speed);
            //RNGblock hold =new RNGblock(Drawing, 4, speed);
            basicBlock BUILT = hold.getHold();
            objects.add(BUILT);
        }
    }

    public basicBlock holdBlockSwap(basicBlock oldBlock,ShapeRenderer Drawing,int speed){
        //Swap blocks to allow the block to be held in a separate array
        if (holdBlock.size() == 0){
            //for it empty the block would just place into the array and removing a block of the queue
            oldBlock.setY(500);
            oldBlock.setX((300));
            holdBlock.add(oldBlock);
            return DeQueue(Drawing, speed);
        }
        else{
            //this requires swapping the blocks
            basicBlock Temp = holdBlock.get(0);
            holdBlock.remove(0);
            oldBlock.setY(500);
            oldBlock.setX(300);
            holdBlock.add(oldBlock);
            return Temp;
        }
    }

    public basicBlock getHold(){
        //gets the current block being held
        try {
            return holdBlock.get(0);
        }catch(Exception e){}
        return null;
    }


    public void addQueue(basicBlock ob){
        //adds block
        objects.add(ob);
    }
    public basicBlock DeQueue(ShapeRenderer Drawing,int speed){
        //remove block from queue
        basicBlock hold = objects.get(index);
        objects.remove(index);
        switch (Type) {
            //if queue is low new blocks are generated using methods
            case 1:
                if (GenIndex > 3){
                    ClassicBlockGeneration(Drawing,speed);
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
                if (GenIndex > 14) {
                    generate(Drawing, speed);
                    generate(Drawing, speed);
                    GenIndex =0;
                }
                break;
        }
        return hold;
    }
}
