package com.bflb.game;

public class Player {
    public String name;
    public Statistics statistics;

    Player(){
        statistics = new Statistics();
        this.name = statistics.name;
    }

    public void setName(String name){
        this.name = name;
        statistics.name = name;
    }
}
