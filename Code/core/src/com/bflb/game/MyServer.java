package com.bflb.game;

import com.bflb.game.Packets.AcceptPacket;
import com.bflb.game.Packets.ConnectPacket;
import com.bflb.game.Packets.DificultyPacket;
import com.bflb.game.Packets.DisconnectPacket;
import com.bflb.game.Packets.GiveUpPacket;
import com.bflb.game.Packets.PlayerPacket;
import com.bflb.game.Packets.ReadyPacket;
import com.bflb.game.Packets.RequestPacket;
import com.bflb.game.Packets.ServerStopPacket;
import com.bflb.game.Packets.TimePacket;
import com.bflb.game.Packets.UnreadyPacket;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class MyServer {
    public Server server;
    public boolean isTime;
    public boolean isPlayer;
    public boolean sendPlayer;
    public TimePacket timePacket;
    public PlayerPacket playerPacket;
    public Kryo kryo;
    public int diff;
    public boolean opponentConnected;
    public boolean opponentReady;
    public boolean opponentGivedUp;
    public boolean restartTimer;

    MyServer() throws IOException {
        server = new Server();
        server.start();
        kryo = server.getKryo();
        kryo.register(TimePacket.class);
        kryo.register(PlayerPacket.class);
        kryo.register(RequestPacket.class);
        kryo.register(AcceptPacket.class);
        kryo.register(Player.class);
        kryo.register(Statistics.class);
        kryo.register(float[].class);
        kryo.register(int[].class);
        kryo.register(DisconnectPacket.class);
        kryo.register(ConnectPacket.class);
        kryo.register(ServerStopPacket.class);
        kryo.register(ReadyPacket.class);
        kryo.register(DificultyPacket.class);
        kryo.register(GiveUpPacket.class);
        kryo.register(UnreadyPacket.class);
        diff = 3;

        timePacket = new TimePacket();
        timePacket.count = 5;

        server.bind(54555, 54777);
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof TimePacket) {
                    timePacket = (TimePacket) object;
                    isTime = true;
                }
                if (object instanceof PlayerPacket) {
                    playerPacket = (PlayerPacket) object;
                    isPlayer = true;
                    AcceptPacket accept = new AcceptPacket();
                    accept.diff = diff;
                    server.sendToAllTCP(accept);
                }
                if (object instanceof RequestPacket){
                    sendPlayer = true;
                }
                if (object instanceof ConnectPacket){
                    opponentConnected = true;
                    restartTimer = true;
                }
                if (object instanceof DisconnectPacket){
                    opponentConnected = false;
                    opponentReady = false;
                }
                if (object instanceof ReadyPacket){
                    opponentReady = true;
                }
                if (object instanceof GiveUpPacket){
                    opponentGivedUp = true;
                }
                if (object instanceof UnreadyPacket){
                    opponentReady = false;
                    restartTimer = true;
                }
            }
        });
    }

    public void giveUp(){
        GiveUpPacket giveUp = new GiveUpPacket();
        server.sendToAllTCP(giveUp);
    }

    public void sendDiff(){
        DificultyPacket dificulty = new DificultyPacket();
        dificulty.diff = diff;
        server.sendToAllTCP(dificulty);
    }

    public void sendTime(Timer time, int count){
        TimePacket timePacket = new TimePacket();
        timePacket.setText(time.str);
        timePacket.count = count + 5;
        server.sendToAllTCP(timePacket);
    }

    public void ready(){
        ReadyPacket ready = new ReadyPacket();
        server.sendToAllTCP(ready);
    }

    public void unready(){
        UnreadyPacket unready = new UnreadyPacket();
        server.sendToAllTCP(unready);
    }

    public void serverStop(){
        ServerStopPacket stop = new ServerStopPacket();
        server.sendToAllTCP(stop);
        server.close();
    }

    public String getTime() {
        isTime = false;
        return timePacket.time;
    }

    public void sendTime(String string) {
        TimePacket send = new TimePacket();
        send.time = string;
        server.sendToAllTCP(send);
    }

    public Player getPlayer(){
        isPlayer = false;
        return playerPacket.player;
    }

    public void sendRequest(){
        RequestPacket req = new RequestPacket();
        server.sendToAllTCP(req);
    }

    public void sendPlayer(Player player){
        sendPlayer = false;
        PlayerPacket send = new PlayerPacket();
        send.player = player;
        server.sendToAllTCP(send);
    }
}
