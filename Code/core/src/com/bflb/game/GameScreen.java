package com.bflb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;


public class GameScreen implements Screen {
    private final MyGame game;
    private Stage stage;
    private Field field;
    private Timer timer;
    private int diff;
    private boolean flag;
    private boolean fromLan;
    private boolean isHost;
    private Array<TextButton> times;
    private Array<Timer> timers;
    private int count;

    public GameScreen(final MyGame game, final int diff, final boolean fromLan, final boolean isHost) {
        this.game = game;
        this.field = new Field(diff, game);
        game.gameInterface.batch = new SpriteBatch();
        this.stage = new Stage();
        this.timer = new Timer(game);
        timer.start();
        this.fromLan = fromLan;
        this.isHost = isHost;
        this.diff = diff;
        flag = true;

        if (fromLan) {
            count = 0;
            times = new Array<TextButton>();
            timers = new Array<Timer>();
            Label.LabelStyle style = new Label.LabelStyle();
            style.font = game.gameInterface.font;

            Label yourName = new Label(game.player.name, style);
            yourName.setPosition(100, Gdx.graphics.getHeight() - 120);
            yourName.setFontScale(2.f, 2.f);
            stage.addActor(yourName);

            if (isHost) {
                Label opponentName = new Label(game.lanGame.server.playerPacket.player.name, style);
                opponentName.setPosition(100, Gdx.graphics.getHeight() - 220);
                opponentName.setFontScale(2.f, 2.f);
                stage.addActor(opponentName);
                game.lanGame.server.timePacket.count = 5;
            } else {
                Label opponentName = new Label(game.lanGame.client.playerPacket.player.name, style);
                opponentName.setPosition(100, Gdx.graphics.getHeight() - 220);
                opponentName.setFontScale(2.f, 2.f);
                stage.addActor(opponentName);
                game.lanGame.client.timePacket.count = 5;
            }

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 5; j++) {
                    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
                    textButtonStyle.up = game.gameInterface.skin.getDrawable("unpressed");
                    textButtonStyle.checked = game.gameInterface.skin.getDrawable("pressed");
                    textButtonStyle.font = game.gameInterface.font;
                    TextButton textButton = new TextButton("", textButtonStyle);
                    textButton.setWidth(100);
                    textButton.setHeight(100);

                    textButton.setPosition(500 + (j * Gdx.graphics.getWidth() / 10), Gdx.graphics.getHeight() - 150 - (i * 100));
                    textButton.setTouchable(Touchable.disabled);
                    times.add(textButton);
                    stage.addActor(textButton);
                }
            }
            TextButton giveUp = new TextButton("GiveUp", game.gameInterface.longButtonStyle);
            giveUp.setWidth(Gdx.graphics.getWidth() - 200);
            giveUp.setHeight(250);
            giveUp.getLabel().setFontScale(2f, 2f);
            giveUp.setPosition(100, 50);
            giveUp.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (isHost)
                        game.lanGame.server.giveUp();
                    else
                        game.lanGame.client.giveUp();
                    game.setScreen(new LoseScreen(game, diff, isHost));
                }
            });
            stage.addActor(giveUp);
            for (int i = 0; i < 10; i++) {
                Timer timer = new Timer(game);
                timers.add(timer);
            }
            timers.get(count).start();
            timers.get(count + 5).start();
        } else {
            TextButton restart = new TextButton("Restart", game.gameInterface.shortButtonStyle);
            restart.setWidth(400);
            restart.setHeight(250);
            restart.getLabel().setFontScale(2f, 2f);

            restart.setPosition(Gdx.graphics.getWidth() - 500, 50);
            restart.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setScreen(new GameScreen(game, diff, fromLan, isHost));
                }
            });

            TextButton back = new TextButton("back", game.gameInterface.shortButtonStyle);
            back.setWidth(400);
            back.setHeight(250);
            back.getLabel().setFontScale(2f, 2f);

            back.setPosition(100, 50);
            back.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setScreen(new DifficultyMenu(game, diff, false, false));
                }
            });


            stage.addActor(restart);
            stage.addActor(back);
        }
        field.setActors(stage);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    public void updateTextfields() {
        times.get(count).setText(timers.get(count).getStr());
        if (isHost) {
            times.get(game.lanGame.server.timePacket.count).setText(timers.get(game.lanGame.server.timePacket.count).getStr());
        } else {
            times.get(game.lanGame.client.timePacket.count).setText(timers.get(game.lanGame.client.timePacket.count).getStr());
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (fromLan) {
            updateTextfields();
            if (field.isDone()) {
                timers.get(count).setStop();
                times.get(count).setChecked(true);
                field = new Field(diff, game);
                field.setActors(stage);
                if (isHost) {
                    game.lanGame.server.sendTime(timers.get(count), count);
                } else {
                    game.lanGame.client.sendTime(timers.get(count), count);
                }
                count++;
                if (count != 5)
                    timers.get(count).start();

            }
            if (count == 5) {
                timer.setStop();
                game.setScreen(new WinScreen(game, "Your time is: " + timer.getStr(), diff, isHost));
            }

            if (isHost) {
                if (game.lanGame.server.opponentGivedUp) {
                    game.lanGame.server.opponentGivedUp = false;
                    game.setScreen(new WinScreen(game, "Your opponent gived up", diff, isHost));
                }
                if (game.lanGame.server.isTime) {
                    timers.get(game.lanGame.server.timePacket.count).setStop();
                    times.get(game.lanGame.server.timePacket.count).setText(game.lanGame.server.timePacket.time);
                    times.get(game.lanGame.server.timePacket.count).setChecked(true);
                    game.lanGame.server.isTime = false;
                    if (game.lanGame.server.timePacket.count != 9)
                        game.lanGame.server.timePacket.count++;
                    else {
                        game.setScreen(new LoseScreen(game, diff, isHost));
                    }
                    timers.get(game.lanGame.server.timePacket.count).start();
                }
            } else {
                if (game.lanGame.client.opponentGivedUp) {
                    game.lanGame.client.opponentGivedUp = false;
                    game.setScreen(new WinScreen(game, "Your opponent gived up", diff, isHost));
                }
                if (game.lanGame.client.isTime) {
                    timers.get(game.lanGame.client.timePacket.count).setStop();
                    times.get(game.lanGame.client.timePacket.count).setText(game.lanGame.client.timePacket.time);
                    times.get(game.lanGame.client.timePacket.count).setChecked(true);
                    game.lanGame.client.isTime = false;
                    if (game.lanGame.client.timePacket.count != 9)
                        game.lanGame.client.timePacket.count++;
                    else {
                        game.setScreen(new LoseScreen(game, diff, isHost));
                    }
                    timers.get(game.lanGame.client.timePacket.count).start();
                }
            }
        } else {
            if (field.isDone() && flag == true) {
                timer.setStop();
                field.disable();
                game.player.statistics.changeStatistics(diff, timer.getTime());
                flag = false;
            }
        }

        timer.drawTime();
        field.draw(stage, game);
        stage.act(Gdx.graphics.getDeltaTime());
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
    }
}
