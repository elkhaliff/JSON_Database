package server;

import com.google.gson.JsonElement;

public class TransactionBroker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }

    public JsonElement getResultCommand() { return command.getResult(); }
}
