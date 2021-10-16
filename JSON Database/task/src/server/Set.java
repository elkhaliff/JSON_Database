package server;

public class Set implements Command {
    private final DataBase dataBase;
    private final String key;
    private final String value;

    public Set(DataBase dataBase, String key, String value) {
        this.dataBase = dataBase;
        this.key = key;
        this.value = value;
    }

    @Override
    public void execute() {
        dataBase.set(key, value);
    }

    @Override
    public Response getResult() {
        return dataBase.getOut();
    }
}
