package Blocker;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class queue {
    private RNGblock Builder;
    private List<basicBlock> objects = new ArrayList<>();
    private List<basicBlock> holdBlock = new ArrayList<>();
    public Random rand;
    private int index = 0;


    public queue(ShapeRenderer Drawing,int speed) {
        rand = new Random();
        index = 0;
        for (int i = 0; i < 20; i++) {
            //rand.nextInt(0)
            RNGblock hold = new RNGblock(Drawing, rand.nextInt(6), speed);
            //RNGblock hold =new RNGblock(Drawing, 4, speed);
            basicBlock BUILT = hold.getHold();
            objects.add(i,BUILT);
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
            return DeQueue(Drawing,speed);
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
        RNGblock newBlock= new RNGblock(Drawing, rand.nextInt(6), speed);
        basicBlock BUILT = newBlock.getHold();
        objects.add(index,BUILT);
        index++;
        if (index > 19){
            generate(Drawing,speed);
        }
        return hold;
    }
}
