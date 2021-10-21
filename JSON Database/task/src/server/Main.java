package server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    private static final String EXIT = "exit";

    private static final int PORT = 34512;

    public static void println(String string) { System.out.println(string); }

    public static void main(String[] args) {
        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        try (ServerSocket server = new ServerSocket(PORT)) {
            println("Server started!");
            while (!server.isClosed()) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream())
                ) {
                    String receivedMsg = input.readUTF();
                    Gson gson = new Gson();
                    Request request = gson.fromJson(receivedMsg, Request.class);

                    ReadWriteLock lock = new ReentrantReadWriteLock();

                    executor.submit(new Session(request, socket, lock));

                    Thread.sleep(50);

                    if (EXIT.equals(request.getType())) break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
