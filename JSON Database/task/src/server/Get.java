package server;

public class Get implements Command {
    private DataBase dataBase;
    private final int index;

    public Get(DataBase dataBase, int index) {
        this.dataBase = dataBase;
        this.index = index;
    }

    @Override
    public void execute() {
        dataBase.get(index);
    }

    @Override
    public String getResult() {
        return dataBase.getOut();
    }
}

