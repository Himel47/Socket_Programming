package SocketSpecificClient.Client;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientReadHandler implements Runnable {
    Thread thread;
    Socket serverClient;
    
    DataInputStream dataInputStream;

    ClientReadHandler(Socket serverClient){
        this.serverClient = serverClient;

        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        while(true){
            try{
                receiveMessage();
            }catch (IOException e){
                Logger.getLogger(ClientReadHandler.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void receiveMessage() throws IOException{
        try{
            dataInputStream = new DataInputStream(serverClient.getInputStream());

        } catch (IOException e) {
            Logger.getLogger(ClientReadHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        String serverMessage = null;

        try{
            serverMessage = dataInputStream.readUTF();
        } catch (IOException e) {
            Logger.getLogger(ClientReadHandler.class.getName()).log(Level.SEVERE, null, e);
        }

        System.out.println( serverMessage);
    }
}
