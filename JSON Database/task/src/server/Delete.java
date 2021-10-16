package server;

public class Delete implements Command {
    private final DataBase dataBase;
    private final String key;

    public Delete(DataBase dataBase, String key) {
        this.dataBase = dataBase;
        this.key = key;
    }

    @Override
    public void execute() { dataBase.delete(key); }

    @Override
    public Response getResult() {
        return dataBase.getOut();
    }
}

