package STS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

public class ClientHandle implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private DH dh;
    private int clientNumber;

    public ClientHandle(Socket socket, int number) {
        this.socket = socket;
        clientNumber = number;
        dh = new DH();
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void log(String s){
        System.out.println(s);
    }

    public void run(){
        String line;
        try {
            dh.startDHagreement(in, new PrintWriter(socket.getOutputStream()));

            while((line=in.readLine())!=null){
                log(clientNumber+" : "+ new String(dh.decrypt(Base64.getDecoder().decode(line))));
            }
            log("["+clientNumber+"]");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DHException e) {
            e.printStackTrace();
        }
    }
}
