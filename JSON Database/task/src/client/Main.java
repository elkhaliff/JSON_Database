package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34512;

    private static final String clientDataPath = System.getProperty("user.dir") + File.separator +
//            "JSON Database" + File.separator + "task" + File.separator +
            "src" + File.separator + "client" + File.separator + "data";

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

            String commandLine = "";
            Gson gson = new Gson();
            if (params.getInput() != null) {

                Path path = Paths.get(clientDataPath + File.separator + params.getInput());

                try (Reader reader = Files.newBufferedReader(path,
                        StandardCharsets.UTF_8)) {
                    JsonElement tree = JsonParser.parseReader(reader);
                    commandLine = gson.toJson(tree);
                } catch (Exception e) {
                    println("File not found.");
                }
            } else {
                commandLine = gson.toJson(params);
            }

            output.writeUTF(commandLine);
            System.out.println("Sent: " + commandLine);

            String receivedMsg = input.readUTF();
            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
