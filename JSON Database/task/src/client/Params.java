package client;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Params {
    @Parameter(names = { "-t", "-type" }, description = "Type of the request")
    private String type;

    @Parameter(names = {"-k", "-key"}, description = "Key of the cell")
    private String key;

    @Parameter(names = {"-v", "-value"}, description = "Value to save in the database")
    private String value;

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}