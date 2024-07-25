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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class PracticeModeSelector implements Screen {
    //LIBGDX required variables
    final MyGdxGame game;
    private final BitmapFont font;
    private final GameScreen2 screen2;
    private final Stage stage;
    OrthographicCamera camera;

    //actor/object on screen
    private final CheckBox button2;
    private final CheckBox button3;
    private final TextField SpeedText;

    //user defined variables
    private int speedInt = 1;
    private int random = 0;

    //initialises all the buttons
    public void Button_Initial(Button button, int width, int height,int x,int y){
        button2.getLabelCell().padLeft(7);
        button.setSize(width,height);
        button.setPosition(x,y);
        button.setTransform(true);
        button.scaleBy(0.1f);

    }

    public PracticeModeSelector(final MyGdxGame game) {
        //screen initialise
        Gdx.graphics.setWindowedMode(800, 480);
        stage = new Stage(new ExtendViewport(800, 480, 1080, 1920));
        //screen input process set to current screen actors/objects
        Gdx.input.setInputProcessor(stage);

        //get LIBGDX game screen
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        //creates font pack for text
        font = new BitmapFont();

        //gets the game skin/texture json from constants
        Skin skin = new Skin(Gdx.files.internal(gameConstants.skin));
        screen2 = new GameScreen2(game);

        //final Button button2 = new TextButton("play",skin);
        //converted to a CheckBox
        button2  = new CheckBox("Standard Random Mode",skin);
        button3 = new CheckBox("Pure random Mode",skin);


        Button button4 = new TextButton("Go Back",skin);
        Button button5 = new TextButton("Start Game",skin);
        Button button6 = new TextButton("+",skin);
        Button button7 = new TextButton("-",skin);




        //initialise buttons
        Button_Initial(button2,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*1+80));
        //for checkmark
        button2.getLabelCell().padLeft(7);

        Button_Initial(button3,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*2+40));
        //for checkmark
        button3.getLabelCell().padLeft(7);

        Button_Initial(button4,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*7/2));

        Button_Initial(button5,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*4+100,Gdx.graphics.getHeight()-(gameConstants.col_width*4 + 30));

        Button_Initial(button6,gameConstants.col_width*1,gameConstants.col_height/4,gameConstants.col_width*4+50,Gdx.graphics.getHeight()-(gameConstants.col_width*3-20));

        Button_Initial(button7,gameConstants.col_width*1,gameConstants.col_height/4,gameConstants.col_width*2+50,Gdx.graphics.getHeight()-(gameConstants.col_width*3-20));

        //actions for buttons
        button4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("button 3");
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });



        button5.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("Starting Game");
                game.setScreen(new TetrisTheGame(game,speedInt,random));
                return true;
            }
        });


        button6.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("speed up");
                if (speedInt < 16) {
                    speedInt = speedInt + 1;
                }
                return true;
            }
        });

        button7.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("speed down");
                if (speedInt > 1) {
                    speedInt = speedInt - 1;
                }
                return true;
            }
        });


        //sets default normal random mode
        button2.setChecked(true);

        //initialises speed field
        SpeedText = new TextField(Integer.toString(speedInt),skin);
        SpeedText.setSize(gameConstants.col_width*3/10,gameConstants.col_height/4);
        SpeedText.setPosition(gameConstants.col_width*39/10,Gdx.graphics.getHeight()-(gameConstants.col_width*3-20));
        SpeedText.scaleBy(0.2f);


        //adds actors
        stage.addActor(button2);
        stage.addActor(button3);
        stage.addActor(button4);
        stage.addActor(button5);
        stage.addActor(button6);
        stage.addActor(button7);
        stage.addActor(SpeedText);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //sets the antialias for text
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //Random mode selector
        //needs update with switch case for better performance
        SpeedText.setText(Integer.toString(speedInt));

        //turns off other button if one button is pressed
        if (button2.isPressed()){
            button3.setChecked(false);
        }
        if (button3.isPressed()){
            button2.setChecked(false);
        }

        if (button3.isChecked()){
            random = (0);

        }
        else{
            random = (1);
        }

        if (button2.isChecked()){
            random = (1);
        }
        else{
            random = (0);
        }

        //System.out.println(ProFortniteGamer.RandomMode);

        //updates screen
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        //draws text for ui
        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Tetris VS", gameConstants.centerX-60, gameConstants.screenHeight-40);
        game.font.draw(game.batch, "main menu", gameConstants.centerX-30, gameConstants.screenHeight-80);
        game.batch.end();

        //test code for touch screen input
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
        //deletes assets if there are any
        stage.dispose();
        game.batch.dispose();
    }

}

