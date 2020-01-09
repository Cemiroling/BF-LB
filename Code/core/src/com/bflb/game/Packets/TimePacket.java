package com.bflb.game.Packets;

public class TimePacket {

    public String time;
    public int count;
    public void setText(String text) {
        this.time = text;
    }

    public String getText() {
        return time;
    }
}
