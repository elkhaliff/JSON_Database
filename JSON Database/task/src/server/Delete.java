package server;

public class Delete implements Command {
    private DataBase dataBase;
    private final int index;

    public Delete(DataBase dataBase, int index) {
        this.dataBase = dataBase;
        this.index = index;
    }

    @Override
    public void execute() { dataBase.delete(index); }

    @Override
    public String getResult() {
        return dataBase.getOut();
    }
}

