package server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final DataBase dataBase = new DataBase();

    private static final String SET = "set";
    private static final String GET = "get";
    private static final String DELETE = "delete";
    private static final String EXIT = "exit";

    private static final int PORT = 34512;

    public static void println(String string) { System.out.println(string); }

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            println("Server started!");
            while (true) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String receivedMsg = input.readUTF();

                    Gson gson = new Gson();
                    Request request = gson.fromJson(receivedMsg, Request.class);

                    TransactionBroker transactionBroker = new TransactionBroker();
                    Command command;
                    dataBase.initTran();
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
                        default: {
                            command = new Exit(dataBase);
                        }
                    }
                    transactionBroker.setCommand(command);
                    transactionBroker.executeCommand();

                    String msgOut = gson.toJson(transactionBroker.getResultCommand());

                    output.writeUTF(msgOut);

                    if (request.getType().equals(EXIT)) return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
