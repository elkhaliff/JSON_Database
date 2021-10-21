package server;

import java.util.concurrent.locks.ReadWriteLock;

public class Set implements Command {
    private final DataBase dataBase;
    private final String key;
    private final String value;
    private final ReadWriteLock lock;

    public Set(DataBase dataBase, String key, String value, ReadWriteLock lock) {
        this.dataBase = dataBase;
        this.key = key;
        this.value = value;
        this.lock = lock;
    }

    @Override
    public void execute() {
        dataBase.set(key, value, lock);
    }

    @Override
    public Response getResult() {
        return dataBase.getOut();
    }
}
