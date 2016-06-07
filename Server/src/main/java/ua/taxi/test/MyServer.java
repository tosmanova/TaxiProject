package ua.taxi.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by andrii on 06.06.16.
 */
public class MyServer {

    private PrintWriter writer;
    private BufferedReader reader;
    private BufferedReader consoleReader;
    private ServerSocket serverSocket;
    private Socket client;


    public MyServer() throws IOException {
        serverSocket = new ServerSocket(8080);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static void main(String[] args) throws IOException {
        MyServer myServer = new MyServer();
        while (true) {
            myServer.write();
            myServer.read();
        }
    }

    private void write() throws IOException {
        client = serverSocket.accept();
        System.out.print("Server: ");
        String input = consoleReader.readLine();
        writer = new PrintWriter(client.getOutputStream());
        writer.printf("Server:%s\n", input);
        writer.flush();
        writer.close();

    }

    private void read() throws IOException {
        client = serverSocket.accept();
        System.out.println("Wait for client");
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println("Client: " + reader.readLine());
        reader.close();
        client.close();

    }

}
