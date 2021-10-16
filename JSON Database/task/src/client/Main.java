package client;

import com.beust.jcommander.*;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34512;

    public static void println(String string) { System.out.println(string); }

    public static void main(String[] args) {
        Params params = new Params();
        JCommander.newBuilder()
                .addObject(params)
                .build()
                .parse(args);

        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            println("Client started!");

            Gson gson = new Gson();
            String commandLine = gson.toJson(params);

            output.writeUTF(commandLine);
            System.out.printf("Sent: %s\n", commandLine);

            String receivedMsg = input.readUTF();
            System.out.printf("Received: %s\n", receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
