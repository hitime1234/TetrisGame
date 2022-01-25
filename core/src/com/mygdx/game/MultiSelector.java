package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MultiSelector implements Screen {
    final MyGdxGame game;
    private final BitmapFont font;


    private Stage stage;


    OrthographicCamera camera;

    public MultiSelector(final MyGdxGame game) {
        stage = new Stage(new ExtendViewport(800, 480, 1080, 1920));
        Gdx.input.setInputProcessor(stage);
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal(gameConstants.skin));

        Button button2 = new TextButton("Connect to server",skin);
        Button button3 = new TextButton("connect to tetris game",skin);
        Button button4 = new TextButton("go back",skin);

        button2.setSize(gameConstants.col_width*4,gameConstants.col_height/2);
        button2.setPosition(gameConstants.col_width+80,Gdx.graphics.getHeight()-(gameConstants.col_width*1+80));
        button2.setTransform(true);
        button2.scaleBy(0.1f);

        button3.setSize(gameConstants.col_width*4,gameConstants.col_height/2);
        button3.setPosition(gameConstants.col_width+80,Gdx.graphics.getHeight()-(gameConstants.col_width*2+40));
        button3.setTransform(true);
        button3.scaleBy(0.1f);

        button4.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button4.setPosition(gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*35/10));
        button4.setTransform(true);
        button4.scaleBy(0.1f);

        button2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button){
                System.out.println("PRESSED");
                game.setScreen(new ServerRank(game));
                return true;
            }
        });
        button3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("button 2");
                game.setScreen(new PracticeModeSelectorMulti(game));
                //game.setScreen(new ());
                return true;
            }
                            });
        button4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("button 3");
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });
        stage.addActor(button2);
        stage.addActor(button3);
        stage.addActor(button4);
    }       
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Tetris VS", gameConstants.centerX-60, gameConstants.screenHeight-40);
        game.font.draw(game.batch, "main menu", gameConstants.centerX-30, gameConstants.screenHeight-80);
        game.batch.end();
        

        //if (Gdx.input.isTouched()) {
        if (false){
            System.out.println("test");
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        camera.setToOrtho(false,width,height);
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
    public void dispose() {
        stage.dispose();
        game.batch.dispose();
    }

}
