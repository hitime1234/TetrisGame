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


public class ServerRank implements Screen {
    //LIBGDX required variables
    final MyGdxGame game;
    private final Stage stage;
    OrthographicCamera camera;
    //font texture
    private final BitmapFont font;


    private final GameScreen2 gameScreen2;

    //user input for text
    private final TextField ServerIp;
    private final TextField Port;
    public final TextField username;
    private final TextField password;
    //checkbox
    private final CheckBox button9;
    //text output
    private final TextField Rank;
    private final TextField HighScore;
    private final TextField Time;
    private final TextField GameWon;

    //networking status message
    private String Connection = "waiting to connect";





    //networking class
    private Networking Client = null;

    //initialisation of variables
    private String Score;
    private String time;
    private String Nwin;
    private String Ranker;



    public ServerRank(final MyGdxGame game) {
        //Screen resolution
        Gdx.graphics.setWindowedMode(950, 580);
        stage = new Stage(new ExtendViewport(800, 480, 1080, 1920));
        //actor/object input processor
        Gdx.input.setInputProcessor(stage);
        //LIBGDX game super class
        this.game = game;
        //sets camera resolution and class
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        //gets new texture file for text
        font = new BitmapFont();
        //gets skin from constants
        Skin skin = new Skin(Gdx.files.internal(gameConstants.skin));
        //DEBUG screen initialise
        gameScreen2 = new GameScreen2(game);

        //final Button button2 = new TextButton("play",skin);
        //converted to a CheckBox
        button9 = new CheckBox("Sign up?",skin);

        //buttons
        Button button4 = new TextButton("Go Back",skin);
        Button button8 = new TextButton("connect",skin);



        //initialise buttons
        button9.getLabelCell().padLeft(7);
        button9.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button9.setPosition(15,200);
        button9.setTransform(true);
        button9.scaleBy(0.1f);


        //Give buttons actions
        button4.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button4.setPosition(gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*7/4));
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




        button8.setSize(gameConstants.col_width*2,gameConstants.col_height/3);
        button8.setPosition(15,150);
        button8.setTransform(true);
        button8.scaleBy(0.1f);
        button8.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                try {
                    Client = new Networking(ServerIp.getText(), Integer.valueOf(Port.getText()), username.getText(), password.getText(),button9.isChecked(),1);
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
                    else {
                        System.out.println(Networking.GetRecv());
                        Connection = "Successfully connected";
                        String Data = Networking.GetRecv();
                        String[] Array = Data.split(",");
                        Score = Array[2];
                        time = Array[3];
                        Nwin = Array[4];
                        Ranker = Array[5];
                    }


                } catch (Exception e){
                    Connection = "Failed to connect to server \n try different username or password \n serverip and port";

                }
                return true;
            }
        });

        //initialisation output text
        Rank = new TextField("--",skin);
        Rank.setSize(gameConstants.col_width*3/3,gameConstants.col_height/4);
        Rank.setPosition(370,320);
        Rank.scaleBy(0.3f);

        HighScore = new TextField("--",skin);
        HighScore.setSize(gameConstants.col_width*3/3,gameConstants.col_height/4);
        HighScore.setPosition(370,260);
        HighScore.scaleBy(0.3f);

        Time = new TextField("--",skin);
        Time.setSize(gameConstants.col_width*3/3,gameConstants.col_height/4);
        Time.setPosition(370,200);
        Time.scaleBy(0.3f);

        GameWon = new TextField("--",skin);
        GameWon.setSize(gameConstants.col_width*3/3,gameConstants.col_height/4);
        GameWon.setPosition(370,150);
        GameWon.scaleBy(0.3f);





        //initialisation input text
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
        //input text set to password character hiding
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');
        password.scaleBy(0.3f);

        //adds actors/objects to screen
        stage.addActor(button4);
        stage.addActor(button9);
        stage.addActor(ServerIp);
        stage.addActor(Port);
        stage.addActor(button8);
        stage.addActor(username);
        stage.addActor(password);
        stage.addActor(Time);
        stage.addActor(HighScore);
        stage.addActor(GameWon);
        stage.addActor(Rank);
    }       
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //updates screen
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        //anti alisa
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        //updates on screen text
        Rank.setText(Ranker);
        Time.setText(time);
        HighScore.setText(Score);
        GameWon.setText(Nwin);

        //Draws ui text
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

        game.font.draw(game.batch,"rank:", 400, 410);
        game.font.draw(game.batch,"HighScore:", 400, 340);
        game.font.draw(game.batch,"Time (milliseconds):", 400, 270);
        game.font.draw(game.batch,"number of Games won:", 400, 210);
        game.font.draw(game.batch,"Note ranking starts at 0.", 720, 500);
        game.batch.end();

        //old code for touch input
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
        //deletes assets from memory
        stage.dispose();
        game.batch.dispose();
    }

}

