package server;

import com.google.gson.JsonElement;

public class Get implements Command {
    private final DataBase dataBase;
    private final JsonElement key;

    public Get(DataBase dataBase, JsonElement key) {
        this.dataBase = dataBase;
        this.key = key;
    }

    @Override
    public void execute() {
        dataBase.get(key);
    }

    @Override
    public JsonElement getResult() {
        return dataBase.getOut();
    }
}

