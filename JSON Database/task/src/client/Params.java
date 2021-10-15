package client;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Params {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-t", "-type" }, description = "Type of the request")
    private String type;

    @Parameter(names = {"-i", "-index"}, description = "Index of the cell")
    private Integer index;

    @Parameter(names = {"-m", "-message"}, description = "Value to save in the database")
    private String message;

    public String getType() {
        return type;
    }

    public Integer getIndex() {
        return index;
    }

    public String getMessage() {
        return message;
    }
}