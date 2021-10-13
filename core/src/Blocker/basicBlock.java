package Blocker;


public class basicBlock {
    int x;
    int y;
    int speed;
    public basicBlock(int x,int y,int speed){
        this.x = x;
        this.y = y;
        this.speed =speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void pass(){
        y = y - speed;
    }
}
