package Blocker;

import java.util.ArrayList;
import java.util.List;

public class queue {
    private List<Object> objects = new ArrayList<>();

    public  queue(Object array[]){
        for (int i=0;i<array.length;i++){
            objects.add(array[array.length-i]);
        }
    }

    public void addQueue(Object ob){
        objects.add(ob);
    }
    public Object DeQueue(){
        Object hold = objects.get(objects.size());
        objects.remove(objects.size());
        return hold;
    }
}
