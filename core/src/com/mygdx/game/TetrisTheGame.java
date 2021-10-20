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
    private float x;
    private float width;
    private float height;
    private int Sleep =0;
    private basicBlock[] DUMP;


    public TetrisTheGame(MyGdxGame game,int speedInt,int randomValue) {
        speed = speedInt;
        randomV =randomValue;
        camera = new OrthographicCamera();
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        stage = new Stage();
        shapeRenderer = new ShapeRenderer();
        camera.setToOrtho(false, 800, 480);
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
        try {
            for (int i = 0; i < DUMP.length; i++) {
                DUMP [i].draw();
            }
        }
        catch(Exception e){

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

        hold.draw();
        hold.pass();

        if (hold.getY() ==0){
            DUMP[index] = hold;
            DeadBlock.add(hold);
            //DeadBlock.Output();
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
        Sleep = Sleep - 100;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Sleep < 0 && x >= 0){
            x = x +30;
            Sleep = 1000;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Sleep < 0 && x <= 780){
            x = x -30;
            Sleep = 1000;
        }



        shapeRenderer.rect(x,y,width,height);
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
