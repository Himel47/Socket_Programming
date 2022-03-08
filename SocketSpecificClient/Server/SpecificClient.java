package SocketSpecificClient.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecificClient implements Runnable {

    Thread thread;
    Socket serverClient;
    ServerSocket serverSocket;
    ArrayList<ClientHandler> clients = new ArrayList<>();
    BufferedReader bufferedReader;
    SpecificClient(Socket serverClient, ServerSocket serverSocket, ArrayList<ClientHandler> clients) {
        this.serverClient = serverClient;
        this.serverSocket = serverSocket;
        this.clients = clients;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            String serverMessage = null;

            try{
                serverMessage = bufferedReader.readLine();
            } catch (IOException e) {
                Logger.getLogger(SpecificClient.class.getName()).log(Level.SEVERE, null, e);
            }

            String serverMessageFull = "  " + serverMessage;

            String[] splittedMessage = serverMessageFull.split(" ", 4);

            String userName = splittedMessage[2];

            for(int i=0; i<clients.size(); i++){
                ClientHandler temporaryClient = clients.get(i);

                if(temporaryClient.userName.equals(userName)){
                    serverMessageFull = splittedMessage[1] + "Server: "+ splittedMessage[3];
                    try{
                        temporaryClient.dataOutputStream.writeUTF(serverMessageFull);
                    } catch (IOException e) {
                        Logger.getLogger(SpecificClient.class.getName()).log(Level.SEVERE, null, e);
                    }

                    try{
                        temporaryClient.dataOutputStream.flush();
                    } catch (IOException e) {
                        Logger.getLogger(SpecificClient.class.getName()).log(Level.SEVERE, null, e);
                    }
                    break;
                }
            }
        }
    }
}
