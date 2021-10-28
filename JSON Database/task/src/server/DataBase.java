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
//            "JSON Database" + File.separator + "task" + File.separator +
            "src" + File.separator + "server" + File.separator + "data" + File.separator + fileName;

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
        try (
                FileOutputStream fos = new FileOutputStream(dbFilePath);
                OutputStreamWriter isr = new OutputStreamWriter(fos, StandardCharsets.UTF_8)
            ) {
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
        if (file.length() > 0) {
            Gson gson = new GsonBuilder().create();
            Path path = file.toPath();
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                db = gson.fromJson(reader, JsonObject.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private JsonElement getElement(JsonArray keysArray) {
        JsonObject own = db;
        JsonElement curr = null;
        for (int i = 0; i < keysArray.size()-1; i++) {
            JsonElement key = keysArray.get(i);
            if (own != null)
                curr = own.get(key.getAsString());
            else
                break;
            if (curr != null && curr.isJsonObject()) {
                own = (JsonObject) curr;
            } else
                own = null;
        }
        return curr;
    }

    public void set(JsonElement keys, JsonElement value) {
        initTran(true);
        if (keys.isJsonPrimitive()) {
            db.add(keys.getAsString(), value);
        } else {
            JsonArray keysArray = keys.getAsJsonArray();
            JsonObject elmByKey = (JsonObject)getElement(keysArray);
            if (elmByKey!=null)
                elmByKey.add(keysArray.get(keysArray.size()-1).getAsString(), value);
        }
        saveFile();
    }
    private JsonElement getValue(JsonElement keys) {
        JsonElement value = null;
        if (keys.isJsonPrimitive()) {
            value = db.get(keys.getAsString());
        } else {
            JsonArray keysArray = keys.getAsJsonArray();
            if (keysArray.size() == 1) {
                value = db.get(keysArray.get(0).getAsString());
            } else {
                JsonObject elmByKey = (JsonObject) getElement(keysArray);
                if (elmByKey != null) {
                    value = elmByKey.get(keysArray.get(keysArray.size() - 1).getAsString());
                }
            }
        }
        return value;
    }

    public void get(JsonElement keys) {
        initTran(true);
        JsonElement value = getValue(keys);
        if (value != null) {
            out.add(RESP_FLD_VALUE, value);
        } else {
            out.addProperty(RESP_FLD_RESPONSE, ERROR);
            out.addProperty(RESP_FLD_REASON, NO_SUCH_KEY);
        }
    }

    public void delete(JsonElement keys) {
        initTran(true);
        JsonElement value = getValue(keys);
        if (value != null) {
            if (keys.isJsonPrimitive()) {
                db.remove(keys.getAsString());
            } else {
                JsonArray keysArray = keys.getAsJsonArray();
                JsonObject elmByKey = (JsonObject)getElement(keysArray);
                if (elmByKey!=null) {
                    elmByKey.remove(keysArray.get(keysArray.size() - 1).getAsString());
                }
            }
            saveFile();
        } else {
            out.addProperty(RESP_FLD_RESPONSE, ERROR);
            out.addProperty(RESP_FLD_REASON, NO_SUCH_KEY);
        }
    }

    public void exit() {
        initTran(false);
    }
}
