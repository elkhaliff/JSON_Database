package server;

import com.google.gson.JsonElement;

interface Command {

    void execute();

    JsonElement getResult();
}