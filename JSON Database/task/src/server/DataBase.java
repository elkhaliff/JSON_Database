package server;

public class DataBase {
    public static final int OK = 0;
    public static final int ERROR = 1;

    private static final int DATA_BASE_SIZE = 1001;
    private final String[] dataBase = new String[DATA_BASE_SIZE];

    private String data;
    private int index;
    private int result;
    private String out;

    public int getResult() {
        return result;
    }

    public String getOut() {
        return out;
    }

    {
        for (int i = 0; i < DATA_BASE_SIZE; i++) {
            dataBase[i] = "";
        }
    }

    public DataBase() {
    }

    public void initTran(int index, String data) {
        this.index = index;
        this.data = data;
    }

    private boolean testInd() {
        return (index > 0 && index < DATA_BASE_SIZE);
    }

    public void set() {
        if (testInd()) {
            dataBase[index] = data;
            result = OK;
        } else {
            result = ERROR;
        }
    }

    public void get() {
        if (testInd()) {
            out = dataBase[index];
            result = (out.equals("")) ? ERROR : OK;
        } else {
            result = ERROR;
        }
    }

    public void delete() {
        if (testInd()) {
            dataBase[index] = "";
            result = OK;
        } else {
            result = ERROR;
        }
    }

    public void exit() {
        result = OK;
    }

}
