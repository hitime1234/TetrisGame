package Blocker;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Vector;

public class RNGblock {
    private basicBlock hold;
    public int x = 300;
    public int y = 500;
    public int speed;

    public RNGblock(ShapeRenderer drawing, int nextInt, int speed) {
        //Literally here to do one thing. BUILD

        hold = Create(drawing,nextInt,speed);
    }

    public basicBlock getHold() {
        return hold;
    }

    public basicBlock Create(ShapeRenderer draw, int num, int speed) {
        basicBlock temp;
        this.speed = speed;
        switch (num){
            case 0:
                temp = Line(draw);
                break;
            case 1:
                temp = HitTheL(draw);
            case 2:
                temp = BOX(draw);
            case 3:
                temp = TMan(draw);
            case 4:
                temp = diag(draw);
            default:
                temp = Line(draw);
        }
        return temp;
    }


    private basicBlock diag(ShapeRenderer draw) {
        int Vector[][] = new int[2][2];
        return new basicBlock(draw,x,y,speed, Vector,4);
    }

    private basicBlock TMan(ShapeRenderer draw) {
        int Vector[][] = new int[2][2];
        return new basicBlock(draw,x,y,speed,Vector,1);

    }

    private basicBlock BOX(ShapeRenderer draw) {
        //this one Very easy to make
        /**
         *  x x
         *  x x
         *  [0,0],[1,0],[0,1],[1,1]
         */
        //Warning memes about vectors below
        //Oh yeah 2d Vector array Initialised
        int Vector[][] = new int[2][2];
        //NOW FOR THE VECTOR CUBE!!!
        //Both magnitude AND DIRECTION AHAHAHAHAHAHHAHAH
        //[0,0]
        Vector[0][0] = x; Vector[0][1] = y;
        //[0,1]
        Vector[1][0] = x+50; Vector[1][1] = y;
        //[1,0]
        Vector[1][0] = x; Vector[1][1] = y+50;
        //[1,1]
        Vector[1][0] = x+50; Vector[1][1] = y+50;
        //o YEAH VECTOR CUBE NOW Has Stuff
        return new basicBlock(draw,x,y,speed,Vector,0);

    }

    private basicBlock HitTheL(ShapeRenderer draw) {
        int Vector[][] = new int[2][2];
        return new basicBlock(draw,x,y,speed, Vector,2);
    }

    private basicBlock Line(ShapeRenderer draw) {
        int Vector[][] = new int[2][2];
        return new basicBlock(draw,x,y,speed, Vector,3);


    }
}
