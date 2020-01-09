package com.bflb.game;

import java.io.IOException;

public class LANGame {
    public MyClient client;
    public MyServer server;

    public LANGame(){
        try {
            client = new MyClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createServer() throws IOException {
        server = new MyServer();
    }
}
