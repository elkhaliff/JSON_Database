package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final String OK = "OK";
    private static final String ERROR = "ERROR";
    private static final String SET = "set";
    private static final String GET = "get";
    private static final String DELETE = "delete";
    private static final String EXIT = "exit";

    private static final int PORT = 34512;

    public static void println(String string) { System.out.println(string); }

    public static void main(String[] args) {
        String[] inputs;
        String action;
        int index = 0;
        String data = "";
        DataBase dataBase = new DataBase();

        try (ServerSocket server = new ServerSocket(PORT)) {
            println("Server started!");
            while (true) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String receivedMsg = input.readUTF();
                    inputs = receivedMsg.split("\\s+");
                    action = inputs[0];
                    if (inputs.length > 1) index = Integer.parseInt(inputs[1]);
                    if (inputs.length > 2) {
                        StringBuilder dataBuild = new StringBuilder();
                        for (int i = 2; i < inputs.length; i++) {
                            dataBuild.append(inputs[i]); dataBuild.append(" ");
                        }
                        data = dataBuild.toString();
                    }

                    Engine engine = new Engine();
                    dataBase.initTran(index, data);
                    Command command;
                    String msgOut = ERROR;
                    switch (action) {
                        case SET: {
                            command = new Set(dataBase);
                            break;
                        }
                        case GET: {
                            command = new Get(dataBase);
                            break;
                        }
                        case DELETE: {
                            command = new Delete(dataBase);
                            break;
                        }
                        default: {
                            command = new Exit(dataBase);
                        }
                    }
                    engine.setCommand(command);
                    engine.executeCommand();
                    if ((dataBase.getResult() == DataBase.OK)) {
                        msgOut = (action.equals(GET)) ? dataBase.getOut() : OK;
                    }


                    output.writeUTF(msgOut);
//                    System.out.printf("Sent: A record # %s was sent!\n", msgOut);

                    if (action.equals(EXIT)) return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
