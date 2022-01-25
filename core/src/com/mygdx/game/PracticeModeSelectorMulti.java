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
    final MyGdxGame game;
    private final BitmapFont font;
    private final GameScreen2 ProFortniteGamer;
    private final CheckBox button2;
    private final CheckBox button3;
    private final TextField SpeedText;
    private final TextField ServerIp;
    private final TextField Port;
    public final TextField username;
    private final TextField password;
    private final CheckBox button9;
    private int speedInt = 1;
    private int random = 0;
    private String Connection = "waiting to connect";


    private Stage stage;


    OrthographicCamera camera;
    private Networking Client = null;

    public PracticeModeSelectorMulti(final MyGdxGame game) {
        Gdx.graphics.setWindowedMode(950, 580);
        stage = new Stage(new ExtendViewport(800, 480, 1080, 1920));
        Gdx.input.setInputProcessor(stage);
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal(gameConstants.skin));
        ProFortniteGamer = new GameScreen2(game);

        //final Button button2 = new TextButton("play",skin);
        //converted to a CheckBox
        button2  = new CheckBox("Pure random Mode",skin);

        button3 = new CheckBox("Standard Random Mode",skin);
        button9 = new CheckBox("Sign up?",skin);

        Button button4 = new TextButton("Go Back",skin);
        Button button5 = new TextButton("Start Game",skin);
        Button button6 = new TextButton("+",skin);
        Button button7 = new TextButton("-",skin);
        Button button8 = new TextButton("connect",skin);


        button2.getLabelCell().padLeft(7);
        button2.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button2.setPosition(gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*1+80));
        button2.setTransform(true);
        button2.scaleBy(0.1f);

        button9.getLabelCell().padLeft(7);
        button9.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button9.setPosition(15,200);
        button9.setTransform(true);
        button9.scaleBy(0.1f);

        button3.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button3.getLabelCell().padLeft(7);
        button3.setPosition(gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*2+40));
        button3.setTransform(true);
        button3.scaleBy(0.1f);

        button4.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button4.setPosition(gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*7/2));
        button4.setTransform(true);
        button4.scaleBy(0.1f);
        button4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("button 3");
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });

        button5.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button5.setPosition(gameConstants.col_width*4+100,Gdx.graphics.getHeight()-(gameConstants.col_width*4 + 30));
        button5.setTransform(true);
        button5.scaleBy(0.1f);
        button5.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("Starting Game");
                game.setScreen(new MultiTetrisTheGame(game,speedInt,random,Client));
                return true;
            }
        });

        button6.setSize(gameConstants.col_width*1,gameConstants.col_height/4);
        button6.setPosition(gameConstants.col_width*4+50,Gdx.graphics.getHeight()-(gameConstants.col_width*3-20));
        button6.setTransform(true);
        button6.scaleBy(0.1f);
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

        button7.setSize(gameConstants.col_width*1,gameConstants.col_height/4);
        button7.setPosition(gameConstants.col_width*2+50,Gdx.graphics.getHeight()-(gameConstants.col_width*3-20));
        button7.setTransform(true);
        button7.scaleBy(0.1f);
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


        button8.setSize(gameConstants.col_width*2,gameConstants.col_height/3);
        button8.setPosition(15,150);
        button8.setTransform(true);
        button8.scaleBy(0.1f);
        button8.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                try {
                    Client = new Networking(ServerIp.getText(), Integer.valueOf(Port.getText()), username.getText(), password.getText(),button9.isChecked(),0);
                    if (Client.GetRecv().equals("Die SCUM")) {
                        Connection = "Failed to connect to server \n try different username or password \n server ip and port";
                        Client = null;
                    } else if (Client.GetRecv().equals("Account Created")){
                        Connection = "your account was made successfully\nyou agree to conditions,\n that the password can't be changed.";
                    }
                    else if (Client.GetRecv().equals("Already exists")){
                        Connection = "Your account  and  connection,\nfailed due to overlapping account details.";
                    }
                    else if (Client.GetRecv().equals("Error Couldn't be added")) {
                        Connection = "Error Couldn't be added due to a server failure";
                    }
                    else {
                        System.out.println(Client.GetRecv());
                        Connection = "Successfully connected";
                    }


                } catch (Exception e){
                    Connection = "Failed to connect to server \n try different username or password \n serverip and port";
                }
                return true;
            }
        });


        button3.setChecked(true);

        SpeedText = new TextField(Integer.toString(speedInt),skin);
        SpeedText.setSize(gameConstants.col_width*3/10,gameConstants.col_height/4);
        SpeedText.setPosition(gameConstants.col_width*39/10,Gdx.graphics.getHeight()-(gameConstants.col_width*3-20));
        SpeedText.scaleBy(0.2f);


        ServerIp = new TextField("127.0.0.1",skin);
        ServerIp.setSize(gameConstants.col_width*3/2,gameConstants.col_height/4);
        ServerIp.setPosition(50,400);
        ServerIp.scaleBy(0.3f);

        Port = new TextField("25565",skin);
        Port.setSize(gameConstants.col_width*3/3,gameConstants.col_height/4);
        Port.setPosition(50,350);
        Port.scaleBy(0.3f);


        username = new TextField("admin",skin);
        username.setSize(gameConstants.col_width*3/2,gameConstants.col_height/4);
        username.setPosition(50,300);
        username.scaleBy(0.3f);

        password = new TextField("admin",skin);
        password.setSize(gameConstants.col_width*3/2,gameConstants.col_height/4);
        password.setPosition(50,250);
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');
        password.scaleBy(0.3f);


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
        //Random mode selector
        //needs update with switch case for better performance
        SpeedText.setText(Integer.toString(speedInt));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
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


        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

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
