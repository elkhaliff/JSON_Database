package server;

public class Delete implements Command {
    private DataBase dataBase;

    public Delete(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute() {
        dataBase.delete();
    }
}

