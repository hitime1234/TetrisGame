package com.mygdx.game;

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

public class TetrisTheGame implements Screen {
    private final Stage stage;
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

    }


    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        y = y + 100;
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();



        shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        height = 50;
        width = 20;
        x= 300;
        y=300;
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
