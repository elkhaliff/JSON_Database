package server;

import com.google.gson.JsonElement;

public class Delete implements Command {
    private final DataBase dataBase;
    private final JsonElement key;

    public Delete(DataBase dataBase, JsonElement key) {
        this.dataBase = dataBase;
        this.key = key;
    }

    @Override
    public void execute() { dataBase.delete(key); }

    @Override
    public JsonElement getResult() {
        return dataBase.getOut();
    }
}

