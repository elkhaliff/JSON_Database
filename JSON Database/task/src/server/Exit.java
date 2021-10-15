package server;

public class Exit implements Command {
    private DataBase dataBase;

    public Exit(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute() {
        dataBase.exit();
    }

    @Override
    public String getResult() {
        return dataBase.getOut();
    }
}

