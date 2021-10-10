package server;

import java.util.Scanner;

public class Main {
    private static final String SET = "set";
    private static final String GET = "get";
    private static final String DELETE = "delete";
    private static final String EXIT = "exit";

    public static void println(String string) { System.out.println(string); }

    public static void main(String[] args) {
        Engine engine = new Engine();
        Scanner scanCommand = new Scanner(System.in);
        String[] inputs;
        String command;
        int index = 0;
        String data = "";
        String out;
        do{
            inputs = scanCommand.nextLine().split("\\s+");
            command = inputs[0];
            if (inputs.length > 1) index = Integer.parseInt(inputs[1]);
            if (inputs.length > 2) {
                StringBuilder dataBuild = new StringBuilder();
                for (int i = 2; i < inputs.length; i++) {
                    dataBuild.append(inputs[i]); dataBuild.append(" ");
                }
                data = dataBuild.toString();
            }

            switch (command) {
                case SET: println(engine.set(index, data)); break;
                case GET: println(engine.get(index)); break;
                case DELETE: println(engine.delete(index)); break;
                case EXIT: return;
            }
        } while (true);

    }
}
