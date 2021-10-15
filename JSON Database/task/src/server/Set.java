package server;

public class Set implements Command {
    private DataBase dataBase;
    private final int index;
    private final String data;

    public Set(DataBase dataBase, int index, String data) {
        this.dataBase = dataBase;
        this.index = index;
        this.data = data;
    }

    @Override
    public void execute() {
        dataBase.set(index, data);
    }

    @Override
    public String getResult() {
        return dataBase.getOut();
    }
}
