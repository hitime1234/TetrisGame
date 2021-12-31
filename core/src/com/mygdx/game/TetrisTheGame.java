package com.mygdx.game;

import Blocker.BasicCube;
import Blocker.basicBlock;
import Blocker.board;
import Blocker.queue;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import jdk.tools.jmod.Main;

import javax.sound.sampled.Line;
import java.awt.*;
import java.io.*;

import static com.mygdx.game.gameConstants.*;

public class TetrisTheGame extends ScreenAdapter implements Screen  {
    private final Stage stage;
    private final queue queuing;
    private final board DeadBlock;
    private final MyGdxGame game;
    private final Music menuMusic;
    private int index;
    private basicBlock hold;
    private ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    Texture img;
    private boolean quit = false;
    private OrthographicCamera camera;
    private Texture bucketImage;
    private int speed = 1;
    private boolean grace = false;
    private int OSpeed = 1;
    private int randomV = 0;
    private float y;
    private int score =0;
    private float x;
    private float width;
    private int dumpSize = 0;
    private boolean swap = true;
    private float height;
    private int Sleep =0;
    private basicBlock[] DUMP;
    private basicBlock tempBlock;
    private int DropTime = 50;
    boolean DropDown = false;
    private int LinesCount = 0;
    private int FrameCount =0;
    private long pastTime = System.currentTimeMillis();
    private long currentTime = System.currentTimeMillis();
    public int fps = 0;
    private int speedChange = 20;
    private basicBlock holdQueue;

    public void LoseScreen(){

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
        table.setPosition(335,-180);
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

        speed = speedInt;
        OSpeed = speedInt;
        randomV =randomValue;
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
        DUMP = new basicBlock[9000];
        queuing = new queue(shapeRenderer,25);
        hold = queuing.DeQueue(shapeRenderer,25);
        index = 0;
        QuitButton();
    }


    @Override
    public void show() {

    }

    public void fpsCounter(SpriteBatch batch){
        FrameCount++;
        //fps counter
        currentTime = System.currentTimeMillis();
        if (currentTime >= (pastTime + 1000)){
            pastTime = System.currentTimeMillis();
            fps  = FrameCount;
            FrameCount = 0;
            BitmapFont font = new BitmapFont();

            font.draw(batch, Integer.toString(FrameCount), 10, 10);
        }
        else{
            BitmapFont font = new BitmapFont();

            font.draw(batch, Integer.toString(fps), 10, 10);
        }

        //fps counter
    }

    public void Counter(SpriteBatch batch){
        //Draws Text and resizes it for the game
        BitmapFont font = new BitmapFont();
        font.getData().setScale(3,3);
        font.draw(batch, "Score: "+Integer.toString(score), 1000, 600);
        font.getData().setScale(2,2);
        font.draw(batch, "speed: "+Integer.toString(OSpeed), 1000, 650);

    }




    private void BLOCKER(){
            for (int i = 0; i < logicalArrayLength(DUMP); i++) {
                try {
                    DUMP[i].draw();
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
        dumpSize = logicalArrayLength(DUMP);
        for (int i = 0; i < dumpSize; i++) {
            for (int x=0;x<4;x++) {
                try {
                    //boolean Result = DUMP[i].RemoveCube(ClearPoint);
                    if (DUMP[i].cube[x] != null) {
                        if (DUMP[i].cube[x].getY() == ClearPoint) {
                            DUMP[i].cube[x] = null;
                            count++;
                        }
                    }

                } catch (Exception e) {

                }
            }



        }
    }


    //preview of hold block
    public void DrawHold(){
        if (queuing.getHold() == null){
        }
        else{
            holdQueue.setX(80);
            holdQueue.setY(475);
            holdQueue.draw();
        }
    }



    public void LowerBoard(int lineCleared,int newStart){
        if (lineCleared != 0) {
            DeadBlock.BuildArray();
            for (int i = 0; i < dumpSize; i++) {
                DUMP[i].dropY(25 * lineCleared,newStart);
                DeadBlock.DrawTypeRectangle(DUMP[i]);
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
                break;
            case 2:
                score = score + (300*(OSpeed));
                break;
            case 3:
                score = score + (500*(OSpeed));
                break;
            case 4:
                score = score + (800*(OSpeed));
                break;
        }
        LowerBoard(LinesCleared,hold[0]);
    }

    public void lineChecksBlanks(){
        int[] hold= DeadBlock.CheckClearBlanks();
        int LinesCleared = IntLogicalArrayLength(hold);
        int DUMPsIZE = logicalArrayLength(DUMP);
        if (LinesCleared != 0) {
            DeadBlock.BuildArray();
            for (int i = 0; i < DUMPsIZE; i++) {
                DUMP[i].dropY(25,26);
                DeadBlock.DrawTypeRectangle(DUMP[i]);
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


    public void loseCheck(){

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        //inputs
        Sleep = Sleep - 100;
        DropTime = DropTime - speed;
        speedChange();




        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Sleep < 0 && hold.cube[0].getX() != 475 && hold.cube[1].getX() != 475 && hold.cube[2].getX() != 475 && hold.cube[3].getX()!= 475 ){
            if (DeadBlock.Boarder[hold.cube[0].getY()+1][hold.cube[0].getX()+25] == 0 && DeadBlock.Boarder[hold.cube[1].getY()+1][hold.cube[1].getX()+25] == 0 && DeadBlock.Boarder[hold.cube[2].getY()+1][hold.cube[2].getX()+25] == 0 && DeadBlock.Boarder[hold.cube[3].getY()+1][hold.cube[3].getX()+25] == 0 ) {
                x = x + 25;
                hold.moveX(25);
                Sleep = 600;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Sleep < 0 && hold.cube[0].getX() != 250 && hold.cube[1].getX() != 250 && hold.cube[2].getX() != 250 && hold.cube[3].getX() != 250){
            if (DeadBlock.Boarder[hold.cube[0].getY()+1][hold.cube[0].getX()-25] == 0 && DeadBlock.Boarder[hold.cube[1].getY()+1][hold.cube[1].getX()-25] == 0 && DeadBlock.Boarder[hold.cube[2].getY()+1][hold.cube[2].getX()-25] == 0 && DeadBlock.Boarder[hold.cube[3].getY()+1][hold.cube[3].getX()-25] == 0 ) {
                x = x - 25;
                hold.moveX(-25);
                Sleep = 600;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            speed = OSpeed + speedChange;

        }
        else{
            speed = OSpeed;
        }




        if (Gdx.input.isKeyPressed(Input.Keys.C) && swap == true){
            swap = false;
            hold = queuing.holdBlockSwap(hold,shapeRenderer,25);
            holdQueue = queuing.getHold().clone();
        }




        if (Gdx.input.isKeyPressed(Input.Keys.UP) && Sleep <= 0) {
            boolean flag = false;
            hold.flip();


            if (hold.Shape == 0) {

            }
            else {
                while (hold.cube[0].getY() < 25 || hold.cube[1].getY() < 25 || hold.cube[2].getY() < 25 || hold.cube[3].getY() < 25){
                    hold.moveY(-25);
                    flag = true;
                }
                if (flag){
                    hold.moveY(25);
                }


                while (hold.cube[0].getX() > 425 && hold.cube[1].getX() > 425 && hold.cube[2].getX() > 425 && hold.cube[3].getX() > 425) {
                    if (hold.cube[0].getX() > 425 && hold.cube[1].getX() > 425 && hold.cube[2].getX() > 425 && hold.cube[3].getX() > 425) {
                        hold.moveX(-25);
                    }
                }
                if (hold.Shape==3){
                    hold.moveX(-25);
                }



                while (hold.cube[0].getX() < 300 && hold.cube[1].getX() < 300 && hold.cube[2].getX() < 300 && hold.cube[3].getX() < 300) {
                    if (hold.cube[0].getX() < 300 && hold.cube[1].getX() < 300 && hold.cube[2].getX() < 300 && hold.cube[3].getX() < 300) {
                        hold.moveX(25);
                    }
                }
                //fixes line piece bug causing piece to go off board
                if (hold.Shape==3){
                    hold.moveX(25);
                }


                while (DeadBlock.Check(hold)){
                    hold.moveY(-25);
                    flag = true;
                }
                if (flag){
                    hold.moveY(25);
                }

            }



            Sleep = 900;
        }

        /**
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)){
            Clear(50);
            DeadBlock.ClearRow(1,51);
        }
         */


        //the dropping staging
        if (DropTime <= 0 && DropDown == false && DeadBlock.Check(hold) == false) {
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                score++;
            }
            hold.pass();
            DropTime = 50;
        }

        hold.draw();



        if (DeadBlock.Check(hold) && DropTime > 0 & grace == false){
            DropDown = true;
            DropTime = 150;
            grace = true;
        }



        else if (DeadBlock.Check(hold) == false && grace == true ){
            grace = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && Sleep < 0){
            while (DeadBlock.Check(hold) != true) {
                if (DropTime <= 0) {
                    hold.pass();
                    DropTime = 2;
                    score = score + 2;
                }
                DropTime--;
            }
            Sleep = 1500;
            grace = true;
        }


        if (DeadBlock.Check(hold) && DropTime <= 0 & grace == true){
            grace = false;
            swap = true;
            DropDown = false;
            DUMP[index] = hold;
            DeadBlock.add(hold);
            lineChecks();
            lineChecksBlanks();

            //DeadBlock.Output();
            hold = queuing.DeQueue(shapeRenderer,25);
            index++;
        }
        else{
            DropDown = false;
        }




        if (hold.GetLowestY() ==25 && DropTime <= 0 && grace == false){
            swap = true;
            DUMP[index] = hold;
            DeadBlock.add(hold);
            //DeadBlock.Output();
            lineChecks();
            lineChecksBlanks();
            hold = queuing.DeQueue(shapeRenderer,25);
            index++;
        }

        else if (hold.GetLowestY() < 25){
            swap = true;
            hold.setY(25);
            hold.draw();
            DUMP[index] = hold;
            DeadBlock.add(hold);
            lineChecks();
            lineChecksBlanks();

            //DeadBlock.Output();


            hold = queuing.DeQueue(shapeRenderer,25);
            index++;
        }



        BLOCKER();




        //shapeRenderer = new  ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (y > 0) {
            y = y - speed;
        }
        else{
            //new shape
            shapeRenderer.rect(x,300,width,height);

        }

        for (int i=1;i<=21;i++){
            shapeRenderer.rect(300,30*i,300,1);
        }
        for (int i=4;i<=14;i++) {
            shapeRenderer.rect(180+(30*i), 630, 1, -600);
        }

        shapeRenderer.rect(50, 700, 4, -150);
        shapeRenderer.rect(200, 700, 4, -150);
        shapeRenderer.rect(50, 700, 154, 4);
        shapeRenderer.rect(50, 550, 152, 4);

        /**
        shapeRenderer.rect(x,y,width,height);
         */

        shapeRenderer.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        fpsCounter(batch);
        batch.end();
        batch.begin();
        Counter(batch);
        batch.end();

        DrawHold();


        stage.act(delta);
        stage.draw();
        if (quit == true){
            game.setScreen(new MainMenuScreen(game));
            dispose();

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

    @Override
    public void dispose () {
        System.out.println("hello");
        batch.dispose();
        shapeRenderer = null;
        img.dispose();
        stage.dispose();
        menuMusic.dispose();

    }
}
