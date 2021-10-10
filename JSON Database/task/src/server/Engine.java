package server;

public class Engine {
    private static final int DATA_BASE_SIZE = 101;
    private static final String OK = "OK";
    private static final String ERROR = "ERROR";
    private final String[] dataBase = new String[DATA_BASE_SIZE];

    {
        for (int i = 0; i < DATA_BASE_SIZE; i++) {
            dataBase[i] = "";
        }
    }

    private boolean testInd(int index) {
        return (index > 0 && index < DATA_BASE_SIZE);
    }

    public String set(int index, String data) {
        if (testInd(index)) {
            dataBase[index] = data;
            return OK;
        } else {
            return ERROR;
        }
    }

    public String get(int index) {
        String out;
        if (testInd(index)) {
            out = dataBase[index];
            return (out == "") ? ERROR : out;
        } else {
            return ERROR;
        }
    }

    public String delete(int index) {
        if (testInd(index)) {
            dataBase[index] = "";
            return OK;
        } else {
            return ERROR;
        }
    }
}
