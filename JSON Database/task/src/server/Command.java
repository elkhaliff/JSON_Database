package server;

interface Command {

    void execute();

    String getResult();
}