package server;

import java.util.Arrays;

public class DataBase {
    public static final String OK = "OK";
    public static final String ERROR = "ERROR";

    private String[] dataBase;

    private String out;

    public DataBase(int dataBaseSize) {
        dataBase = new String[dataBaseSize];
        Arrays.fill(dataBase, "");
    }

    public String getOut() {
        return out;
    }

    public void initTran() {
        out = OK;
    }

    public void set(int index, String data) {
        dataBase[index] = data;
    }

    public void get(int index) {
        out = dataBase[index];
        out = (out.equals("")) ? ERROR : out;
    }

    public void delete(int index) {
        dataBase[index] = "";
    }

    public void exit() {
        out = OK;
    }

}
