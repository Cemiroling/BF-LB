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
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.InetAddress;

public class MyClient {
    public Client client;
    public boolean isTime;
    public boolean isPlayer;
    public boolean sendPlayer;
    public TimePacket timePacket;
    public PlayerPacket playerPacket;
    public Kryo kryo;
    public boolean done;
    public int diff;
    public boolean serverStop;
    public boolean opponentReady;
    public boolean opponentGivedUp;
    public boolean restartTimer;
    public boolean updateDiff;

    MyClient()throws IOException{
        client = new Client();
        start();
    }

    public void start(){
        client.start();
    }

    public void connect(String adress)throws IOException{
        kryo = client.getKryo();
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
        timePacket = new TimePacket();
        timePacket.count = 5;

        InetAddress address = client.discoverHost(54777, 1000);
        client.connect(1000, address.toString().replace("/", ""), 54555, 54777);
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof TimePacket) {
                    timePacket = (TimePacket) object;
                    isTime = true;
                }
                if (object instanceof PlayerPacket) {
                    playerPacket = (PlayerPacket) object;
                    isPlayer = true;
                }
                if (object instanceof RequestPacket){
                    sendPlayer = true;
                }
                if (object instanceof  AcceptPacket){
                    AcceptPacket accepted = (AcceptPacket) object;
                    diff = accepted.diff;
                    done = true;
                }
                if (object instanceof  ServerStopPacket){
                    serverStop = true;
                    opponentReady = false;
                }
                if (object instanceof ReadyPacket){
                    opponentReady = true;
                }
                if (object instanceof DificultyPacket){
                    DificultyPacket difficulty = (DificultyPacket) object;
                    diff = difficulty.diff;
                    updateDiff = true;
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
        client.sendTCP(giveUp);
    }

    public void ready(){
        ReadyPacket ready = new ReadyPacket();
        client.sendTCP(ready);
    }

    public void unready(){
        UnreadyPacket unready = new UnreadyPacket();
        client.sendTCP(unready);
    }

    public void sendRequest(){
        RequestPacket req = new RequestPacket();
        client.sendTCP(req);
    }

    public void connect(){
        ConnectPacket connect = new ConnectPacket();
        client.sendTCP(connect);
    }

    public void disconnect(){
        DisconnectPacket disconnect = new DisconnectPacket();
        client.sendTCP(disconnect);
    }

    public Player getPlayer(){
        isPlayer = false;
        return playerPacket.player;
    }

    public void sendTime(Timer timer, int count){
        TimePacket timePacket = new TimePacket();
        timePacket.setText(timer.str);
        timePacket.count = count + 5;
        client.sendTCP(timePacket);
    }

    public void sendPlayer(Player player){
        sendPlayer = false;
        PlayerPacket send = new PlayerPacket();
        send.player = player;
        client.sendTCP(send);
    }
}
