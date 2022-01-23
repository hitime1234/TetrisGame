package Handling;

import com.badlogic.gdx.Input;


import java.io.*;
import java.util.ArrayList;

public class CSVManager {
    private final String FileName;
    public int Error = 0;
    public String directory = System.getProperty("user.home");
    public String version = "0.0.5v";
    public String placeholder;
    private int start = 0;
    public int index =0;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> Data = new ArrayList<>();








    public void splitUP(String line){
            String[] part = line.split(",");
            for (int i=0;i< part.length;i++) {
                list.add(part[i]);
            }
    }

    public void splitUPData(String line){
        Data = new ArrayList<>();
        String[] part = line.split(",");
        for (int i=0;i< part.length;i++) {
            Data.add(part[i]);
        }
    }



    public void ReadCSV(String Filename){
        String absolutePath = directory + System.getProperty("file.separator") + Filename;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutePath))) {
            String line = bufferedReader.readLine();
            if (line !=null) {
                splitUP(line);
            }
            line = bufferedReader.readLine();
            if (line !=null) {
                splitUPData(line);
            }
            bufferedReader.close();
            System.out.println(list.get(0));
        } catch (FileNotFoundException e) {
            // Exception handling
            Error = -1;
        } catch (IOException e) {
            // Exception handling
            Error = -1;
        }
    }

    public void ForceWrite(String Filename,String dd) {
        try(FileWriter fileWriter = new FileWriter(directory + System.getProperty("file.separator") + "temp.txt")) {
            fileWriter.write(Filename);
            fileWriter.write("\n\n\n");
            fileWriter.write(dd);
            fileWriter.close();
        } catch (IOException e){

        }
    }


    public void CreateCSV(String Filename){
        // Write the content in file
        String absolutePath = directory + System.getProperty("file.separator") + Filename;
        //time in seconds
        //application options top level
        //user data
        //settings


        try(FileWriter fileWriter = new FileWriter(absolutePath)) {
            String fileContent = version + ","
                    +  directory + ","
                    + Returner(2) + ","
                    + Returner(3) + ","
                    + Returner(4) + ","
                    + Returner(5) + ",";
            fileWriter.write(fileContent);

            if (Data != null){
                String Build = "";
                for (int i=0;i<=Data.size()-1;i++){
                    Build = Build + Data.get(i)+",";
                }
                fileWriter.write("\n"+Build);
            }

            fileWriter.close();
        } catch (IOException e) {
            // exception handling
             Error = -1;
        }


    }

    public CSVManager(String File,int Type){
        FileName = File;
        switch (Type) {
            case 1:
            //read information
                ReadCSV(File);
                if (Error == -1){
                }
                else {
                    break;
                }
            case 0:
            default:
                //create new file
                list.add(version);
                list.add(directory);
                list.add("0");
                list.add("null");
                list.add("0");
                list.add("1");
                //KeyCodes for default controls
                try {
                    // Flip up Arrow - 19
                    Data = new ArrayList<>();
                    Data.add(String.valueOf(Input.Keys.UP));
                    // Soft drop, down arrow - 20
                    Data.add(String.valueOf(Input.Keys.DOWN));
                    // left move - 21
                    Data.add(String.valueOf(Input.Keys.LEFT));
                    // right move - 22
                    Data.add(String.valueOf(Input.Keys.RIGHT));
                    // hard Drop -62
                    Data.add (String.valueOf(Input.Keys.SPACE));
                    //hold - 31
                    Data.add (String.valueOf(Input.Keys.C));
                } catch (NullPointerException e) {System.out.println("error" + e);}
                CreateCSV(File);
                ReadCSV(File);
                break;
        }
    }

    public String getSpeed(){
        return Returner(5);
    }
    public String getTime(){
        return Returner(4);
    }

    public String getNAME(){
        return Returner(3);
    }

    public void setScore(String data){
        Sender(2,data);
    }

    public void setSpeed(String data){
        Sender(5,data);
    }
    public void  setTime(String data){
        Sender(4,data);
    }

    public void setNAME(String data){
        Sender(3,data);
    }
    public void getScore(String data){
        Sender(2,data);
    }

    public String Returner(int index){
        return list.get(index);
    }
    public void Sender(int index,String data){
        list.set(index,data);
    }



    public void CsvUpdate(){
        CreateCSV(FileName);
        ReadCSV(FileName);
    }

    public int getUP(){
        return Integer.valueOf(Data.get(0));
    }

    public int getDOWN(){
        return Integer.valueOf(Data.get(1));
    }

    public int getLEFT(){
        return Integer.valueOf(Data.get(2));
    }
    public int getRIGHTKey(){
        return Integer.valueOf(Data.get(3));
    }

    public int getSPACE(){
        return Integer.valueOf(Data.get(4));
    }
    public int getHold(){
        return Integer.valueOf(Data.get(5));
    }

    public void setUP(int key){
        Data.set(0,String.valueOf(key));
    }

    public void setDOWN(int key){
        Data.set(1,String.valueOf(key));
    }

    public void setLEFT(int key){
        Data.set(2,String.valueOf(key));
    }
    public void setRIGHTKey(int key){
        Data.set(3,String.valueOf(key));
    }

    public void setSPACE(int key){
        Data.set(4,String.valueOf(key));
    }
    public void setHoldKey(int key){
        Data.set(5,String.valueOf(key));
    }


}

