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
                break;
            case 2:
                temp = BOX(draw);
                break;
            case 3:
                temp = TMan(draw);
                break;
            case 4:
                temp = diagR(draw);
                break;
            case 5:
                temp = HitTheJ(draw);
                break;

            case 6:
                temp = diagL(draw);
                break;
            default:
                temp = Line(draw);
                break;

        }
        return temp;
    }

    private basicBlock HitTheJ(ShapeRenderer draw) {
        int Vector[][] = new int[8][2];
        return new basicBlock(draw,x,y,speed, Vector,5);
    }


    private basicBlock diagR(ShapeRenderer draw) {
        int Vector[][] = new int[8][2];
        return new basicBlock(draw,x,y,speed, Vector,4);
    }

    private basicBlock diagL(ShapeRenderer draw) {
        int Vector[][] = new int[8][2];
        return new basicBlock(draw,x,y,speed, Vector,6);
    }

    private basicBlock TMan(ShapeRenderer draw) {
        int Vector[][] = new int[8][2];
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
        int Vector[][] = new int[8][4];
        //[0,0]
        //X axis
        Vector[0][0] = x;
        //Y axis
        Vector[0][1] = y;

        //[1,0]
        Vector[1][0] = x+50; Vector[1][1] = y;

        //[0,1]
        Vector[2][0] = x; Vector[2][1] = y+50;

        //[1,1]
        Vector[3][0] = x+50; Vector[3][1] = y+50;

        return new basicBlock(draw,x,y,speed,Vector,0);

    }

    private basicBlock HitTheL(ShapeRenderer draw) {
        int Vector[][] = new int[8][4];
        return new basicBlock(draw,x,y,speed, Vector,2);
    }

    private basicBlock Line(ShapeRenderer draw) {
        int Vector[][] = new int[8][4];
        //[0,0]
        //X axis
        Vector[0][0] = x;
        //Y axis
        Vector[0][1] = y;

        //[1,0]
        Vector[1][0] = x+10; Vector[1][1] = y;

        //[0,1]
        Vector[2][0] = x; Vector[2][1] = y+50;

        //[1,1]
        Vector[3][0] = x+((25+1)/2); Vector[3][1] = y+50;

        return new basicBlock(draw,x,y,speed, Vector,3);


    }
}
