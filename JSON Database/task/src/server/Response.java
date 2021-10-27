package server;

import com.google.gson.JsonElement;

public class Response {
    private String response;
    private JsonElement value;
    private String reason;

    public void setResponse(String response) {
        this.response = response;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
