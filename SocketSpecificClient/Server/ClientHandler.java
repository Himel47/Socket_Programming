package SocketSpecificClient.Server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread{
    
    public String userName;
    public Socket serverClient;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;

    ClientHandler(Socket serverClient) throws IOException{
        this.serverClient = serverClient;
        dataInputStream = new DataInputStream(serverClient.getInputStream());
        dataOutputStream = new DataOutputStream(serverClient.getOutputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    }

    @Override
    public void run(){
        try{
            userName = dataInputStream.readUTF();
            System.out.println(userName + " Connected\n");

            new Thread(() -> {
                while (true) {
                    try {
                        receiveMessage();
                    } catch (IOException e) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }).start();
        } catch (IOException e) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void receiveMessage() throws IOException {
        String clientMessage = dataInputStream.readUTF();
        System.out.println(clientMessage);
    }
}
