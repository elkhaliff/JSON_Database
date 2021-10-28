package server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Session  extends Thread { // implements Runnable {
    private static final DataBase dataBase = new DataBase();

    private static final String SET = "set";
    private static final String GET = "get";
    private static final String DELETE = "delete";
    private static final String EXIT = "exit";

    private final Socket socket;
    private final ServerSocket server;
    private AtomicBoolean stopServer;

    public Session(ServerSocket server, Socket socket, AtomicBoolean stopServer) {
        this.server = server;
        this.socket = socket;
        this.stopServer = stopServer;
    }

    @Override
    public void run() {

        try (
                socket;
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            TransactionBroker transactionBroker = new TransactionBroker();
            Command command;

            String receivedMsg = input.readUTF();

            Gson gson = new Gson();
            Request request = gson.fromJson(receivedMsg, Request.class);
            switch (request.getType()) {
                case SET: {
                    command = new Set(dataBase, request.getKey(), request.getValue());
                    break;
                }
                case GET: {
                    command = new Get(dataBase, request.getKey());
                    break;
                }
                case DELETE: {
                    command = new Delete(dataBase, request.getKey());
                    break;
                }
                case EXIT: {
                    stopServer.set(true);
                    command = new Exit(dataBase);
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected type: " + request.getType());
                }
            }
            transactionBroker.setCommand(command);
            transactionBroker.executeCommand();
            String msgOut = gson.toJson(transactionBroker.getResultCommand());
            output.writeUTF(msgOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stopServer.get()) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }
    }
}
