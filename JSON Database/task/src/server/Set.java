package server;

import com.google.gson.JsonElement;

public class Set implements Command {
    private final DataBase dataBase;
    private final JsonElement key;
    private final JsonElement value;

    public Set(DataBase dataBase, JsonElement key, JsonElement value) {
        this.dataBase = dataBase;
        this.key = key;
        this.value = value;
    }

    @Override
    public void execute() {
        dataBase.set(key, value);
    }

    @Override
    public JsonElement getResult() {
        return dataBase.getOut();
    }
}
