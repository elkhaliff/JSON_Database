package server;

import com.google.gson.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataBase {
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";
    private static final String NO_SUCH_KEY = "No such key";

    private static final String RESP_FLD_RESPONSE = "response";
    private static final String RESP_FLD_REASON = "reason";
    private static final String RESP_FLD_VALUE = "value";

    private static final String fileName = "db.json";
    private static final String dbFilePath = System.getProperty("user.dir") + File.separator +
            "JSON Database" + File.separator + "task" + File.separator +
            "src" + File.separator + "server" + File.separator + "data" + File.separator + fileName;

    // private Map<String, JsonElement> db;
    // private Response out;
    private JsonObject db;
    private JsonObject out;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final Lock readLock = readWriteLock.readLock();



    public DataBase() {
        db = new JsonObject();
        loadFile();
    }

    private void initTran(boolean withFIle) {
        out = new JsonObject();
        out.addProperty(RESP_FLD_RESPONSE, OK);
        if (withFIle) {
            readLock.lock();
            loadFile();
            readLock.unlock();
        }
    }

    public JsonElement getOut() {
        return out;
    }

    private void saveFile() {
        try (FileOutputStream fos = new FileOutputStream(dbFilePath);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            writeLock.lock();
            gson.toJson(db, isr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    private void loadFile() {
        File file = new File(dbFilePath);
        Gson gson = new GsonBuilder().create();
        Path path = file.toPath();
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            db = gson.fromJson(reader, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(JsonElement keys, JsonElement value) {
        initTran(true);
        if (keys.isJsonPrimitive()) {
            db.add(keys.getAsString(), value);
        } else {
            JsonArray keysArray = keys.getAsJsonArray();
            JsonArray valueArray = value.getAsJsonArray();
            for (JsonElement key : keysArray) {

            }
        }
        saveFile();
    }

    public void get(JsonElement key) {
        initTran(true);
        if (key.isJsonPrimitive()) {
            JsonElement value = db.get(key.getAsString());
            if (value != null) {
                out.add(RESP_FLD_VALUE, value);
            } else {
                out.addProperty(RESP_FLD_RESPONSE, ERROR);
                out.addProperty(RESP_FLD_REASON, NO_SUCH_KEY);
            }
        }
    }

    public void delete(JsonElement key) {
        initTran(true);
        if (key.isJsonPrimitive()) {
            JsonElement value = db.get(key.getAsString());
            if (value != null) {
                db.remove(key.getAsString());
                saveFile();
            } else {
                out.addProperty(RESP_FLD_RESPONSE, ERROR);
                out.addProperty(RESP_FLD_REASON, NO_SUCH_KEY);
            }
        }
    }

    public void exit() {
        initTran(false);
    }
}
