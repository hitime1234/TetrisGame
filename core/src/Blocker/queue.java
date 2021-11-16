package Blocker;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class queue {
    private RNGblock Builder;
    private List<basicBlock> objects = new ArrayList<>();

    public queue(ShapeRenderer Drawing,int speed) {
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            //rand.nextInt(0)
            RNGblock hold = new RNGblock(Drawing, 4, speed);
            basicBlock BUILT = hold.getHold();
            objects.add(BUILT);
        }
    }


    public void addQueue(basicBlock ob){
        objects.add(ob);
    }
    public basicBlock DeQueue(){
        basicBlock hold = objects.get(objects.size()-1);
        objects.remove(objects.size()-1);
        return hold;
    }
}
