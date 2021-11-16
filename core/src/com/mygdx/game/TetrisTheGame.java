package com.mygdx.game;

import Blocker.basicBlock;
import Blocker.board;
import Blocker.queue;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.*;

import java.util.concurrent.ExecutionException;

public class TetrisTheGame implements Screen {



    private final Stage stage;
    private final queue queuing;
    private final board DeadBlock;
    private int index;
    private basicBlock hold;
    private ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture img;
    private OrthographicCamera camera;
    private Texture bucketImage;
    private int speed = 1;
    private int randomV = 0;
    private float y;
    private int score =0;
    private float x;
    private float width;
    private float height;
    private int Sleep =0;
    private basicBlock[] DUMP;
    private basicBlock tempBlock;

    public TetrisTheGame(MyGdxGame game,int speedInt,int randomValue) {
        speed = speedInt;
        randomV =randomValue;
        camera = new OrthographicCamera();
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        stage = new Stage();

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
        DUMP = new basicBlock[20];
        queuing = new queue(shapeRenderer,speed);
        hold = queuing.DeQueue();
        index = 0;
    }


    @Override
    public void show() {
        
    }

    private void BLOCKER(){
            for (int i = 0; i < DUMP.length; i++) {
                try {
                    DUMP[i].draw();
                }
                catch(Exception e){

                }

        }
    }

    private void Clear(int ClearPoint) {
        for (int i = 0; i < 20; i++) {
            try {
                if (DUMP[i].getY() == 25) {
                    boolean Result = DUMP[i].RemoveCube(25);

                    if (Result == true) {
                        DUMP[i] = null;
                    }
                }
            } catch (Exception e) {
            }
            score = score + 100;
        }
    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //inputs
        Sleep = Sleep - 100;


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Sleep < 0 && hold.getX() <=525){
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
            hold.setSpeed(5);
        }
        else{
            hold.setSpeed(speed);
        }


        //the dropping staging
        hold.pass();
        hold.draw();


        if (DeadBlock.Check(hold)){
            DUMP[index] = hold;
            DeadBlock.add(hold);
            //DeadBlock.Output();
            System.out.println(DeadBlock.CheckClear());
            hold = queuing.DeQueue();
            index++;
        }


        if (hold.GetLowestY() ==25){
            DUMP[index] = hold;
            DeadBlock.add(hold);
            DeadBlock.Output();
            System.out.println(DeadBlock.CheckClear());
            if (DeadBlock.CheckClear() == 25){
                Clear(25);
                DeadBlock.ClearRow(25);
            }

            hold = queuing.DeQueue();
            index++;
        }
        else if (hold.GetLowestY() < 25){
            hold.setY(25);
            hold.draw();
            DUMP[index] = hold;
            DeadBlock.add(hold);
            if (DeadBlock.CheckClear() == 25){
                Clear(25);
                DeadBlock.ClearRow(25);
            };
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
