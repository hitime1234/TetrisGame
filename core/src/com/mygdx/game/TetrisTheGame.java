package com.mygdx.game;

import Blocker.BasicCube;
import Blocker.basicBlock;
import Blocker.board;
import Blocker.queue;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.awt.*;

import static com.mygdx.game.gameConstants.*;

public class TetrisTheGame implements Screen {
    private final Stage stage;
    private final queue queuing;
    private final board DeadBlock;
    private final MyGdxGame game;
    private int index;
    private basicBlock hold;
    private ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture img;
    private OrthographicCamera camera;
    private Texture bucketImage;
    private int speed = 1;
    private int OSpeed = 1;
    private int randomV = 0;
    private float y;
    private int score =0;
    private float x;
    private float width;
    private int dumpSize = 0;
    private basicBlock swap = null;
    private float height;
    private int Sleep =0;
    private basicBlock[] DUMP;
    private basicBlock tempBlock;
    private int DropTime = 50;
    boolean DropDown = false;
    private int FrameCount =0;
    private long pastTime = System.currentTimeMillis();
    private long currentTime = System.currentTimeMillis();
    public int fps = 0;
    public TetrisTheGame(MyGdxGame game,int speedInt,int randomValue) {
        speed = speedInt;
        OSpeed = speedInt;
        randomV =randomValue;
        camera = new OrthographicCamera();
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        stage = new Stage();
        this.game = game;
        camera.setToOrtho(false, 1280, 720);
        camera.update();
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        height = 50;
        width = 20;
        x= 300;
        y=300;
        DeadBlock = new board();
        //ABOSLOUTE DUMPTRUCK
        DUMP = new basicBlock[100];
        queuing = new queue(shapeRenderer,25);
        hold = queuing.DeQueue();
        index = 0;
    }


    @Override
    public void show() {

    }

    public void fpsCounter(SpriteBatch batch){
        FrameCount++;
        //fps counter
        currentTime = System.currentTimeMillis();
        if (currentTime >= (pastTime + 1000)){
            pastTime = System.currentTimeMillis();
            fps  = FrameCount;
            FrameCount = 0;
            BitmapFont font = new BitmapFont(); //or use alex answer to use custom font

            font.draw(batch, Integer.toString(FrameCount), 10, 10);
        }
        else{
            BitmapFont font = new BitmapFont(); //or use alex answer to use custom font

            font.draw(batch, Integer.toString(fps), 10, 10);
        }

        //fps counter
    }

    private void BLOCKER(){
            for (int i = 0; i < logicalArrayLength(DUMP); i++) {
                try {
                    DUMP[i].draw();
                }
                catch(Exception e){

                }

        }
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
    public int IntLogicalArrayLength(int[] passedArray) {
        int count = 0;
        for (int i = 0; i < passedArray.length; i++) {
            if (passedArray[i] != 0) {
                count++;
            }
        }
        return count;
    }

    private void Clear(int ClearPoint) {
        int count = 0;
        dumpSize = logicalArrayLength(DUMP);
        for (int i = 0; i < dumpSize; i++) {
            for (int x=0;x<4;x++) {
                try {
                    //boolean Result = DUMP[i].RemoveCube(ClearPoint);
                    if (DUMP[i].cube[x] != null) {
                        if (DUMP[i].cube[x].getY() == ClearPoint) {
                            DUMP[i].cube[x] = null;
                            count++;
                        }
                    }

                } catch (Exception e) {

                }
            }

            score = score + 100;
        }
    }

    public void LowerBoard(int lineCleared,int newStart){
        if (lineCleared != 0) {
            DeadBlock.BuildArray();
            for (int i = 0; i < dumpSize; i++) {
                DUMP[i].dropY(25 * lineCleared,newStart);
                DeadBlock.DrawTypeRectangle(DUMP[i]);
            }

        }
    }

    public void lineChecks(){
        int[] hold= DeadBlock.CheckClear();
        int LinesCleared = IntLogicalArrayLength(hold);
        for (int i=0;i<LinesCleared;i++) {
            if (hold[i]!= 0) {
                Clear(hold[i]);
            }
        }
        LowerBoard(LinesCleared,hold[0]);

    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        //inputs
        Sleep = Sleep - 100;
        DropTime = DropTime - speed;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Sleep < 0 && hold.getX() <=550){
            x = x +30;
            hold.moveX(25);
            Sleep = 600;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Sleep < 0 && hold.getX() >=125){
            x = x -30;
            hold.moveX(-25);
            Sleep = 600;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            speed = OSpeed + 20;
        }
        else{
            speed = OSpeed;
        }

        /**
        if (Gdx.input.isKeyPressed(Input.Keys.C)){
            if (swap != null){
                hold = swap;
                System.out.println("hold");
            }
            else {
                swap = hold;
                hold = queuing.DeQueue();
            }
        }
*/
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && Sleep <= 0){
            hold.flip();
            Sleep = 900;
        }



        //the dropping staging
        if (DropTime <= 0 && DropDown == false) {
            hold.pass();
            DropTime = 50;
        }

        hold.draw();

        if (DeadBlock.Check(hold) && DropTime > 0){
            DropDown = true;
        }
        else if (DeadBlock.Check(hold)){
            DropDown = false;
            DUMP[index] = hold;
            DeadBlock.add(hold);
            lineChecks();
            //DeadBlock.Output();
            hold = queuing.DeQueue();
            index++;
        }
        else{
            DropDown = false;
        }




        if (hold.GetLowestY() ==25 && DropTime <= 0){
            DUMP[index] = hold;
            DeadBlock.add(hold);
            //DeadBlock.Output();
            System.out.println(DeadBlock.CheckClear());
            lineChecks();

            hold = queuing.DeQueue();
            index++;
        }

        else if (hold.GetLowestY() < 25){
            hold.setY(25);
            hold.draw();
            DUMP[index] = hold;
            DeadBlock.add(hold);
            lineChecks();

            //DeadBlock.Output();
            System.out.println(DeadBlock.CheckClear());
            hold = queuing.DeQueue();
            index++;
        }

        BLOCKER();




        //shapeRenderer = new  ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (y > 0) {
            y = y - speed;
        }
        else{
            //new shape
            shapeRenderer.rect(x,300,width,height);

        }

        for (int i=1;i<=18;i++){
            shapeRenderer.rect(100,25*i,500,1);
        }
        for (int i=4;i<=24;i++) {
            shapeRenderer.rect(25*i, 450, 1, -425);
        }

        /**
        shapeRenderer.rect(x,y,width,height);
         */

        shapeRenderer.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        fpsCounter(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        img.dispose();

    }
}
