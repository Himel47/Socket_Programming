
package SocketSpecificClient.Client;

import java.io.*;
import java.net.Socket;

public class Client {

    public Socket serverClient;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;
    String userName;

    //public String getName() {
    //    return name;
    //}

    //public void setName(String name) {
    //    this.name = name;
    //}
    public static void main(String[] args) throws IOException {
        new Client().start();
    }

    public void start() throws IOException {
        serverClient = new Socket("localhost", 5000);
        System.out.println("Connected to Server");
        dataInputStream = new DataInputStream(serverClient.getInputStream());
        dataOutputStream = new DataOutputStream(serverClient.getOutputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter Client Name: ");
        userName = bufferedReader.readLine();
        
        dataOutputStream.writeUTF(userName);
        run();

    }

    private void run() throws IOException {
        ClientWriteHandler clientWriteHandler = new ClientWriteHandler(serverClient, userName);
        ClientReadHandler clientReadHandler = new ClientReadHandler(serverClient);
    }

}
