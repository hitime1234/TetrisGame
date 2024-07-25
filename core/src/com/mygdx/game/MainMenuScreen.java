package com.mygdx.game;
import Handling.CSVManager;
import com.badlogic.gdx.Game;
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

public class MainMenuScreen implements Screen {
    //required libgdx variables
    final MyGdxGame game;
    private final BitmapFont font;
    private final Stage stage;
    OrthographicCamera camera;

    //class for CSV management
    public CSVManager File;

    //initialises all the buttons
    public void Button_Initial(Button button, int width, int height,int x,int y){
        button.setSize(width,height);
        button.setPosition(x,y);
        button.setTransform(true);
        button.scaleBy(0.1f);

    }



    public void CreateSessionFile(){
        //creates csv class
        File = new CSVManager("TESTROOT.csv",1);
        //file Layout
        //version index 0
        //file location index 1
        //Score integer 2
        //Name String 3
        //Time 4
        //speed 5

        //checks the file is readable
        CSVManager ReadFile = new CSVManager("TESTROOT.csv", 1);
        //testing data read properly
        System.out.println(ReadFile.getRIGHTKey());
    }

    public MainMenuScreen(final MyGdxGame game) {

        //creates the window and screen for the game client
        //resolution
        Gdx.graphics.setWindowedMode(800, 480);
        //creates CSV file
        CreateSessionFile();
        //creates object screen
        stage = new Stage(new ExtendViewport(800, 480, 1080, 1920));
        //where object get their input data from
        Gdx.input.setInputProcessor(stage);
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        font = new BitmapFont();

        //button object initialised
        Skin skin = new Skin(Gdx.files.internal(gameConstants.skin));
        Button button2 = new TextButton("play",skin);
        Button button3 = new TextButton("multiplayer",skin);
        Button button4 = new TextButton("settings",skin);
        Button button5 = new TextButton("exit",skin);

        //initialises all the buttons using method to reduce space
        Button_Initial(button2,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*1+80));
        Button_Initial(button3,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*2+40));
        Button_Initial(button4,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*3));
        Button_Initial(button5,gameConstants.col_width*2,gameConstants.col_height/2,gameConstants.col_width*2+95,Gdx.graphics.getHeight()-(gameConstants.col_width*4-40));


        //adds the buttons actions
        button2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button){
                System.out.println("PRESSED");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PracticeModeSelector(game));
                return true;
            }
        });
        button3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("button 2");
                game.setScreen(new MultiSelector(game));
                //game.setScreen(new ());
                return true;
            }
                            });
        button4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("button 3");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Settings(game));
                return true;
            }
        });
        button5.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("Shutting down");
                Gdx.app.exit();
                return true;
            }
        });

        //adds the buttons to the game screen
        stage.addActor(button2);
        stage.addActor(button3);
        stage.addActor(button4);
        stage.addActor(button5);
    }       
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //updates screen
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stage.act(delta);
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draws text on screen
        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Tetris VS", gameConstants.centerX-60, gameConstants.screenHeight-40);
        game.font.draw(game.batch, "main menu", gameConstants.centerX-30, gameConstants.screenHeight-80);
        game.batch.end();
        

        //old touch screen code
        //if (Gdx.input.isTouched()) {
        /*
        if (false){
            System.out.println("test");
            game.setScreen(new GameScreen(game));
            dispose();
        }
        */

        stage.draw();
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
        //outputs an exit log just in case the disposal of assets fail
        Gdx.app.log("note: ", "dispose called");
        stage.dispose();
        game.batch.dispose();
    }

}
