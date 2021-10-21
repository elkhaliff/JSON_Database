package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class DataBase {
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";
    private static final String NO_SUCH_KEY = "No such key";

    private static final String fileName = "db.json";
    private static final String dbFilePath = System.getProperty("user.dir") + File.separator +
//            "JSON Database" + File.separator + "task" + File.separator +
            "src" + File.separator + "server" + File.separator + "data" + File.separator + fileName;

    private Map<String, String> db;


    private Response out;

    public DataBase() {
        db = new HashMap<>();
        loadFile();
    }

    private void initTran(ReadWriteLock lock) {
        out = new Response();
        out.setResponse(OK);
        Lock readLock = lock.readLock();
        readLock.lock();
        loadFile();
        readLock.unlock();
    }

    public Response getOut() {
        return out;
    }

    private void saveFile() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(dbFilePath);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            gson.toJson(db, isr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFile() {
        File file = new File(dbFilePath);
        Gson gson = new GsonBuilder().create();
        Path path = file.toPath();
        try (Reader reader = Files.newBufferedReader(path,
                StandardCharsets.UTF_8)) {
            db = (Map<String, String>) gson.fromJson(reader, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(String key, String value, ReadWriteLock lock) {
        initTran(lock);
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        db.put(key, value);
        try {
            saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeLock.unlock();
    }

    public void get(String key, ReadWriteLock lock) {
        initTran(lock);
        if (db.containsKey(key)) {
            out.setValue(db.get(key));
        } else {
            out.setResponse(ERROR);
            out.setReason(NO_SUCH_KEY);
        }
    }

    public void delete(String key, ReadWriteLock lock) {
        initTran(lock);
        if (db.containsKey(key)) {
            Lock writeLock = lock.writeLock();
            writeLock.lock();
            db.remove(key);
            try {
                saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            writeLock.unlock();
        } else {
            out.setResponse(ERROR);
            out.setReason(NO_SUCH_KEY);
        }
    }

    public void exit() {
        out = new Response();
        out.setResponse(OK);
    }

}
