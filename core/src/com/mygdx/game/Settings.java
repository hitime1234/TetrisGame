package com.mygdx.game;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import Handling.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Settings implements Screen {
    final MyGdxGame game;
    private final BitmapFont font;


    //note these variables isn't in use because the features was never finished
    private final TextField ColorblindMode;
    private String ColorBlindString = "Normal";
    private int ColourBlindSelection = 1;
    //end

    //text screens for the keybindings
    private final TextField RIGHT;
    private final TextField LEFT;
    private final TextField Flip;
    private final TextField down;
    private final int KeyCode = 0;
    private final TextField Hold;
    private final TextField HardDrop;



    //access to the CSV file
    private CSVManager file;




    //displays/draws objects on screen
    private final Stage stage;

    //keybindings real int values
    private int KeyRight;
    private int keyLeft;
    private int keyFlip;
    private int keyHold;
    private int keyHardDrop;
    private int keySoftDrop;

    //status of checking for key presses
    private boolean checking = false;




    //camera for stage required to display objects
    OrthographicCamera camera;



    public Settings(final MyGdxGame game)  {
        //gets stage
        stage = new Stage(new ExtendViewport(800, 480, 1080, 1920));
        Gdx.input.setInputProcessor(stage);
        file = new CSVManager("TESTROOT.csv",1);


        //old network test code
        //Client1 = new Networking("127.0.0.1",25565,"admin","admin",false,0);
        //Client2 = new Networking("127.0.0.1",25565,"admin","admin",false,0);
        //threadPool = Executors.newCachedThreadPool();
        //threadPool.submit(Client1);
        //threadPool.submit(Client2);


        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal(gameConstants.skin));


        //final Button button2 = new TextButton("play",skin);
        //converted to a CheckBox


        Button button4 = new TextButton("Go Back",skin);

        Button button5 = new TextButton("KeyBind",skin);
        Button button8 = new TextButton("KeyBind",skin);
        Button button9 = new TextButton("KeyBind",skin);
        Button button10 = new TextButton("KeyBind",skin);
        Button button11 = new TextButton("KeyBind",skin);
        Button button12 = new TextButton("KeyBind",skin);

        Button button6 = new TextButton(">",skin);
        Button button7 = new TextButton("<",skin);


        //DA WORLD STOPS - aka refreshes the file
        //this removes the old file contents - including score
        Button Nuke = new TextButton("Clear old settings",skin);

        Nuke.setSize(gameConstants.col_width*3,gameConstants.col_height/2);
        Nuke.setPosition(gameConstants.col_width*2+(80+160),30);
        Nuke.setTransform(true);
        Nuke.scaleBy(0.1f);
        Nuke.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("NUKING");
                file = new CSVManager("TESTROOT.csv",0);
                KeyRight = file.getRIGHTKey();
                keyHardDrop = file.getSPACE();
                keySoftDrop = file.getDOWN();
                keyHold = file.getHold();
                keyLeft = file.getLEFT();
                keyFlip = file.getUP();
                return true;

            }
        });


        button4.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button4.setPosition(gameConstants.col_width*2+(80+180),Gdx.graphics.getHeight()-(gameConstants.col_width*7/2));
        button4.setTransform(true);
        button4.scaleBy(0.1f);
        button4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                System.out.println("returning home");
                System.out.println("closing connection");
                //threadPool.shutdown();
                game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });


        //key Buttons for binding

        //key right
        button5.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button5.setPosition(150,350);
        button5.setTransform(true);
        button5.scaleBy(0.1f);
        button5.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                int hold = getInput();
                RIGHT.setText(Input.Keys.toString(hold));
                KeyRight = hold;
                if (KeyRight != 0) {
                    file.setRIGHTKey(KeyRight);
                    file.CsvUpdate();
                }
                return true;
            }
        });

        //key left
        button8.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button8.setPosition(150,280);
        button8.setTransform(true);
        button8.scaleBy(0.1f);
        button8.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                int hold = getInput();
                LEFT.setText(Input.Keys.toString(hold));
                keyLeft = hold;
                if (keyLeft != 0) {
                    file.setLEFT(keyLeft);
                    file.CsvUpdate();
                }
                return true;
            }
        });

        //key Flip
        button9.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button9.setPosition(150,210);
        button9.setTransform(true);
        button9.scaleBy(0.1f);
        button9.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                int hold = getInput();
                Flip.setText(Input.Keys.toString(hold));
                keyFlip = hold;
                if (keyFlip != 0) {
                    file.setUP(keyFlip);
                    file.CsvUpdate();
                }
                return true;
            }
        });


        //key hold
        button10.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button10.setPosition(150,140);
        button10.setTransform(true);
        button10.scaleBy(0.1f);
        button10.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                int hold = getInput();
                Hold.setText(Input.Keys.toString(hold));
                keyHold = hold;
                if (keyHold  != 0) {
                    file.setHoldKey(keyHold);
                    file.CsvUpdate();
                }
                return true;
            }
        });

        //key Down
        button11.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button11.setPosition(150,70);
        button11.setTransform(true);
        button11.scaleBy(0.1f);
        button11.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                int hold = getInput();
                down.setText(Input.Keys.toString(hold));
                keySoftDrop = hold;
                if (keySoftDrop  != 0) {
                    file.setDOWN(keySoftDrop);
                    file.CsvUpdate();
                }
                return true;
            }
        });

        //key Hard drop
        button12.setSize(gameConstants.col_width*2,gameConstants.col_height/2);
        button12.setPosition(150,0);
        button12.setTransform(true);
        button12.scaleBy(0.1f);
        button12.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                int hold = getInput();
                HardDrop.setText(Input.Keys.toString(hold));
                keyHardDrop = hold;
                if (keyHardDrop  != 0) {
                    file.setSPACE(keyHardDrop);
                    file.CsvUpdate();
                }
                return true;
            }
        });



        button6.setSize(gameConstants.col_width*1,gameConstants.col_height/3);
        button6.setPosition(gameConstants.col_width*4+(90+180),Gdx.graphics.getHeight()-(gameConstants.col_width*3-20));
        button6.setTransform(true);
        button6.scaleBy(0.1f);
        button6.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                //System.out.println("speed up");
                if (ColourBlindSelection < 4) {
                    ColourBlindSelection = ColourBlindSelection + 1;
                }
                else{
                    if (ColourBlindSelection == 4){
                        ColourBlindSelection = 1;
                    }
                }
                return true;
            }
        });

        button7.setSize(gameConstants.col_width*1,gameConstants.col_height/3);
        button7.setPosition(gameConstants.col_width*2+180,Gdx.graphics.getHeight()-(gameConstants.col_width*3-20));
        button7.setTransform(true);
        button7.scaleBy(0.1f);
        button7.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event,float x,float y,int pointer,int button) {
                //System.out.println("speed down");
                if (ColourBlindSelection > 1) {
                    ColourBlindSelection = ColourBlindSelection - 1;
                }else{
                    if (ColourBlindSelection == 1){
                        ColourBlindSelection = 4;
                    }
                }

                return true;
            }
        });




        ColorblindMode = new TextField(Integer.toString(ColourBlindSelection),skin);
        ColorblindMode.setSize(gameConstants.col_width*3/2,gameConstants.col_height/4);
        ColorblindMode.setPosition(gameConstants.screenWidth/2+110,Gdx.graphics.getHeight()-(gameConstants.col_width*3-20));
        ColorblindMode.scaleBy(0.2f);


        KeyRight = file.getRIGHTKey();
        RIGHT = new TextField(Input.Keys.toString(KeyRight),skin);
        RIGHT.setSize(gameConstants.col_width*4/3,gameConstants.col_height/4);
        RIGHT.setPosition(10,360);
        RIGHT.scaleBy(0.2f);


        keyLeft = file.getLEFT();
        LEFT = new TextField(Input.Keys.toString(keyLeft),skin);
        LEFT.setSize(gameConstants.col_width*4/3,gameConstants.col_height/4);
        LEFT.setPosition(10,290);
        LEFT.scaleBy(0.2f);

        keyFlip = file.getUP();
        Flip= new TextField(Input.Keys.toString(keyFlip),skin);
        Flip.setSize(gameConstants.col_width*4/3,gameConstants.col_height/4);
        Flip.setPosition(10,220);
        Flip.scaleBy(0.2f);

        keyHold = file.getHold();
        Hold= new TextField(Input.Keys.toString(keyHold),skin);
        Hold.setSize(gameConstants.col_width*4/3,gameConstants.col_height/4);
        Hold.setPosition(10,150);
        Hold.scaleBy(0.2f);

        keySoftDrop = file.getDOWN();
        down = new TextField(Input.Keys.toString(keySoftDrop),skin);
        down.setSize(gameConstants.col_width*4/3,gameConstants.col_height/4);
        down.setPosition(10,80);
        down.scaleBy(0.2f);



        keyHardDrop = file.getSPACE();
        HardDrop= new TextField(Input.Keys.toString(keyHardDrop),skin);
        HardDrop.setSize(gameConstants.col_width*4/3,gameConstants.col_height/4);
        HardDrop.setPosition(10,10);
        HardDrop.scaleBy(0.2f);




        stage.addActor(button4);
        stage.addActor(button5);
        //stage.addActor(button6);
        //stage.addActor(button7);
        stage.addActor(button8);
        stage.addActor(button9);
        stage.addActor(button10);
        stage.addActor(button11);
        stage.addActor(button12);
        //stage.addActor(SpeedText);
        stage.addActor(RIGHT);
        stage.addActor(LEFT);
        stage.addActor(Flip);
        stage.addActor(Hold);
        stage.addActor(down);
        stage.addActor(HardDrop);
        stage.addActor(Nuke);

    }
    @Override
    public void show() {

    }


    public int getInput(){
        int key = 0;
        if (checking == false) {
            checking =true;
            for (int i = 0; i < 255; i++) {
                if (Gdx.input.isKeyPressed(i)) {
                    key = i;
                }
            }
            checking = false;
        }
        return key;
    }

    @Override
    public void render(float delta) {
        RIGHT.setText(Input.Keys.toString(KeyRight));
        LEFT.setText(Input.Keys.toString(keyLeft));
        down.setText(Input.Keys.toString(keySoftDrop));
        HardDrop.setText(Input.Keys.toString(keyHardDrop));
        Flip.setText(Input.Keys.toString(keyFlip));
        Hold.setText(Input.Keys.toString(keyHold));


        /*
        Random rand = new Random();
        PholdData = holdData;
        holdData = String.valueOf(rand.nextInt(100000));
        Client1.sendOFF(holdData);
        String hold = Client2.GetRecv();
        if (hold.equals(PholdData)){
            System.out.println("goodJob" +id);
            id++;
        }

         */



        //feature never implemented
        switch (ColourBlindSelection){
            case 1:
                ColorBlindString = "normal";
                break;
            case 2:
                ColorBlindString = "Deuteranopia";
                break;
            case 3:
                ColorBlindString = "Protanopia";
                break;
            case 4:
                ColorBlindString = "Tritanopia";
                break;
        }
        ColorblindMode.setText(ColorBlindString);





        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //draws actors/object on screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //checks for input for actors/objects
        stage.act(delta);
        stage.draw();

        //draws text for the ui
        game.batch.begin();
        game.font.getData().setScale(1.2f,1.2f);
        game.font.draw(game.batch, "KeyBindings", 120, gameConstants.screenHeight-40);
        game.font.draw(game.batch, "move block right", 10, 400);
        game.font.draw(game.batch, "move block left", 10, 330);
        game.font.draw(game.batch, "Flip", 10, 260);
        game.font.draw(game.batch, "hold block", 10, 190);
        game.font.draw(game.batch, "block soft drop", 10, 120);
        game.font.draw(game.batch, "block hard drop", 10, 50);
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


