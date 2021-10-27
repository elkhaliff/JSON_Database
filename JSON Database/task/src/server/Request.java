package server;

import com.google.gson.JsonElement;

public class Request {
    private final String type;
    private final JsonElement key;
    private final JsonElement value;

    public Request(String type, JsonElement key, JsonElement value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }
}
