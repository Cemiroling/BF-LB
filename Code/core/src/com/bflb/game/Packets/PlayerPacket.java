package com.bflb.game.Packets;

import com.bflb.game.Player;

public class PlayerPacket {
    public Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
