package server;

public class Set implements Command {
    private DataBase dataBase;

    public Set(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public void execute() {
        dataBase.set();
    }
}
