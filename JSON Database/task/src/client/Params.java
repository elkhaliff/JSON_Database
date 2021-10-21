package client;

import com.beust.jcommander.Parameter;

public class Params {
    @Parameter(names = { "-t", "-type" }, description = "Type of the request")
    private String type;

    @Parameter(names = {"-k", "-key"}, description = "Key of the cell")
    private String key;

    @Parameter(names = {"-v", "-value"}, description = "Value to save in the database")
    private String value;

    @Parameter(names = {"-in", "-input"}, description = "Params file name")
    private String input;

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getInput() { return input; }
}
