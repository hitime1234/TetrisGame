package Handling;


import Blocker.BasicCube;
import Blocker.basicBlock;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;

public class PackerClass implements Runnable{
    private Networking GameP2P = null;
    private basicBlock player;
    private ArrayList<basicBlock> dump;
    private ShapeRenderer shapeRenderer;
    private ArrayList<basicBlock> Dump3Temp;
    private basicBlock player2;
    private ArrayList<basicBlock> Dump3;
    public int score =0;
    public int thisSpeed =0;
    public int ScoreP2 =0;
    public int SpeedP2 =0;
    private basicBlock Player2Hold;
    public basicBlock SendHold;
    public basicBlock P1Next1 = null;
    public basicBlock P1Next2 = null;
    public basicBlock P1Next3 = null;
    public basicBlock P2Next1 = null;
    public basicBlock P2Next2 = null;
    public basicBlock P2Next3 = null;
    public boolean StopCode = false;
    public long Time =-1l;
    public boolean allowed  = false;
    private long Temp =-1l;
    public int numberLines =0;
    public int totalLines =0;
    public boolean winCheck = false;
    public boolean currentCheck = false;
    public String opponent = "";
    public String Player = "";
    public String Results = "";

    public void Stop(boolean stopCode) {
        StopCode = true;
    }

    public basicBlock getPlayer2() {
        return player2;
    }

    public basicBlock getPlayer2hold() {
        return Player2Hold;
    }

    public ArrayList<basicBlock> getDump3() {
        return Dump3;
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
        allowed  = true;
        Dump3Temp = new ArrayList<>();

        try {
            //String[] Split = data.split(";::;");
            //System.out.println(data);
            int Temp =0;
            arrayList =gson.fromJson(data, ArrayList.class);
             for (int i=0;i<arrayList.size();i++) {
                try {
                    String hold = arrayList.get(i).toString();
                    ArrayList<String> FinalData = gson.fromJson(hold, ArrayList.class);
                    String SValue = FinalData.get(0);
                    ArrayList Value = gson.fromJson(SValue, ArrayList.class);
                    basicBlock Output = new basicBlock(shapeRenderer, 0, 0, 0, new int[4][4], -1);
                    Output.SetValues(Output.read(Value.get(0).toString()), Integer.parseInt(Value.get(1).toString()), Integer.parseInt(Value.get(2).toString()), Integer.parseInt(Value.get(3).toString()), Boolean.parseBoolean(Value.get(4).toString()));
                    BasicCube[] cubeArray = new BasicCube[10];
                    String SCube1 = FinalData.get(1);
                    String SCube2 = FinalData.get(2);
                    String SCube3 = FinalData.get(3);
                    String SCube4 = FinalData.get(4);
                    cubeArray[0] = gson.fromJson(SCube1, BasicCube.class);
                    cubeArray[1] = gson.fromJson(SCube2, BasicCube.class);
                    cubeArray[2] = gson.fromJson(SCube3, BasicCube.class);
                    cubeArray[3] = gson.fromJson(SCube4, BasicCube.class);
                    if (Output.Shape == 7){
                        String SCube5 = FinalData.get(5);
                        String SCube6 = FinalData.get(6);
                        String SCube7 = FinalData.get(7);
                        String SCube8 = FinalData.get(8);
                        String SCube9 = FinalData.get(9);
                        cubeArray[4] = gson.fromJson(SCube5, BasicCube.class);
                        cubeArray[5] = gson.fromJson(SCube6, BasicCube.class);
                        cubeArray[6] = gson.fromJson(SCube7, BasicCube.class);
                        cubeArray[7] = gson.fromJson(SCube8, BasicCube.class);
                        cubeArray[8] = gson.fromJson(SCube9, BasicCube.class);
                        Output.cube = cubeArray;
                        SpeedP2 = Integer.parseInt(FinalData.get(10));
                        ScoreP2 = Integer.parseInt(FinalData.get(11));
                        Long time = Long.valueOf(FinalData.get(12));
                        totalLines = Integer.parseInt(FinalData.get(13));
                        winCheck = Boolean.parseBoolean(FinalData.get(14));
                        opponent = (FinalData.get(15));
                    }
                    else {
                        Output.cube = cubeArray;
                        SpeedP2 = Integer.parseInt(FinalData.get(5));
                        ScoreP2 = Integer.parseInt(FinalData.get(6));
                        Long time = Long.valueOf(FinalData.get(7));
                        totalLines = Integer.parseInt(FinalData.get(8));
                        winCheck = Boolean.parseBoolean(FinalData.get(9));
                        opponent = (FinalData.get(10));
                    }
                    Dump3Temp.add(Output);
                } catch (Exception e) {
                    //System.out.println("data output fail\n" +e);
                }

            }



             Translator();
             Dump3 = Dump3Temp;
            //DUMP2 = Dump3;


            try {
                    String hold = arrayList.get(Dump3.size()).toString();
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
                    if (allowed) {
                        player2 = Output;
                    }


                try {
                    hold = arrayList.get(Dump3.size()+1).toString();
                    FinalData = gson.fromJson(hold, ArrayList.class);
                    SValue = FinalData.get(0);
                    SCube1 = FinalData.get(1);
                    SCube2 = FinalData.get(2);
                    SCube3 = FinalData.get(3);
                    SCube4 = FinalData.get(4);
                    Value = gson.fromJson(SValue, ArrayList.class);
                    Output = new basicBlock(shapeRenderer, 0, 0, 0, new int[4][4], -1);
                    Output.SetValues(Output.read(Value.get(0).toString()), Integer.parseInt(Value.get(1).toString()), Integer.parseInt(Value.get(2).toString()), Integer.parseInt(Value.get(3).toString()), Boolean.parseBoolean(Value.get(4).toString()));
                    cubeArray = new BasicCube[4];
                    cubeArray[0] = gson.fromJson(SCube1, BasicCube.class);
                    cubeArray[1] = gson.fromJson(SCube2, BasicCube.class);
                    cubeArray[2] = gson.fromJson(SCube3, BasicCube.class);
                    cubeArray[3] = gson.fromJson(SCube4, BasicCube.class);
                    Output.cube = cubeArray;
                    if (allowed) {
                        Player2Hold = Output;
                    }
                }catch (Exception e){

                }




            } catch (Exception e){
                System.out.println(e);
                System.out.println("player block failed to unpack");
            }

        } catch (Exception e){

        }

    }


    public void Translator(){
        try {
            for (int i = 0; i < Dump3Temp.size(); i++){
                if (Dump3Temp != null) {
                    Dump3Temp.get(i).moveX(600);
                }
            }
        } catch (Exception e){
            //System.out.println("nothing in Dump3");
        }
    }


    public String Serialize(){
        //Creating the object
        String data = "";
        ArrayList<String> fullArray = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        for (int i=0;i<dump.size();i++){
            if (dump != null){
                try {
                    ArrayList<String> hold = new ArrayList<>();
                    ArrayList holdClone = dump.get(i).CloneData();
                    String Values = gson.toJson(holdClone);
                    String Cube1 = gson.toJson(dump.get(i).cube[0]);
                    String Cube2 = gson.toJson(dump.get(i).cube[1]);
                    String Cube3 = gson.toJson(dump.get(i).cube[2]);
                    String Cube4 = gson.toJson(dump.get(i).cube[3]);
                    hold.add(Values);hold.add(Cube1);hold.add(Cube2);hold.add(Cube3);hold.add(Cube4);
                    if (dump.get(i).Shape == 7) {
                        String Cube5 = gson.toJson(dump.get(i).cube[4]);
                        String Cube6 = gson.toJson(dump.get(i).cube[5]);
                        String Cube7 = gson.toJson(dump.get(i).cube[6]);
                        String Cube8 = gson.toJson(dump.get(i).cube[7]);
                        String Cube9 = gson.toJson(dump.get(i).cube[8]);
                        hold.add(Cube5);hold.add(Cube6);hold.add(Cube7);hold.add(Cube8);hold.add(Cube9);
                    }
                    hold.add(String.valueOf(thisSpeed));
                    hold.add(String.valueOf(score));
                    //Getting the current date
                    Date date = new Date();
                    //This method returns the time in millis
                    long timeMilli = date.getTime();
                    hold.add(String.valueOf(timeMilli));
                    hold.add(String.valueOf(numberLines));
                    hold.add(String.valueOf(currentCheck));
                    hold.add(String.valueOf(Player));
                    fullArray.add(gson.toJson(hold));
                } catch (Exception e){
                    System.out.println("error not able store this data");
                }
            }
        }

        try {
            //Adds playerBlock
            ArrayList<String> hold = new ArrayList<>();
            ArrayList holdClone = player.CloneData();
            String Values  = gson.toJson(holdClone);
            String Cube1 = gson.toJson(player.cube[0]);
            String Cube2 = gson.toJson(player.cube[1]);
            String Cube3 = gson.toJson(player.cube[2]);
            String Cube4 = gson.toJson(player.cube[3]);
            hold.add(Values);hold.add(Cube1);hold.add(Cube2);hold.add(Cube3);hold.add(Cube4);
            fullArray.add(gson.toJson(hold));

            // hold block

            try {
                hold = new ArrayList<>();
                holdClone = SendHold.CloneData();
                Values = gson.toJson(holdClone);
                Cube1 = gson.toJson(SendHold.cube[0]);
                Cube2 = gson.toJson(SendHold.cube[1]);
                Cube3 = gson.toJson(SendHold.cube[2]);
                Cube4 = gson.toJson(SendHold.cube[3]);
                hold.add(Values);
                hold.add(Cube1);
                hold.add(Cube2);
                hold.add(Cube3);
                hold.add(Cube4);
                fullArray.add(gson.toJson(hold));
            } catch (Exception e){

            }
















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

    public void setPlayer(basicBlock player) {
        this.player = player;
    }

    public void setDumper(ArrayList<basicBlock> dump) {
        this.dump = dump;
    }

    public PackerClass(Networking client, basicBlock player, ArrayList<basicBlock> dump, ShapeRenderer shapeRenderer){
        this.GameP2P = client;
        this.player = player;
        this.dump = dump;
        this.shapeRenderer = shapeRenderer;
    }



    @Override
    public void run() {
        while (true) {
            if (StopCode == true){
                GameP2P.sendOFF(Results);
                break;
            }
            else {
                String hold = Serialize();
                GameP2P.sendOFF(hold);
                String Data = GameP2P.GetRecv();
                unSerialize(Data);
            }
        }
    }
}
