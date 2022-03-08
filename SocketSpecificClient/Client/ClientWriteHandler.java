package SocketSpecificClient.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientWriteHandler implements Runnable {
    Thread thread;
    String userName;
    Socket serverClient;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;

    ClientWriteHandler(Socket serverClient, String userName){
        this.serverClient = serverClient;
        this.userName = userName;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try{
            dataOutputStream = new DataOutputStream(serverClient.getOutputStream());
        } catch (IOException e) {
            Logger.getLogger(ClientWriteHandler.class.getName()).log(Level.SEVERE, null, e);
        }

        while(true){
            try{
                sendMessage();
            } catch (IOException e) {
                Logger.getLogger(ClientWriteHandler.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void sendMessage() throws IOException {
        String clientMessage= bufferedReader.readLine();
        String clientMessageFull = userName + ": " + clientMessage;
        dataOutputStream.writeUTF(clientMessageFull);
    }
}
