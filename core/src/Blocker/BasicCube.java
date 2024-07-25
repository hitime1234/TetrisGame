package Blocker;

public class BasicCube {
    private int length = 25;
    private int height = 25;
    private int x = 0;
    private int y = 0;
    private final int[][] Vector;
    private int angler;
    public int rotationX = 25;
    public int rotationY = 25;
    public int eX = 0;
    public int setY = 0;

    public BasicCube(int x, int y, int length, int height, int[][] Vector){
        //constructor for cube
        this.y = y;
        this.x = x;
        this.length = length;
        this.height = height;
        this.Vector = Vector;
        this.angler = angler;
    }

    public int[][] getVector() {
        //returns the cubes vectors
        //X axis
        Vector[0][0] = x;
        //Y axis
        Vector[0][1] = y;

        //[1,0]
        Vector[1][0] = x + length;
        Vector[1][1] = y;

        //[0,1]
        Vector[2][0] = x;
        Vector[2][1] = y + height;

        //[1,1]
        Vector[3][0] = x + length;
        Vector[3][1] = y + height;
        return Vector;
    }


    public int getLength() {
        //returns the length of the cube
        return length;
    }

    public void setLength(int length) {
        //sets the length of the cube
        this.length = length;
    }

    public void setAngler(int angler) {
        //set angle for the cube
        this.angler = angler;
        if (angler==360){
            this.angler = 0;
        }
    }

    public int getAngler() {
        //return the angle of the cube
        return angler;
    }

    public int getHeight() {
        //return the height of the cube
        return height;
    }

    public void setHeight(int height) {
        //set the height of the cube
        this.height = height;
    }

    public int getX() {
        //return the x value of the cube
        return x;
    }


    public void PassAngle(){
        //if the value is at 360 meaning the cube has completely loop around it reset it back to 0
        if (this.angler ==360){
            angler = 0;
        }
        else{
            //rotates cube by 90 degrees
            this.angler = this.angler + 90;
        }
    }


    public void setX(int x) {
        //set the x value
        this.x = x;
    }

    public int getY() {
        //return the y value
        return y;
    }

    public void setY(int y) {
        //set the y value
        this.y = y;
    }
}
