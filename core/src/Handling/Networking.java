package Handling;


import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;


public class Networking implements Runnable{
    //variables for the class
    private static String dataToSend= "hello1";
    private static Socket sock;
    private static String DataToRecv = "";
    public final String username;
    private final String password;
    private  BufferedReader in;
    private PrintWriter writerToClient;
    private InputStream is;
    public boolean ready = false;

    //sets data class variable
    public static void sendOFF(String data){
        dataToSend = data;
    }

    //gets data received by the thread client
    public static String GetRecv(){
        return DataToRecv;
    }

    //checks for received packets
    public static String Recv(BufferedReader in){
        String userOut = "";
        try {
            while ((userOut = in.readLine()) != null )       //Waits for response
            {
                break;
                //System.out.println("Server says: " + userOut);  //Prints response
            }
        } catch (Exception e){
            System.out.println(e);
        }


        return userOut.replace(";::;","\n");
    }

    //sets data to server
    public static void send(PrintWriter writer,String data){
        writer.println(data.replace("\n",";::;"));
        writer.flush();
    }

    //sets data to server and receive data after
    public int p2pMode(PrintWriter writerToClient,BufferedReader in,InputStream is){
        String hold = "";

        try {
            while (true) {
                send(writerToClient, dataToSend);
                try {
                    //hold = Recv(in);
                    hold = Recv(in);
                    if (!hold.isEmpty()){
                        DataToRecv = hold;
                    }
                } catch (Exception e) {
                    System.out.println("no data");
                    hold = "";
                }
                if (!hold.equals("data?") && !hold.equals("end")) {
                    DataToRecv = hold;
                    //dataToSend = "hello0";
                } else if (hold.equals("end")) {
                    System.out.println("connection closed");
                    DataToRecv = "Closed";
                    break;
                } else {
                    //dataToSend = "hello1";
                }
                send(writerToClient, dataToSend);
            }
        }
        catch (Exception e){
            return -1;
        }
        return 0;

    }



    //constructor creates connection with server and logins authenticates with it
    public Networking(String ServerIp,int Port,String Username,String Password,boolean Signup,int Type){
        this.username = Username;
        this.password = Password;

        // write your code here
        try {
            sock = new Socket(ServerIp, Port);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writerToClient = new PrintWriter(sock.getOutputStream());
            this.is = sock.getInputStream();

            System.out.println(Signup);
            if (Type ==1){
                send(writerToClient,"GetRank");
            }
            else {
                send(writerToClient, "Open");
            }
            Recv(in);
            send(writerToClient,username);
            send(writerToClient,password);

            if (Signup){
                send(writerToClient,"NEW");
            }
            else{
                send(writerToClient,"OLD");
            }
            DataToRecv = Recv(in);
        }
        catch (Exception e){
            System.out.println("closed");
            DataToRecv = "ERROR";
        }
    }




    @Override
    public void run() {
        //tells the server to place it on the match making queue
        //send and recv
        String hold = "";
        boolean run = true;
        while (run) {
            send(writerToClient, "yes");
            hold = Recv(in);
            if (hold.equals("done")){
                run = false;
            }
        }
        Recv(in);
        System.out.println("ready");
        p2pMode(writerToClient,in,is);
    }
}


