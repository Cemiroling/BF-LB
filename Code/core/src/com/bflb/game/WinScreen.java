package com.bflb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class WinScreen implements Screen {
    private final MyGame game;
    private TextButton leave;
    private TextButton restart;
    private Stage stage;
    private Label win;
    private Texture upArrow;

    WinScreen(final MyGame game, String string, final int diff, final boolean isHost) {
        this.game = game;
        stage = new Stage();

        game.player.statistics.lanWins++;
        game.player.statistics.saveStats();

        upArrow = new Texture(Gdx.files.internal("up.png"));

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = game.gameInterface.font;
        win = new Label("You Win!", style);
        win.setColor(Color.GREEN);
        win.setPosition((int) (Gdx.graphics.getWidth() * 0.25), (int) (Gdx.graphics.getHeight() * 0.8));
        win.setFontScale(3.f, 3.f);
        stage.addActor(win);

        Label winrate = new Label("Your winrate:  " + game.player.statistics.calcWinrate() + "%", style);
        winrate.setPosition(Gdx.graphics.getWidth() / 2 - 500, Gdx.graphics.getHeight() / 2 - 200);
        winrate.setFontScale(2.5f, 2.5f);
        stage.addActor(winrate);

        Label time = new Label(string, style);
        time.setPosition((int) (Gdx.graphics.getWidth() / 2 - 500), (int) (Gdx.graphics.getHeight() / 2));
        time.setFontScale(2.5f, 2.5f);
        stage.addActor(time);

        leave = new TextButton("leave", game.gameInterface.shortButtonStyle);
        leave.setWidth(400);
        leave.setHeight(250);
        leave.getLabel().setFontScale(2f, 2f);
        restart = new TextButton("restart", game.gameInterface.shortButtonStyle);
        restart.setWidth(400);
        restart.setHeight(250);
        restart.getLabel().setFontScale(2f, 2f);
        leave.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (isHost) {
                    game.lanGame.server.serverStop();
                } else {
                    game.lanGame.client.disconnect();
                    game.lanGame.client.client.stop();
                    game.lanGame.client.start();
                }
                game.setScreen(new LanMenu(game));
            }
        });
        restart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (isHost) {
                    game.lanGame.server.opponentReady = false;
                } else {
                    game.lanGame.client.opponentReady = false;
                }
                game.setScreen(new DifficultyMenu(game, diff, true, isHost));
            }
        });
        leave.setPosition(Gdx.graphics.getWidth() / 2 - 500, 50);
        restart.setPosition(Gdx.graphics.getWidth() / 2 + 100, 50);

        stage.addActor(leave);
        stage.addActor(restart);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.gameInterface.batch.begin();
        game.gameInterface.batch.draw(upArrow, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() / 2 - 250, 92, 141);
        game.gameInterface.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        upArrow.dispose();
    }
}
