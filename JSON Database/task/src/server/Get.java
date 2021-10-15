package server;

public class Get implements Command {
    private DataBase dataBase;

    public Get(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute() {
        dataBase.get();
    }
}

