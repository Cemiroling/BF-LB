package com.bflb.game;

import com.badlogic.gdx.Game;

public class MyGame extends Game {
    public Player player;
    public GameInterface gameInterface;
    public LANGame lanGame;

    public void create() {
        gameInterface = new GameInterface();
        lanGame = new LANGame();
        player = new Player();
        this.setScreen(new MainMenu(this));
    }


    public void render() {
        super.render();
    }

    public void dispose() {
        gameInterface.batch.dispose();
        gameInterface.font.dispose();
        gameInterface.skin.dispose();
    }
}