package com.mygdx.game;

import Blocker.BasicCube;
import Blocker.basicBlock;
import Blocker.board;
import Blocker.queue;
import Handling.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import javax.sound.sampled.Line;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiTetrisTheGame extends ScreenAdapter implements Screen  {
    private final Stage stage;
    private final ExecutorService threadPool;
    private PackerClass NUTJ =null;
    private Networking GameP2P;
    private queue queuing;
    private final long start;
    private BitmapFont font;
    private ArrayList<basicBlock> DUMP2 =null;
    private LoseChecking LoseCheckThread;
    private final board DeadBlock;
    private final MyGdxGame game;
    private final Music menuMusic;
    private final Music moveMusic;
    private final Music FlipMusic;
    private final Music PlaceMusic;
    private final Music Line1Music;
    private final Music Line2Music;
    private final Music Line3Music;
    private final Music TetrisMusic;
    private basicBlock PlayerBlock;
    private ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture img;
    private boolean quit = false;
    private final OrthographicCamera camera;
    private final Texture bucketImage;
    private int speed;
    private boolean grace = false;
    private int OSpeed;
    private float y;
    private int score =0;
    private float x;
    private final float width;
    private int dumpSize = 0;
    private boolean swap = true;
    private final float height;
    private int Sleep =0;
    private int DropTime = 50;
    boolean DropDown = false;
    private int LinesCount = 0;
    private int FrameCount =0;
    private long pastTime = System.currentTimeMillis();
    private long StartTime = System.currentTimeMillis();
    public int fps = 0;
    private final int speedChange = 20;
    private basicBlock holdQueue;
    public passingData ThreadData;
    private BugFixThread fixBug;
    private boolean loseScreen = false;
    private final CSVManager file;
    private boolean write = true;
    private final int KeyRight;
    private final int keyLeft;
    private final int keyFlip;
    private final int keyHold;
    private final int keyHardDrop;
    private final int keySoftDrop;
    private ArrayList<basicBlock> Dump3 =null;
    private basicBlock Player2Block;
    private int TempSpeed;
    private int TempX;
    private int TempY;
    private ArrayList<String> Temp;
    private BasicCube[] TempCube;
    private ShapeRenderer TempDraw;
    private int Player2Score;
    private int Player2Speed;
    private basicBlock Player2Hold;
    private int Lines;
    private int TotalRecieved = 0;
    private int totalSent =0;
    private boolean youWin;
    private boolean currentStatus;
    private int NumWin = 0;

    public void LoseScreen(){
        loseScreen = true;
    }

    public void QuitButton(){
        Skin skin = new Skin(Gdx.files.internal(gameConstants.skin));
        Button button2 = new TextButton("Quit",skin);
        button2.setTransform(true);
        Table table = new Table();
        table.setFillParent(true);
        table.setTransform(true);
        table.setScale(0.9f);
        table.add(button2);
        table.setPosition(525,-190);
        button2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                System.out.println("PRESSED");
                quit= true;
                return true;
            }
        });
        stage.addActor(table);
    }

    public void unSerialize(String data){
        ArrayList arrayList = new ArrayList();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        /*
          layering

           Fully Stored serialised
           partial serialised arraylist of data
           serialised String data arrayList
           Raw string data
         */

        Dump3 = new ArrayList<>();
        try {
            //String[] Split = data.split(";::;");
            //System.out.println(data);
            arrayList =gson.fromJson(data, ArrayList.class);


            for (int i=0;i<arrayList.size()-1;i++) {
                try {
                    String hold = arrayList.get(i).toString();
                    ArrayList<String> FinalData = gson.fromJson(hold, ArrayList.class);
                    String SValue = FinalData.get(0);
                    String SCube1 = FinalData.get(1);
                    String SCube2 = FinalData.get(2);
                    String SCube3 = FinalData.get(3);
                    String SCube4 = FinalData.get(4);
                    ArrayList Value = gson.fromJson(SValue, ArrayList.class);
                    basicBlock Output = new basicBlock(shapeRenderer, 0, 0, 0, new int[4][4], -1);
                    Output.SetValues(Output.read(Value.get(0).toString()), Integer.parseInt(Value.get(1).toString()), Integer.parseInt(Value.get(2).toString()), Integer.parseInt(Value.get(3).toString()), Boolean.parseBoolean(Value.get(4).toString()));
                    BasicCube[] cubeArray = new BasicCube[4];
                    cubeArray[0] = gson.fromJson(SCube1, BasicCube.class);
                    cubeArray[1] = gson.fromJson(SCube2, BasicCube.class);
                    cubeArray[2] = gson.fromJson(SCube3, BasicCube.class);
                    cubeArray[3] = gson.fromJson(SCube4, BasicCube.class);
                    Output.cube = cubeArray;
                    Dump3.add(Output);
                } catch (Exception e) {
                    System.out.println("data output fail");
                }
            }
            //DUMP2 = Dump3;
            try {
                String hold = arrayList.get(arrayList.size()-1).toString();
                ArrayList<String> FinalData = gson.fromJson(hold, ArrayList.class);
                String SValue = FinalData.get(0);
                String SCube1 = FinalData.get(1);
                String SCube2 = FinalData.get(2);
                String SCube3 = FinalData.get(3);
                String SCube4 = FinalData.get(4);
                ArrayList Value = gson.fromJson(SValue, ArrayList.class);
                basicBlock Output = new basicBlock(shapeRenderer, 0, 0, 0, new int[4][4], -1);
                Output.SetValues(Output.read(Value.get(0).toString()), Integer.parseInt(Value.get(1).toString()), Integer.parseInt(Value.get(2).toString()), Integer.parseInt(Value.get(3).toString()), Boolean.parseBoolean(Value.get(4).toString()));
                BasicCube[] cubeArray = new BasicCube[4];
                cubeArray[0] = gson.fromJson(SCube1, BasicCube.class);
                cubeArray[1] = gson.fromJson(SCube2, BasicCube.class);
                cubeArray[2] = gson.fromJson(SCube3, BasicCube.class);
                cubeArray[3] = gson.fromJson(SCube4, BasicCube.class);
                Output.cube = cubeArray;
                Player2Block = Output;
            } catch (Exception e){
                System.out.println(e);
                System.out.println("player block failed to unpack");
            }

        } catch (Exception e){

        }

    }



    public String Serialize(){
        //Creating the object
        String data = "";
        ArrayList<String> fullArray = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        for (int i=0;i<DUMP2.size();i++){
            if (DUMP2 != null){
                try {
                    ArrayList<String> hold = new ArrayList<>();
                    ArrayList holdClone = DUMP2.get(i).CloneData();
                    String Values  = gson.toJson(holdClone);
                    String Cube1 = gson.toJson(DUMP2.get(i).cube[0]);
                    String Cube2 = gson.toJson(DUMP2.get(i).cube[1]);
                    String Cube3 = gson.toJson(DUMP2.get(i).cube[2]);
                    String Cube4 = gson.toJson(DUMP2.get(i).cube[3]);
                    hold.add(Values);hold.add(Cube1);hold.add(Cube2);hold.add(Cube3);hold.add(Cube4);
                    fullArray.add(gson.toJson(hold));
                } catch (Exception e){
                    System.out.println("error not able store this data");
                }
            }
        }
        try {
            //Adds playerBlock
            ArrayList<String> hold = new ArrayList<>();
            ArrayList holdClone = PlayerBlock.CloneData();
            String Values  = gson.toJson(holdClone);
            String Cube1 = gson.toJson(PlayerBlock.cube[0]);
            String Cube2 = gson.toJson(PlayerBlock.cube[1]);
            String Cube3 = gson.toJson(PlayerBlock.cube[2]);
            String Cube4 = gson.toJson(PlayerBlock.cube[3]);
            hold.add(Values);hold.add(Cube1);hold.add(Cube2);hold.add(Cube3);hold.add(Cube4);
            fullArray.add(gson.toJson(hold));
            //finalise the String for transport
            data = gson.toJson(fullArray);
            //for (int i=0;i<fullArray.size();i++){
               //data += fullArray.get(i) + ";::;";
            //}
        }catch (Exception e){
            System.out.println("error not able store this data");
            }

        //System.out.println(data);
        return data;
    }


    public void packetPacker(){
        String data = "";

        //WARNING future self
        // DUMP has been deprecated
        ArrayList<basicBlock> Basic = DUMP2;



        //Creating the object
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        data = Serialize();
        GameP2P.sendOFF(data);
        if (GameP2P.GetRecv().isEmpty() == false && !GameP2P.GetRecv().equals("[") && !GameP2P.GetRecv().equals("]")) {
            String store = GameP2P.GetRecv();
            //file.ForceWrite(store,data);
            unSerialize(store);
        }

        //System.out.println(data);




        /**
        String jsonString = gson.toJson(hold);

        System.out.println(jsonString);
        CSVManager data = gson.fromJson(jsonString,CSVManager.class);
        System.out.println(data.NAME());
         */

    }

    public MultiTetrisTheGame(MyGdxGame game, int speedInt, int randomValue, Networking client) {
        Gdx.graphics.setWindowedMode(1600, 720);
        //client

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/jackSong2.ogg"));
        menuMusic.setLooping(true);
        menuMusic.play();
        moveMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/move_cut2.ogg"));
        FlipMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/flip2.ogg"));
        PlaceMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/5660886917709824nnew.ogg"));
        Line1Music = Gdx.audio.newMusic(Gdx.files.internal("sounds/6593737204957184.ogg"));
        Line2Music = Gdx.audio.newMusic(Gdx.files.internal("sounds/line_2.ogg"));
        Line3Music = Gdx.audio.newMusic(Gdx.files.internal("sounds/line_3.ogg"));
        TetrisMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/tetris.ogg"));
        FlipMusic.setVolume(0.35f);
        moveMusic.setVolume(0.4f);
        PlaceMusic.setVolume(0.6f);

        font = new BitmapFont();

        speed = speedInt;
        OSpeed = speedInt;
        camera = new OrthographicCamera();
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        stage = new Stage(new ExtendViewport(800, 480, 1280, 720));
        Gdx.input.setInputProcessor(stage);
        this.game = game;
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
        //DUMP = new basicBlock[9000];
        //MARK FOR TRANSITION

        DUMP2 = new ArrayList<>();

        queuing = new queue(shapeRenderer,25,randomValue);
        PlayerBlock = queuing.DeQueue(shapeRenderer,25);
        QuitButton();
        ThreadData = new passingData(DUMP2,DeadBlock);
        fixBug = new BugFixThread(ThreadData);
        LoseCheckThread = new LoseChecking(DUMP2);
        file = new CSVManager("TESTROOT.csv",1);
        //keys binding
        KeyRight =file.getRIGHTKey();
        keyLeft =file.getLEFT();
        keySoftDrop = file.getDOWN();
        keyHardDrop = file.getSPACE();
        keyHold = file.getHold();
        keyFlip = file.getUP();
        start = System.nanoTime();




        threadPool = Executors.newCachedThreadPool();
        GameP2P = client;
        threadPool.submit(GameP2P);
        NUTJ = new PackerClass(GameP2P,PlayerBlock,DUMP2,shapeRenderer);
        NUTJ.Player = GameP2P.username;
        threadPool.submit(NUTJ);
        StartTime = System.currentTimeMillis();
    }


    @Override
    public void show() {

    }

    public void fpsCounter(SpriteBatch batch){
        FrameCount++;
        //fps counter

        //updates thread data
        font.getData().setScale(1,1);

        long currentTime = System.currentTimeMillis();
        if (currentTime >= (pastTime + 1000)){
            pastTime = System.currentTimeMillis();
            fps  = FrameCount;
            FrameCount = 0;

            font.draw(batch, Integer.toString(FrameCount), 10, 10);
        }
        else{

            font.draw(batch, Integer.toString(fps), 10, 10);
        }

        //fps counter
    }

    public void Counter(SpriteBatch batch){
        //Draws Text and resizes it for the game
        font.getData().setScale(2.2f,1.8f);
        font.draw(batch, "Score: "+Integer.toString(score), 500, 630);
        font.getData().setScale(1.5f,1.25f);
        font.draw(batch, "Speed: "+Integer.toString(OSpeed), 500, 590);
        font.draw(batch, "Lines Sent: "+Integer.toString(totalSent/2), 50, 200);
    }

    public void Player2Counter(SpriteBatch batch){
        //Draws Text and resizes it for the game
        font.getData().setScale(2.2f,1.8f);
        font.draw(batch, "Score: "+Integer.toString(Player2Score), 1080, 630);
        font.getData().setScale(1.5f,1.25f);
        font.draw(batch, "Speed: "+Integer.toString(Player2Speed), 1080, 590);
    }



    private void BLOCKER(){
            //Translator();
            for (int i = 0; i < DUMP2.size(); i++) {
                try {
                    if (DUMP2.get(i) != null) {
                        DUMP2.get(i).draw();
                    }
                }
                catch(Exception e){
                }

        }
            try{
        for (int i = 0; i < Dump3.size(); i++) {
            try {
                if (Dump3.get(i) != null) {
                    Dump3.get(i).draw();
                }
            } catch (Exception e) {
            }
        }
        }catch (Exception e){
            }

    }




    public int logicalArrayLength(basicBlock[] passedArray) {
        int count = 0;
        if (passedArray != null) {
            for (int i = 0; i < passedArray.length; i++) {
                if (passedArray[i] != null) {
                    count++;
                }
            }
        }
        return count;
    }


    public int IntLogicalArrayLength(int[] passedArray) {
        int count = 0;
        for (int i = 0; i < passedArray.length; i++) {
            if (passedArray[i] != 0) {
                count++;
            }
        }
        return count;
    }

    private void Clear(int ClearPoint) {
        int count = 0;
        dumpSize = DUMP2.size();
        for (int i = 0; i < dumpSize; i++) {
            for (int x=0;x<4;x++) {
                try {
                    //boolean Result = DUMP[i].RemoveCube(ClearPoint);
                    if (DUMP2.get(i).cube[x] != null) {
                        if (DUMP2.get(i).cube[x].getY() == ClearPoint) {
                            basicBlock DumpHold = DUMP2.get(i);
                            DumpHold.cube[x] = null;
                            DUMP2.set(i,DumpHold);
                            count++;
                        }
                    }

                } catch (Exception e) {

                }
            }



        }
    }


    //preview of holdhold block
    public void DrawHold(){
        if (queuing.getHold() == null){
        }
        else{
            holdQueue.setX(80);
            holdQueue.setY(475);
            holdQueue.draw();
        }
    }


    //preview of player 2 holdhold block
    public void Player2DrawHold(){
        Player2Hold = NUTJ.getPlayer2hold();
        if (Player2Hold == null){
        }
        else{
            Player2Hold.setX(750);
            Player2Hold.setY(475);
            Player2Hold.draw();
        }
    }




    public void LowerBoard(int lineCleared,int newStart){
        fixBug.UnderFlow();
        if (lineCleared != 0) {
            DeadBlock.BuildArray();
            for (int i = 0; i < dumpSize; i++) {
                DUMP2.get(i).dropY(25 * lineCleared,newStart);
                DeadBlock.DrawTypeRectangle(DUMP2.get(i),DUMP2);
                NUTJ.setDumper(DUMP2);
                NUTJ.setPlayer(PlayerBlock);
                Dump3 =( NUTJ.getDump3());
                Player2Block = NUTJ.getPlayer2();
            }
        }
    }

    public void lineChecks(){
        int[] hold= DeadBlock.CheckClear();
        int LinesCleared = IntLogicalArrayLength(hold);
        for (int i=0;i<LinesCleared;i++) {
            if (hold[i]!= 0) {
                Clear(hold[i]);
            }
            LinesCount++;
        }
        switch (LinesCleared){
            case 1:
                score = score + (100*(OSpeed+1));
                Line1Music.play();
                break;
            case 2:
                score = score + (300*(OSpeed));
                Line2Music.play();
                break;
            case 3:
                score = score + (500*(OSpeed));
                Line3Music.play();
                break;
            case 4:
                score = score + (800*(OSpeed));
                TetrisMusic.play();
                break;
        }
        totalSent += LinesCleared;
        NUTJ.numberLines = totalSent/2;
        LowerBoard(LinesCleared,hold[0]);
    }

    public void lineChecksBlanks(){
        int[] hold= DeadBlock.CheckClearBlanks();
        int LinesCleared = IntLogicalArrayLength(hold);
        int DUMPsIZE = DUMP2.size();
        if (LinesCleared != 0) {
            DeadBlock.BuildArray();
            for (int i = 0; i < DUMPsIZE; i++) {
                DUMP2.get(i).dropY(25,26);
                DeadBlock.DrawTypeRectangle(DUMP2.get(i),DUMP2);
                NUTJ.setDumper(DUMP2);
                NUTJ.setPlayer(PlayerBlock);
                Dump3 =( NUTJ.getDump3());
                Player2Block = NUTJ.getPlayer2();


            }
        }
    }

    public void speedChange(){
        if (LinesCount >= 10){
            OSpeed++;
            if (speedChange > 1) {
                speed--;
            }
            LinesCount = 0;
        }


    }




    public void LosingScreen(float delta){
        LoseCheckThread = new LoseChecking(DUMP2);
        BitmapFont fontTitle = new BitmapFont(Gdx.files.internal("gdx-skins-master/quantum-horizon/raw/font-title-export.fnt"));
        fontTitle.getData().setScale(1.25f,1.25f);
        fontTitle.setColor(Color.BLACK);
        fontTitle.draw(batch, "You lose", 480, 700);
        font = new BitmapFont(Gdx.files.internal("gdx-skins-master/quantum-horizon/raw/font-export.fnt"));
        font.getData().setScale(1.5f,1.5f);
        font.setColor(Color.GOLD);
        font.draw(batch, "Your score was " + score, 350, 440);
        font.setColor(Color.BLACK);
        font.getData().setScale(1.5f,1.5f);
        font.draw(batch, "Your final Speed was " + OSpeed, 325, 410);
        //file handling
        //null name Meaning file tracking is disabled
        currentStatus = true;
        Stage LoseStage = new Stage(new ExtendViewport(800, 480, 1280, 720));
        Gdx.input.setInputProcessor(LoseStage);
        Skin skin = new Skin(Gdx.files.internal(gameConstants.skin));
        Button button2 = new TextButton("Quit",skin);
        Button button3 = new TextButton("Play Again",skin);
        Table table = new Table();
        table.add(button2);
        table.add(button3);
        table.setPosition(550,350);
        button2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                System.out.println("PRESSED");
                quit= true;
                return true;
            }
        });
        button3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                System.out.println("PRESSED");
                game.setScreen(new PracticeModeSelectorMulti(game));
                //dispose
                return true;
            }
        });


        LoseStage.addActor(table);
        LoseStage.act(delta);
        LoseStage.draw();


    }

    public void DrawNextInLine(){
        basicBlock Next1 = queuing.getObjects(queuing.getIndex()).clone();
        basicBlock Next2 = queuing.getObjects(queuing.getIndex()+1).clone();
        basicBlock Next3 = queuing.getObjects(queuing.getIndex()+2).clone();
        if (Next1 != null){
            Next1.setX(573);
            Next1.setY(370);
            Next1.draw();
        }
        if (Next2 != null){
            Next2.setX(573);
            Next2.setY(240);
            Next2.draw();
        }
        if (Next3 != null){
            Next3.setX(573);
            Next3.setY(120);
            Next3.draw();
        }
    }



    @Override
    public void render(float delta) {

        //packetPacker();
        NUTJ.setDumper(DUMP2);
        NUTJ.setPlayer(PlayerBlock);
        NUTJ.thisSpeed = OSpeed;
        NUTJ.score = score;
        NUTJ.SendHold = holdQueue;
        Dump3 =( NUTJ.getDump3());
        Player2Block = NUTJ.getPlayer2();
        Player2Speed = NUTJ.SpeedP2;
        Player2Score = NUTJ.ScoreP2;
        Player2Hold = NUTJ.getPlayer2hold();
        youWin = NUTJ.winCheck;
        NUTJ.currentCheck = currentStatus;
        SentLines();





        //fix bug with clears
        for (int i=0;i<DUMP2.size();i++) {
            try {
                for (int j = 0; i < DUMP2.get(i).cube.length; i++) {
                    if (DUMP2.get(i).cube[j].getY() < 25) {
                        DUMP2.get(i).cube[j] = null;
                    }
                }
            }
            catch ( Exception e){
            }
        }



        if (!loseScreen && !youWin) {
            loseScreen = LoseCheckThread.Lose;
            if (!loseScreen && !youWin) {
                LoseCheckThread = new LoseChecking(DUMP2);
            }
            else{
                LoseCheckThread.interrupt();
                LoseCheckThread = null;
            }
        }
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        //inputs



        if (loseScreen == false && youWin == false) {
            Sleep = Sleep - 100;
            DropTime = DropTime - speed;
            speedChange();


            if (Gdx.input.isKeyPressed(KeyRight) && Sleep < 0 && PlayerBlock.cube[0].getX() != 475 && PlayerBlock.cube[1].getX() != 475 && PlayerBlock.cube[2].getX() != 475 && PlayerBlock.cube[3].getX() != 475) {
                if (DeadBlock.Boarder[PlayerBlock.cube[0].getY() + 1][PlayerBlock.cube[0].getX() + 25] == 0 && DeadBlock.Boarder[PlayerBlock.cube[1].getY() + 1][PlayerBlock.cube[1].getX() + 25] == 0 && DeadBlock.Boarder[PlayerBlock.cube[2].getY() + 1][PlayerBlock.cube[2].getX() + 25] == 0 && DeadBlock.Boarder[PlayerBlock.cube[3].getY() + 1][PlayerBlock.cube[3].getX() + 25] == 0) {
                    x = x + 25;
                    moveMusic.stop();
                    moveMusic.play();
                    PlayerBlock.moveX(25);
                    Sleep = 600;
                }
            }

            if (Gdx.input.isKeyPressed(keyLeft) && Sleep < 0 && PlayerBlock.cube[0].getX() != 250 && PlayerBlock.cube[1].getX() != 250 && PlayerBlock.cube[2].getX() != 250 && PlayerBlock.cube[3].getX() != 250) {
                if (DeadBlock.Boarder[PlayerBlock.cube[0].getY() + 1][PlayerBlock.cube[0].getX() - 25] == 0 && DeadBlock.Boarder[PlayerBlock.cube[1].getY() + 1][PlayerBlock.cube[1].getX() - 25] == 0 && DeadBlock.Boarder[PlayerBlock.cube[2].getY() + 1][PlayerBlock.cube[2].getX() - 25] == 0 && DeadBlock.Boarder[PlayerBlock.cube[3].getY() + 1][PlayerBlock.cube[3].getX() - 25] == 0) {
                    x = x - 25;
                    moveMusic.stop();
                    moveMusic.play();
                    PlayerBlock.moveX(-25);
                    Sleep = 600;
                }
            }

            if (Gdx.input.isKeyPressed(keySoftDrop)) {
                speed = OSpeed + speedChange;

            } else {
                speed = OSpeed;
            }


            if (Gdx.input.isKeyPressed(keyHold) && swap == true) {
                swap = false;
                PlayerBlock = queuing.holdBlockSwap(PlayerBlock, shapeRenderer, 25);
                holdQueue = queuing.getHold().clone();
            }


            if (Gdx.input.isKeyPressed(keyFlip) && Sleep <= 0) {
                boolean flag = false;
                FlipMusic.play();
                PlayerBlock.flip();



                if (PlayerBlock.Shape == 0) {

                } else {
                    while (PlayerBlock.cube[0].getY() < 25 || PlayerBlock.cube[1].getY() < 25 || PlayerBlock.cube[2].getY() < 25 || PlayerBlock.cube[3].getY() < 25) {
                        PlayerBlock.moveY(-25);


                        flag = true;
                    }
                    if (flag) {
                        PlayerBlock.moveY(25);



                    }


                    while (PlayerBlock.cube[0].getX() >= 425 && PlayerBlock.cube[1].getX() >= 425 && PlayerBlock.cube[2].getX() >= 425 && PlayerBlock.cube[3].getX() >= 425) {
                        if (PlayerBlock.cube[0].getX() >= 425 && PlayerBlock.cube[1].getX() >= 425 && PlayerBlock.cube[2].getX() >= 425 && PlayerBlock.cube[3].getX() >= 425) {
                            PlayerBlock.moveX(-25);

                        }
                    }
                    if (PlayerBlock.Shape == 3) {
                        PlayerBlock.moveX(-25);

                    }


                    while (PlayerBlock.cube[0].getX() <= 300 && PlayerBlock.cube[1].getX() <= 300 && PlayerBlock.cube[2].getX() <= 300 && PlayerBlock.cube[3].getX() <= 300) {
                        if (PlayerBlock.cube[0].getX() <= 300 && PlayerBlock.cube[1].getX() <= 300 && PlayerBlock.cube[2].getX() <= 300 && PlayerBlock.cube[3].getX() <= 300) {
                            PlayerBlock.moveX(25);

                        }
                    }
                    //fixes line piece bug causing piece to go off board
                    if (PlayerBlock.Shape == 3) {
                        PlayerBlock.moveX(25);

                    }
                    if (Gdx.input.isKeyPressed(keyLeft) && !PlayerBlock.CheckGreaterX(425) && !PlayerBlock.CheckLessX(300)){
                        PlayerBlock.moveX(-25);

                    }
                    else if(Gdx.input.isKeyPressed(KeyRight) && !PlayerBlock.CheckGreaterX(425) && !PlayerBlock.CheckLessX(300)){
                        PlayerBlock.moveX(25);

                    }


                    while (DeadBlock.Check(PlayerBlock)) {
                        flag = true;
                        if (DeadBlock.Check(PlayerBlock)) {
                            PlayerBlock.moveX(25);
                            if (DeadBlock.Check(PlayerBlock)) {
                                PlayerBlock.moveX(-25);
                                PlayerBlock.moveX(-25);

                                if (DeadBlock.Check(PlayerBlock)) {
                                    PlayerBlock.moveX(25);
                                    PlayerBlock.moveY(-25);

                                }
                            } else {
                                //No issue
                            }
                        }
                    }
                    if (flag) {
                        PlayerBlock.moveY(25);
                    }




                    if ((PlayerBlock.CheckLessX(300) || PlayerBlock.CheckGreaterX(425) || PlayerBlock.cube[0].getY() < 25 || PlayerBlock.cube[1].getY() < 25 || PlayerBlock.cube[2].getY() < 25 || PlayerBlock.cube[3].getY() < 25) && flag){
                        boolean flag1 = false;
                        boolean flag2 = false;
                        boolean flag3 = false;

                       while (PlayerBlock.CheckLessX(300) || DeadBlock.Check(PlayerBlock)){
                           flag1 = true;
                           PlayerBlock.moveX(25);
                           if (PlayerBlock.CheckGreaterX(425)){
                               PlayerBlock.moveX(-125);
                               PlayerBlock.moveY(-25);
                           }
                       }
                       while (PlayerBlock.CheckGreaterX(425) || DeadBlock.Check(PlayerBlock)){
                           flag2 = true;
                           PlayerBlock.moveX(-25);
                           if (PlayerBlock.CheckLessX(300)){
                               PlayerBlock.moveX(125);
                               PlayerBlock.moveY(-25);
                           }
                       }
                       while (PlayerBlock.cube[0].getY() < 25 || PlayerBlock.cube[1].getY() < 25 || PlayerBlock.cube[2].getY() < 25 || PlayerBlock.cube[3].getY() < 25 || DeadBlock.Check(PlayerBlock)){
                           flag3 = true;
                           PlayerBlock.moveY(-25);
                       }
                    }







                }


                Sleep = 900;
            }

            //debug key
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                //Clear(50);
                //DeadBlock.ClearRow(1,51);
                //LoseScreen();
                //score = score + 100000;
                //OSpeed++;

                //NUTJ.numberLines += 1;
            }

            //bugfix
            fixBug.start();
            fixBug = new BugFixThread(ThreadData);



            //the dropping staging
            if (DropTime <= 0 && DropDown == false && DeadBlock.Check(PlayerBlock) == false) {
                if (Gdx.input.isKeyPressed(keySoftDrop)) {
                    score++;
                }
                PlayerBlock.pass();

                DropTime = 50;
            }

            PlayerBlock.draw();

            if (Player2Block != null){
                try {
                    Player2Block.moveX(600);
                    Player2Block.draw();
                }
                catch (Exception e){
                    System.out.println("no player 2");
                }
            }

            if (DeadBlock.Check(PlayerBlock) && DropTime > 0 & grace == false) {
                DropDown = true;
                DropTime = 150;
                grace = true;
            } else if (DeadBlock.Check(PlayerBlock) == false && grace == true) {
                grace = false;
            }


            if (Gdx.input.isKeyPressed(keyHardDrop) && Sleep < 0) {
                while (DeadBlock.Check(PlayerBlock) != true) {
                    if (DropTime <= 0) {
                        PlayerBlock.pass();
                        DropTime = 3;
                        score = score + 2;
                    }
                    DropTime--;
                }
                Sleep = 1500;
                grace = true;

            }


            if (DeadBlock.Check(PlayerBlock) && DropTime <= 0 & grace) {
                NUTJ.setDumper(DUMP2);
                NUTJ.setPlayer(PlayerBlock);

                Dump3 =( NUTJ.getDump3());
                Player2Block = NUTJ.getPlayer2();

                BLOCKER();
                PlaceMusic.play();
                grace = false;
                swap = true;
                DropDown = false;
                //DUMP[index] = hold;
                DUMP2.add(PlayerBlock);
                DeadBlock.add(PlayerBlock,DUMP2);
                lineChecks();
                LoseCheckThread.start();

                //DeadBlock.Output();
                PlayerBlock = queuing.DeQueue(shapeRenderer, 25);
            } else {
                DropDown = false;
            }


            if (PlayerBlock.GetLowestY() == 25 && DropTime <= 0 && !grace) {
                NUTJ.setDumper(DUMP2);
                NUTJ.setPlayer(PlayerBlock);

                Dump3 =( NUTJ.getDump3());
                Player2Block = NUTJ.getPlayer2();

                BLOCKER();
                PlaceMusic.play();
                swap = true;
                //DUMP[index] = hold;
                DUMP2.add(PlayerBlock);
                DeadBlock.add(PlayerBlock,DUMP2);
                //DeadBlock.Output();
                lineChecks();
                PlayerBlock = queuing.DeQueue(shapeRenderer, 25);
            } else if (PlayerBlock.GetLowestY() < 25) {
                NUTJ.setDumper(DUMP2);
                NUTJ.setPlayer(PlayerBlock);

                Dump3 =( NUTJ.getDump3());
                Player2Block = NUTJ.getPlayer2();

                BLOCKER();
                PlaceMusic.play();
                swap = true;
                PlayerBlock.setY(25);
                PlayerBlock.draw();

                DUMP2.add(PlayerBlock);
                DeadBlock.add(PlayerBlock,DUMP2);
                lineChecks();

                //DeadBlock.Output();


                PlayerBlock = queuing.DeQueue(shapeRenderer, 25);
            }

            NUTJ.setDumper(DUMP2);
            NUTJ.setPlayer(PlayerBlock);

            Dump3 =( NUTJ.getDump3());
            Player2Block = NUTJ.getPlayer2();

            BLOCKER();


            //shapeRenderer = new  ShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


            for (int i = 1; i <= 21; i++) {
                shapeRenderer.rect(300, 30 * i, 300, 1);
            }
            for (int i = 4; i <= 14; i++) {
                shapeRenderer.rect(180 + (30 * i), 630, 1, -600);
            }


            for (int i = 1; i <= 21; i++) {
                shapeRenderer.rect(1020, 30 * i, 300, 1);
            }
            for (int i = 4; i <= 14; i++) {
                shapeRenderer.rect(900 + (30 * i), 630, 1, -600);
            }

            shapeRenderer.rect(50, 700, 4, -150);
            shapeRenderer.rect(200, 700, 4, -150);
            shapeRenderer.rect(50, 700, 154, 4);
            shapeRenderer.rect(50, 550, 152, 4);

            shapeRenderer.rect(850, 700, 4, -150);
            shapeRenderer.rect(1000, 700, 4, -150);
            shapeRenderer.rect(850, 700, 154, 4);
            shapeRenderer.rect(850, 550, 152, 4);


            shapeRenderer.rect(620, 550, 4, -430);
            shapeRenderer.rect(770, 550, 4, -430);
            shapeRenderer.rect(620, 550, 154, 4);
            shapeRenderer.rect(620, 120, 152, 4);


        }




        /*
        shapeRenderer.rect(x,y,width,height);
         */

        shapeRenderer.end();


        if (loseScreen) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.21f, 0.227f, 0.78f, 1);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.getActors().clear();
            shapeRenderer.end();
            removeMusic();
        }

        if (loseScreen && write){
            write= false;
            if (!Objects.equals(file.getNAME(), "null")) {
                file.setScore(String.valueOf(score));
                file.setSpeed(String.valueOf(OSpeed));
                //ending in millseconds
                long ending = (System.nanoTime()- start)/1000000;
                file.setTime(String.valueOf(ending));
                file.CsvUpdate();
            }
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (loseScreen != true) {
            font.draw(batch, "Next", 535, 100);
            font.draw(batch,"Player: " + GameP2P.username, 300, 665);
            font.draw(batch,"opponent: " + NUTJ.opponent, 850, 665);
            font.getData().setScale(2.0f, 1.6f);
            font.draw(batch, "Hold", 75, 535);
            font.draw(batch, "P2 Hold", 692, 535);
            fpsCounter(batch);
            font.getData().setScale(1f, 1f);
        }
        else{
            LosingScreen(delta);
        }

        batch.end();
        batch.begin();
        if (loseScreen != true) {
            Counter(batch);
            Player2Counter(batch);
        }
        batch.end();

        DrawHold();
        Player2DrawHold();
        DrawNextInLine();

        if (youWin){
            NUTJ.Results = "Result:"+ String.valueOf(score) + "," + String.valueOf(System.currentTimeMillis() - StartTime) + "," + String.valueOf(NumWin+1)+",";
            NUTJ.Stop(true);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            font.getData().setScale(2f,2f);
            font.draw(batch,"You win,\nYour Results,\n will be sent to server,\nfeel free to leave.\n",200,400);
            batch.end();
        }

        stage.act(delta);
        stage.draw();
        if (quit == true){
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private void SentLines() {
        int sent = NUTJ.totalLines;
        Lines  = sent - TotalRecieved;

        for (int g=0;g<Lines;g++) {
            DeadBlock.BuildArray();
            basicBlock BlockLine = new basicBlock(shapeRenderer,250,25,0,new int[20][20],7);
            DUMP2.add(BlockLine);
            DeadBlock.DrawTypeRectangle(BlockLine,DUMP2);
            for (int i = 0; i < DUMP2.size() - 1; i++) {
                DUMP2.get(i).moveY(-25);
                DUMP2.get(i).y += 25;
                DeadBlock.DrawTypeRectangle(DUMP2.get(i),DUMP2);
                NUTJ.setDumper(DUMP2);
                NUTJ.setPlayer(PlayerBlock);
                Dump3 = (NUTJ.getDump3());
                Player2Block = NUTJ.getPlayer2();

            }
            fixBug.UnderFlow();
        }
        Lines = 0;
        TotalRecieved = sent;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    public void removeMusic(){
        menuMusic.dispose();
        moveMusic.dispose();
        FlipMusic.dispose();
        TetrisMusic.dispose();

    }

    @Override
    public void dispose () {
        System.out.println("hello");
        batch.dispose();
        shapeRenderer = null;
        img.dispose();
        stage.dispose();
        removeMusic();
        queuing = null;
        DUMP2 = null;
        LoseCheckThread.interrupt();
        LoseCheckThread = null;
        holdQueue = null;
        ThreadData =null;
        fixBug.interrupt();
        fixBug = null;
        font = null;
        NUTJ.Stop(true);
        NUTJ = null;
        threadPool.shutdown();
        Dump3 = null;
        Player2Block = null;
        PlayerBlock = null;

    }

    public Texture getBucketImage() {
        return bucketImage;
    }
}
