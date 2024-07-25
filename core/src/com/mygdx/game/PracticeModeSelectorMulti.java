package com.mygdx.game;

import Handling.Networking;
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


public class PracticeModeSelectorMulti implements Screen {
    //required LIBGDX variables
    final MyGdxGame game;
    private final BitmapFont font;
    private final GameScreen2 gameScreen2;
    private final Stage stage;
    OrthographicCamera camera;

    //actor on screen
    private final CheckBox button2;
    private final CheckBox button3;
    private final TextField SpeedText;
    private final TextField ServerIp;
    private final TextField Port;
    public final TextField username;
    private final TextField password;
    private final CheckBox button9;

    //user defined variables
    private int speedInt = 1;
    private int random = 0;

    //text for connection status
    private String Connection = "waiting to connect";




    //networking class
    private Networking Client = null;

    //initialises all the buttons
    public void Button_Initial(Button button, int width, int height,int x,int y){
        button2.getLabelCell().padLeft(7);
        button.setSize(width,height);
        button.setPosition(x,y);
        button.setTransform(true);
        button.scaleBy(0.1f);
    }

    public PracticeModeSelectorMulti(final MyGdxGame game) {
        //screen initialise
        Gdx.graphics.setWindowedMode(950, 580);
        stage = new Stage(new ExtendViewport(800, 480, 1080, 1920));
        Gdx.input.setInputProcessor(stage);

        //get LIBGDX game screen
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        //creates font pack for text
        font = new BitmapFont();

        //gets the game skin/texture json from constants
        Skin skin = new Skin(Gdx.files.internal(gameConstants.skin));
        gameScreen2 = new GameScreen2(game);

        //final Button button2 = new TextButton("play",skin);
        //converted to a CheckBox
        button2  = new CheckBox("Pure random Mode",skin);
        button3 = new CheckBox("Standard Random Mode",skin);
        button9 = new CheckBox("Sign up?",skin);

        //buttons
        Button button4 = new TextButton("Go Back",skin);
        Button button5 = new TextButton("Start Game",skin);
        Button button6 = new TextButton("+",skin);
        Button button7 = new TextButton("-",skin);
        Button button8 = new TextButton("connect",skin);

        //initialise buttons
        button2.getLabelCell().padLeft(7);
        //for checkmark
        Button_Initial(button2,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*1+80));




        Button_Initial(button3,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*2+40));
        //for checkmark
        button3.getLabelCell().padLeft(7);

        Button_Initial(button4,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*7/2));

        Button_Initial(button5,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*4+100,Gdx.graphics.getHeight()-(gameConstants.col_width*4 + 30));

        Button_Initial(button6,gameConstants.col_width*1,gameConstants.col_height/4,gameConstants.col_width*4+50,Gdx.graphics.getHeight()-(gameConstants.col_width*3-20));

        Button_Initial(button7,gameConstants.col_width*1,gameConstants.col_height/4,gameConstants.col_width*2+50,Gdx.graphics.getHeight()-(gameConstants.col_width*3-20));

        Button_Initial(button8,gameConstants.col_width*2,gameConstants.col_height/3,15,150);

        //for checkmark
        button9.getLabelCell().padLeft(7);
        Button_Initial(button9,gameConstants.col_width*2,gameConstants.col_height/2,15,200);


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
                game.setScreen(new MultiTetrisTheGame(game,speedInt,random,Client));
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

        button8.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                try {
                    Client = new Networking(ServerIp.getText(), Integer.valueOf(Port.getText()), username.getText(), password.getText(),button9.isChecked(),0);
                    if (Networking.GetRecv().equals("Die SCUM")) {
                        Connection = "Failed to connect to server \n try different username or password \n server ip and port";
                        Client = null;
                    } else if (Networking.GetRecv().equals("Account Created")){
                        Connection = "your account was made successfully\nyou agree to conditions,\n that the password can't be changed.";
                    }
                    else if (Networking.GetRecv().equals("Already exists")){
                        Connection = "Your account  and  connection,\nfailed due to overlapping account details.";
                    }
                    else if (Networking.GetRecv().equals("Error Couldn't be added")) {
                        Connection = "Error Couldn't be added due to a server failure";
                    }
                    else if (Networking.GetRecv().equals("ERROR")){
                        Connection = "server not found";
                    }

                    else {
                        System.out.println(Networking.GetRecv());
                        Connection = "Successfully connected";
                    }


                } catch (Exception e){
                    Connection = "Failed to connect to server \n try different username or password \n serverip and port";
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


        //initialises SERVERIP field
        ServerIp = new TextField("127.0.0.1",skin);
        ServerIp.setSize(gameConstants.col_width*3/2,gameConstants.col_height/4);
        ServerIp.setPosition(50,400);
        ServerIp.scaleBy(0.3f);

        //initialises port field
        Port = new TextField("25565",skin);
        Port.setSize(gameConstants.col_width*3/3,gameConstants.col_height/4);
        Port.setPosition(50,350);
        Port.scaleBy(0.3f);

        //initialises username field
        username = new TextField("admin",skin);
        username.setSize(gameConstants.col_width*3/2,gameConstants.col_height/4);
        username.setPosition(50,300);
        username.scaleBy(0.3f);


        //initialises password field but with character protection enabled
        password = new TextField("admin",skin);
        password.setSize(gameConstants.col_width*3/2,gameConstants.col_height/4);
        password.setPosition(50,250);
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');
        password.scaleBy(0.3f);

        //adds actors
        stage.addActor(button2);
        stage.addActor(button3);
        stage.addActor(button4);
        stage.addActor(button5);
        stage.addActor(button6);
        stage.addActor(button7);
        stage.addActor(button9);
        stage.addActor(SpeedText);
        stage.addActor(ServerIp);
        stage.addActor(Port);
        stage.addActor(button8);
        stage.addActor(username);
        stage.addActor(password);
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
        game.font.getData().setScale(1.2f,1.0f);
        game.font.draw(game.batch, "Welcome to Tetris VS", gameConstants.centerX-10, gameConstants.screenHeight+80);
        game.font.draw(game.batch, "main menu", gameConstants.centerX+25, gameConstants.screenHeight+60);
        game.font.draw(game.batch, "main menu", gameConstants.centerX+25, gameConstants.screenHeight+60);
        game.font.draw(game.batch,"Status:", 75, 150);
        game.font.draw(game.batch, Connection, 75, 125);
        game.font.draw(game.batch,"Server ip address:", 30, 510);
        game.font.draw(game.batch,"Port:", 30, 450);
        game.font.draw(game.batch,"username:", 30, 400);
        game.font.draw(game.batch,"password:", 30, 340);
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

