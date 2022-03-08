package SocketSpecificClient.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public Socket serverClient;
    ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        new Server().newClientConnect();
    }

    public ServerSocket serverSocket;
    public void newClientConnect() throws IOException{
        serverSocket = new ServerSocket(5000);
        
        System.out.println("Server Started");

        ClientAdd clientAdd;
        clientAdd = new ClientAdd(serverClient, serverSocket, clients);

        SpecificClient specificClient;
        specificClient = new SpecificClient(serverClient,serverSocket, clients);
    }
}

class ClientAdd implements Runnable{
    Thread thread;
    public Socket serverClient;
    ArrayList<ClientHandler> clients = new ArrayList<>();
    public ServerSocket serverSocket;

    ClientAdd(Socket serverClient, ServerSocket serverSocket, ArrayList<ClientHandler>clients){
        this.serverClient=serverClient;
        this.serverSocket=serverSocket;

        this.clients = clients;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try{
            while(true){
                serverClient = serverSocket.accept();
                System.out.println("New Client Connecting");

                ClientHandler newClient = new ClientHandler(serverClient);

                clients.add(newClient);
                newClient.start();
            }
        } catch (IOException e) {
            Logger.getLogger(ClientAdd.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
