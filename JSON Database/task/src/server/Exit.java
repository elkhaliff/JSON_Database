package server;

import com.google.gson.JsonElement;

public class Exit implements Command {
    private final DataBase dataBase;

    public Exit(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute() {
        dataBase.exit();
    }

    @Override
    public JsonElement getResult() {
        return dataBase.getOut();
    }
}

