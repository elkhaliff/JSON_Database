package server;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";
    private static final String NO_SUCH_KEY = "No such key";

    private final Map<String, String> dataBase;


    private Response out;

    public DataBase() {
        dataBase = new HashMap<>();
    }

    public void initTran() {
        out = new Response();
        out.setResponse(OK);
    }

    public Response getOut() {
        return out;
    }

    public void set(String key, String value) {
        dataBase.put(key, value);
    }

    public void get(String key) {
        if (dataBase.containsKey(key))
            out.setValue(dataBase.get(key));
        else {
            out.setResponse(ERROR);
            out.setReason(NO_SUCH_KEY);
        }
    }

    public void delete(String key) {
        if (dataBase.containsKey(key))
            dataBase.remove(key);
        else {
            out.setResponse(ERROR);
            out.setReason(NO_SUCH_KEY);
        }
    }

    public void exit() {
    }

}
