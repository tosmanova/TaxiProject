package ua.taxi.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by andrii on 06.06.16.
 */
public class MyClient {


    private PrintWriter writer;
    private BufferedReader reader;
    private BufferedReader consoleReader;
    private Socket socket;


    public MyClient() throws IOException {
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static void main(String[] args) throws IOException {
        MyClient myClient = new MyClient();
        while (true) {
            myClient.read();
            myClient.write();
        }
    }

    private void write() throws IOException {
        socket = new Socket("127.0.0.1", 8080);
        System.out.print("Client: ");
        String input = consoleReader.readLine();
        writer = new PrintWriter(socket.getOutputStream());
        writer.printf("%s\n", input);
        writer.flush();
        writer.close();
        socket.close();
    }

    private void read() throws IOException {
        socket = new Socket("127.0.0.1", 8080);
        System.out.println("Wait for server");
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(reader.readLine());
        reader.close();
        socket.close();

    }

}
