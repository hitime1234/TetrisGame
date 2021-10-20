package Handling;

import java.io.*;
import java.util.ArrayList;

public class CSVManager {
    public int Error = 0;
    private String directory = System.getProperty("user.home");
    public String version;
    public String placeholder;
    private int start = 0;
    public int index =0;
    private ArrayList<String> list;

    public void splitUP(String line){
            list = new ArrayList<String>();
            for (int i=0;i<line.length();i++) {
                if (line.charAt(i) == ',') {
                    list.add(line.substring(start, i));
                    start = i + 1;
                    index++;
                }
            }
    }

    public void ReadCSV(String Filename){
        String absolutePath = directory + System.getProperty("file.separator") + Filename;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutePath))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                splitUP(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // Exception handling
            Error = 1;
        } catch (IOException e) {
            // Exception handling
            Error = -1;
        }
        System.out.println(list.get(0));
    }

    public void CreateCSV(String Filename){
        // Write the content in file
        String absolutePath = directory + System.getProperty("file.separator") + Filename;
        //time in seconds
        try(FileWriter fileWriter = new FileWriter(absolutePath)) {
            String fileContent = "0.0.1v,"
                    + System.getProperty("user.home") + ","
                    + "0" + ","
                    + "NULL" + ","
                    + "0" + ","
                    + "1";
            fileWriter.write(fileContent);
            fileWriter.close();
        } catch (IOException e) {
            // exception handling
             Error = -1;
        }

    }

    public CSVManager(String File,int Type){
        switch (Type) {
            case 0:
            //create new file
                CreateCSV(File);
                ReadCSV(File);
            break;
            case 1:
            //read information
                ReadCSV(File);
        }
    }

    public String speed(){
        return Returner(5);
    }
    public String Time(){
        return Returner(4);
    }

    public String NAME(){
        return Returner(3);
    }
    public String Score(){
        return Returner(2);
    }

    public String Returner(int index){
        return list.get(index);
    }
}

