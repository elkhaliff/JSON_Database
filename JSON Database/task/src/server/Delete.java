package server;

import java.util.concurrent.locks.ReadWriteLock;

public class Delete implements Command {
    private final DataBase dataBase;
    private final String key;
    private final ReadWriteLock lock;

    public Delete(DataBase dataBase, String key, ReadWriteLock lock) {
        this.dataBase = dataBase;
        this.key = key;
        this.lock = lock;
    }

    @Override
    public void execute() { dataBase.delete(key, lock); }

    @Override
    public Response getResult() {
        return dataBase.getOut();
    }
}

