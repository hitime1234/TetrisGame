package com.mygdx.game;

import Blocker.basicBlock;
import Blocker.board;
import Blocker.queue;
import Handling.BugFixThread;
import Handling.CSVManager;
import Handling.LoseChecking;
import Handling.passingData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.ArrayList;


public class TetrisTheGame extends ScreenAdapter implements Screen  {

    private final Stage stage;
    private queue queuing;
    private final long start;
    private ArrayList<basicBlock> DUMP2;
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
    private basicBlock hold;
    private ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture img;
    private boolean quit = false;
    private final OrthographicCamera camera;
    private int speed ;
    private boolean grace = false;
    private int OSpeed ;
    private int score =0;
    private float x;
    private int dumpSize = 0;
    private boolean swap = true;
    private int Sleep =0;

    private int DropTime = 50;
    boolean DropDown = false;
    private int LinesCount = 0;
    private int FrameCount =0;
    private long pastTime = System.currentTimeMillis();
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
    private BitmapFont font;


    public void QuitButton(){
        Skin skin = new Skin(Gdx.files.internal(gameConstants.skin));
        Button button2 = new TextButton("Quit",skin);
        button2.setTransform(true);
        Table table = new Table();
        table.setFillParent(true);
        table.setTransform(true);
        table.setScale(0.9f);
        table.add(button2);
        table.setPosition(335,-230);
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

    public TetrisTheGame(MyGdxGame game,int speedInt,int randomValue) {
        Gdx.graphics.setWindowedMode(900, 720);

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
        Texture bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        stage = new Stage(new ExtendViewport(800, 480, 1280, 720));
        Gdx.input.setInputProcessor(stage);
        this.game = game;
        camera.setToOrtho(false, 1280, 720);
        camera.update();
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        float height = 50;
        float width = 20;
        x= 300;
        float y = 300;
        DeadBlock = new board();
        //ABOSLOUTE DUMPTRUCK
        //DUMP = new basicBlock[9000];
        //MARK FOR TRANSITION

        DUMP2 = new ArrayList<>();

        queuing = new queue(shapeRenderer,25,randomValue);
        hold = queuing.DeQueue(shapeRenderer,25);
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


    }


    @Override
    public void show() {

    }

    public void fpsCounter(SpriteBatch batch){
        FrameCount++;
        //fps counter

        //updates thread data
        font.getData().setScale(1,1);
        //font = new BitmapFont();
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
        font.draw(batch, "Score: "+ score, 880, 600);
        font.getData().setScale(1.5f,1.25f);
        font.draw(batch, "speed: "+ OSpeed, 880, 630);
        font.getData().setScale(1,1);

    }




    private void BLOCKER(){
            for (int i = 0; i < DUMP2.size(); i++) {
                try {
                    if (DUMP2.get(i) != null) {
                        DUMP2.get(i).draw();
                    }
                }
                catch(Exception e){

                }
        }
    }

    public int logicalArrayLength(basicBlock[] passedArray) {
        int count = 0;
        for (int i = 0; i < passedArray.length; i++) {
            if (passedArray[i] != null) {
                count++;
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





    public void LowerBoard(int lineCleared,int newStart){
        if (lineCleared != 0) {
            DeadBlock.BuildArray();
            for (int i = 0; i < dumpSize; i++) {
                DUMP2.get(i).dropY(25 * lineCleared,newStart);
                DeadBlock.DrawTypeRectangle(DUMP2.get(i));
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
                DeadBlock.DrawTypeRectangle(DUMP2.get(i));
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
        fontTitle.draw(batch, "You lose", 470, 600);
        font = new BitmapFont(Gdx.files.internal("gdx-skins-master/quantum-horizon/raw/font-export.fnt"));
        font.getData().setScale(1.5f,1.5f);
        font.setColor(Color.GOLD);
        font.draw(batch, "Your score was " + score, 300, 500);
        font.setColor(Color.BLACK);
        font.getData().setScale(1.5f,1.5f);
        font.draw(batch, "Your final Speed was " + OSpeed, 250, 450);
        //file handling
        //null name Meaning file tracking is disabled

        Stage LoseStage = new Stage(new ExtendViewport(800, 480, 1280, 720));
        Gdx.input.setInputProcessor(LoseStage);
        Skin skin = new Skin(Gdx.files.internal(gameConstants.skin));
        Button button2 = new TextButton("Quit",skin);
        Button button3 = new TextButton("Play Again",skin);
        Table table = new Table();
        table.add(button2);
        table.add(button3);
        table.setPosition(460,300);
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
                game.setScreen(new PracticeModeSelector(game));
                return true;
            }
        });


        LoseStage.addActor(table);
        LoseStage.act(delta);
        LoseStage.draw();


    }

    @Override
    public void render(float delta) {

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


        if (loseScreen == false) {
            loseScreen = LoseCheckThread.Lose;

            if (loseScreen == false) {
                LoseCheckThread = new LoseChecking(DUMP2);
            }
            else{
                LoseCheckThread.interrupt();
                LoseCheckThread = null;
            }
        }
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        //inputs





        if (loseScreen == false) {
            Sleep = Sleep - 100;
            DropTime = DropTime - speed;
            speedChange();


            if (Gdx.input.isKeyPressed(KeyRight) && Sleep < 0 && hold.cube[0].getX() != 475 && hold.cube[1].getX() != 475 && hold.cube[2].getX() != 475 && hold.cube[3].getX() != 475) {
                if (DeadBlock.Boarder[hold.cube[0].getY() + 1][hold.cube[0].getX() + 25] == 0 && DeadBlock.Boarder[hold.cube[1].getY() + 1][hold.cube[1].getX() + 25] == 0 && DeadBlock.Boarder[hold.cube[2].getY() + 1][hold.cube[2].getX() + 25] == 0 && DeadBlock.Boarder[hold.cube[3].getY() + 1][hold.cube[3].getX() + 25] == 0) {
                    x = x + 25;
                    moveMusic.stop();
                    moveMusic.play();
                    hold.moveX(25);
                    Sleep = 600;
                }
            }

            if (Gdx.input.isKeyPressed(keyLeft) && Sleep < 0 && hold.cube[0].getX() != 250 && hold.cube[1].getX() != 250 && hold.cube[2].getX() != 250 && hold.cube[3].getX() != 250) {
                if (DeadBlock.Boarder[hold.cube[0].getY() + 1][hold.cube[0].getX() - 25] == 0 && DeadBlock.Boarder[hold.cube[1].getY() + 1][hold.cube[1].getX() - 25] == 0 && DeadBlock.Boarder[hold.cube[2].getY() + 1][hold.cube[2].getX() - 25] == 0 && DeadBlock.Boarder[hold.cube[3].getY() + 1][hold.cube[3].getX() - 25] == 0) {
                    x = x - 25;
                    moveMusic.stop();
                    moveMusic.play();
                    hold.moveX(-25);
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
                hold = queuing.holdBlockSwap(hold, shapeRenderer, 25);
                holdQueue = queuing.getHold().clone();
            }


            if (Gdx.input.isKeyPressed(keyFlip) && Sleep <= 0) {
                boolean flag = false;
                FlipMusic.play();
                hold.flip();


                if (hold.Shape == 0) {

                } else {
                    while (hold.cube[0].getY() < 25 || hold.cube[1].getY() < 25 || hold.cube[2].getY() < 25 || hold.cube[3].getY() < 25) {
                        hold.moveY(-25);
                        flag = true;
                    }
                    if (flag) {
                        hold.moveY(25);
                    }


                    while (hold.cube[0].getX() >= 425 && hold.cube[1].getX() >= 425 && hold.cube[2].getX() >= 425 && hold.cube[3].getX() >= 425) {
                        if (hold.cube[0].getX() >= 425 && hold.cube[1].getX() >= 425 && hold.cube[2].getX() >= 425 && hold.cube[3].getX() >= 425) {
                            hold.moveX(-25);
                        }
                    }
                    if (hold.Shape == 3) {
                        hold.moveX(-25);
                    }


                    while (hold.cube[0].getX() < 300 && hold.cube[1].getX() < 300 && hold.cube[2].getX() < 300 && hold.cube[3].getX() < 300) {
                        if (hold.cube[0].getX() < 300 && hold.cube[1].getX() < 300 && hold.cube[2].getX() < 300 && hold.cube[3].getX() < 300) {
                            hold.moveX(25);
                        }
                    }
                    //fixes line piece bug causing piece to go off board
                    if (hold.Shape == 3) {
                        hold.moveX(25);
                    }

                    if (Gdx.input.isKeyPressed(keyLeft) && !hold.CheckGreaterX(425) && !hold.CheckLessX(300)){
                        hold.moveX(-25);

                    }
                    else if(Gdx.input.isKeyPressed(KeyRight) && !hold.CheckGreaterX(425) && !hold.CheckLessX(300)){
                        hold.moveX(25);

                    }


                    while (DeadBlock.Check(hold)) {
                        flag = true;
                        if (DeadBlock.Check(hold)) {
                            hold.moveX(25);
                            if (DeadBlock.Check(hold)) {
                                hold.moveX(-25);
                                hold.moveX(-25);

                                if (DeadBlock.Check(hold)) {
                                    hold.moveX(25);
                                    hold.moveY(-25);

                                }
                            } else {
                                //No issue
                            }
                        }
                    }
                    if (flag) {
                        hold.moveY(25);
                    }




                    if ((hold.CheckLessX(300) || hold.CheckGreaterX(425) || hold.cube[0].getY() < 25 || hold.cube[1].getY() < 25 || hold.cube[2].getY() < 25 || hold.cube[3].getY() < 25) && flag){
                        boolean flag1 = false;
                        boolean flag2 = false;
                        boolean flag3 = false;

                        while (hold.CheckLessX(300) || DeadBlock.Check(hold)){
                            flag1 = true;
                            hold.moveX(25);
                            if (hold.CheckGreaterX(425)){
                                hold.moveX(-125);
                                hold.moveY(-25);
                            }
                        }
                        while (hold.CheckGreaterX(425) || DeadBlock.Check(hold)){
                            flag2 = true;
                            hold.moveX(-25);
                            if (hold.CheckLessX(300)){
                                hold.moveX(125);
                                hold.moveY(-25);
                            }
                        }
                        while (hold.cube[0].getY() < 25 || hold.cube[1].getY() < 25 || hold.cube[2].getY() < 25 || hold.cube[3].getY() < 25 || DeadBlock.Check(hold)){
                            flag3 = true;
                            hold.moveY(-25);
                        }
                    }

                }


                Sleep = 900;
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                Clear(50);
                DeadBlock.ClearRow(1,51);
                //LoseScreen();
                //score = score + 100000;
                //OSpeed++;
            }

            //bugfix
            fixBug.start();
            fixBug = new BugFixThread(ThreadData);



            //the dropping staging
            if (DropTime <= 0 && DropDown == false && DeadBlock.Check(hold) == false) {
                if (Gdx.input.isKeyPressed(keySoftDrop)) {
                    score++;
                }
                hold.pass();

                DropTime = 50;
            }

            hold.draw();



            if (DeadBlock.Check(hold) && DropTime > 0 & grace == false) {
                DropDown = true;
                DropTime = 100;
                grace = true;
            } else if (DeadBlock.Check(hold) == false && grace == true && DropTime < 10) {
                grace = false;
            }


            if (Gdx.input.isKeyPressed(keyHardDrop) && Sleep < 0) {
                while (DeadBlock.Check(hold) != true) {
                    if (DropTime <= 0) {
                        hold.pass();
                        DropTime = 3;
                        score = score + 2;
                    }
                    DropTime--;
                }
                Sleep = 1500;
                grace = true;

            }


            if (DeadBlock.Check(hold) && DropTime <= 0 & grace == true) {
                PlaceMusic.play();
                grace = false;
                swap = true;
                DropDown = false;
                //DUMP[index] = hold;
                DUMP2.add(hold);
                DeadBlock.add(hold);
                lineChecks();
                LoseCheckThread.start();
                //DeadBlock.Output();
                hold = queuing.DeQueue(shapeRenderer, 25);
            } else {
                DropDown = false;
            }


            if (hold.GetLowestY() == 25 && DropTime <= 0 && grace == false) {
                PlaceMusic.play();
                swap = true;
                //DUMP[index] = hold;
                DUMP2.add(hold);
                DeadBlock.add(hold);
                //DeadBlock.Output();
                lineChecks();
                hold = queuing.DeQueue(shapeRenderer, 25);
            } else if (hold.GetLowestY() < 25) {
                PlaceMusic.play();
                swap = true;
                hold.setY(25);
                hold.draw();
                DUMP2.add(hold);
                DeadBlock.add(hold);
                lineChecks();

                //DeadBlock.Output();


                hold = queuing.DeQueue(shapeRenderer, 25);
            }


            BLOCKER();


            //shapeRenderer = new  ShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


            for (int i = 1; i <= 21; i++) {
                shapeRenderer.rect(300, 30 * i, 300, 1);
            }
            for (int i = 4; i <= 14; i++) {
                shapeRenderer.rect(180 + (30 * i), 630, 1, -600);
            }

            shapeRenderer.rect(50, 700, 4, -150);
            shapeRenderer.rect(200, 700, 4, -150);
            shapeRenderer.rect(50, 700, 154, 4);
            shapeRenderer.rect(50, 550, 152, 4);

            shapeRenderer.rect(620, 550, 4, -430);
            shapeRenderer.rect(770, 550, 4, -430);
            shapeRenderer.rect(620, 550, 154, 4);
            shapeRenderer.rect(620, 120, 152, 4);

        }




        /**
        shapeRenderer.rect(x,y,width,height);
         */

        shapeRenderer.end();



        if (loseScreen) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.21f, 0.227f, 0.78f, 1);
            shapeRenderer.rect(0, 0, 1000, 1000);
            stage.getActors().clear();
            shapeRenderer.end();
            removeMusic();
        }

        if (loseScreen && write){
            write= false;
            if (file.getNAME() != "null") {
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
            font.getData().setScale(2.3f, 1.8f);
            font.draw(batch, "Hold", 155, 525);
            fpsCounter(batch);
        }
        else{
            LosingScreen(delta);
        }

        batch.end();
        batch.begin();




        if (loseScreen != true) {
            Counter(batch);
        }
        batch.end();

        DrawHold();
        DrawNextInLine();


        stage.act(delta);
        stage.draw();

        if (quit == true){
            dispose();
            game.setScreen(new MainMenuScreen(game));

        }
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



    }
}
