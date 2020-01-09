package com.bflb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StatsScreen implements Screen {
    private final MyGame game;
    private Array<TextButton> textButtonArray;
    private Array<Label> labelArray;
    private Stage stage;


    public StatsScreen(final MyGame game, final boolean fromLan, final boolean isHost, final boolean itSelf) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        textButtonArray = new Array<TextButton>();
        labelArray = new Array<Label>();

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = game.gameInterface.font;

        Label statistics = new Label("Statistics", style);
        statistics.setFontScale(3f, 3f);
        statistics.setPosition(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() - 100);
        stage.addActor(statistics);

        if (fromLan && !itSelf) {
            if (isHost) {
                for (int i = 0; i < 6; i++) {
                    if (game.lanGame.server.getPlayer().statistics.bestTimes[i] < 0) {
                        Label label1 = new Label("Best time:  N/A", style);
                        label1.setFontScale(1.5f, 1.5f);
                        Label label2 = new Label("Average time:  N/A", style);
                        label2.setFontScale(1.5f, 1.5f);
                        labelArray.add(label1);
                        labelArray.add(label2);
                    } else {
                        Label label1 = new Label("Best time:  " + Float.toString(game.lanGame.server.getPlayer().statistics.bestTimes[i]).replace('.', ':'), style);
                        label1.setFontScale(1.5f, 1.5f);
                        Label label2 = new Label("Average time:  " + Float.toString(game.lanGame.server.getPlayer().statistics.avgTimes[i]).replace('.', ':'), style);
                        label2.setFontScale(1.5f, 1.5f);
                        labelArray.add(label1);
                        labelArray.add(label2);
                    }
                }
                Label label1 = new Label("Winrate:  " + game.lanGame.server.getPlayer().statistics.calcWinrate() + "%", style);
                label1.setFontScale(1.5f, 1.5f);
                Label label2 = new Label("Wins  :  " + game.lanGame.server.getPlayer().statistics.lanWins + "    Losts  :  " + game.lanGame.server.getPlayer().statistics.lanLosts, style);
                label2.setFontScale(1.5f, 1.5f);
                labelArray.add(label1);
                labelArray.add(label2);
            } else {
                for (int i = 0; i < 6; i++) {
                    if (game.lanGame.client.getPlayer().statistics.bestTimes[i] < 0) {
                        Label label1 = new Label("Best time:  N/A", style);
                        label1.setFontScale(1.5f, 1.5f);
                        Label label2 = new Label("Average time:  N/A", style);
                        label2.setFontScale(1.5f, 1.5f);
                        labelArray.add(label1);
                        labelArray.add(label2);
                    } else {
                        Label label1 = new Label("Best time:  " + Float.toString(game.lanGame.client.getPlayer().statistics.bestTimes[i]).replace('.', ':'), style);
                        label1.setFontScale(1.5f, 1.5f);
                        Label label2 = new Label("Average time:  " + Float.toString(game.lanGame.client.getPlayer().statistics.avgTimes[i]).replace('.', ':'), style);
                        label2.setFontScale(1.5f, 1.5f);
                        labelArray.add(label1);
                        labelArray.add(label2);
                    }
                }
                Label label1 = new Label("Winrate:  " + game.lanGame.client.getPlayer().statistics.calcWinrate() + "%", style);
                label1.setFontScale(1.5f, 1.5f);
                Label label2 = new Label("Wins  :  " + game.lanGame.client.getPlayer().statistics.lanWins + "    Losts  :  " + game.lanGame.client.getPlayer().statistics.lanLosts, style);
                label2.setFontScale(1.5f, 1.5f);
                labelArray.add(label1);
                labelArray.add(label2);
            }
        } else {
            for (int i = 0; i < 6; i++) {
                if (game.player.statistics.bestTimes[i] < 0) {
                    Label label1 = new Label("Best time:  N/A", style);
                    label1.setFontScale(1.5f, 1.5f);
                    Label label2 = new Label("Average time:  N/A", style);
                    label2.setFontScale(1.5f, 1.5f);
                    labelArray.add(label1);
                    labelArray.add(label2);
                } else {
                    Label label1 = new Label("Best time:  " + Float.toString(game.player.statistics.bestTimes[i]).replace('.', ':'), style);
                    label1.setFontScale(1.5f, 1.5f);
                    Label label2 = new Label("Average time:  " + Float.toString(game.player.statistics.avgTimes[i]).replace('.', ':'), style);
                    label2.setFontScale(1.5f, 1.5f);
                    labelArray.add(label1);
                    labelArray.add(label2);
                }
            }
            Label label1 = new Label("Winrate:  " + game.player.statistics.calcWinrate() + "%", style);
            label1.setFontScale(1.5f, 1.5f);
            Label label2 = new Label("Wins  :  " + game.player.statistics.lanWins + "    Losts  :  " + game.player.statistics.lanLosts, style);
            label2.setFontScale(1.5f, 1.5f);
            labelArray.add(label1);
            labelArray.add(label2);
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                labelArray.get(i * 2 + j).setPosition(400, Gdx.graphics.getHeight() + 130 - (j * 100) - ((i + 2) * Gdx.graphics.getHeight() / 10));
            }
        }

        for (int i = 0; i < 14; i++) {
            stage.addActor(labelArray.get(i));
        }

        for (int i = 0; i < 6; i++) {
            TextButton textButton = new TextButton(String.valueOf(i + 3) + "×" + String.valueOf(i + 3), game.gameInterface.diffButtonsStyle);
            textButton.setWidth(200);
            textButton.setHeight(200);
            textButton.getLabel().setFontScale(1f, 1f);
            textButtonArray.add(textButton);
        }
        TextButton textButton1 = new TextButton("LAN", game.gameInterface.diffButtonsStyle);
        textButton1.setWidth(200);
        textButton1.setHeight(200);
        textButton1.getLabel().setFontScale(1f, 1f);
        textButtonArray.add(textButton1);

        TextButton textButton = new TextButton("back", game.gameInterface.shortButtonStyle);
        textButton.setWidth(400);
        textButton.setHeight(250);
        textButton.getLabel().setFontScale(2f, 2f);
        textButton.setPosition(Gdx.graphics.getWidth() / 2 - 200, 50);
        textButtonArray.add(textButton);


        textButtonArray.get(7).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (fromLan) {
                    if (isHost) {
                        game.setScreen(new DifficultyMenu(game, game.lanGame.server.diff, fromLan, isHost));
                    } else {
                        game.setScreen(new DifficultyMenu(game, 3, fromLan, isHost));
                    }
                } else
                    game.setScreen(new MainMenu(game));
            }
        });

        for (int i = 0; i < 8; i++) {
            stage.addActor(textButtonArray.get(i));
        }


        for (int i = 0; i < 7; i++) {
            textButtonArray.get(i).setPosition(100, Gdx.graphics.getHeight() - ((i + 2) * Gdx.graphics.getHeight() / 10));
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
    }
}
