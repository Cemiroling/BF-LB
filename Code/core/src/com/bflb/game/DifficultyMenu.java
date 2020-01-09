package com.bflb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class DifficultyMenu implements Screen {
    private final MyGame game;
    private Array<TextButton> textButtonArray;
    private Stage stage;
    private int diff;
    private boolean fromLan;
    private boolean isHost;
    private Label.LabelStyle style;
    private int updateRate;
    private boolean ready;
    private Timer timer;
    private TextButton yourReady;
    private TextButton opponentReady;

    public DifficultyMenu(final MyGame game, int dif, final boolean fromLan, final boolean isHost) {
        //Gdx.files.local("stats.txt").delete();
        this.game = game;
        stage = new Stage(new ScreenViewport());
        textButtonArray = new Array<TextButton>();
        this.diff = dif;
        this.fromLan = fromLan;
        this.isHost = isHost;

        if(fromLan){

            if(isHost){

            }
            else {

            }
        }

        for (int i = 0; i < 6; i++) {
            TextButton textButton = new TextButton(String.valueOf(i + 3) + "×" + String.valueOf(i + 3), game.gameInterface.diffButtonsStyle);
            textButton.setWidth(400);
            textButton.setHeight(400);
            textButton.getLabel().setFontScale(2f, 2f);
            textButtonArray.add(textButton);
        }

        final TextButton textButton = new TextButton("back", game.gameInterface.shortButtonStyle);
        textButton.setWidth(400);
        textButton.setHeight(250);
        textButton.getLabel().setFontScale(2f, 2f);
        textButtonArray.add(textButton);
        TextButton textButton1 = new TextButton("start", game.gameInterface.shortButtonStyle);
        textButton1.setWidth(400);
        textButton1.setHeight(250);
        textButton1.getLabel().setFontScale(2f, 2f);
        textButtonArray.add(textButton1);
        textButtonArray.get(0).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                diff = 3;
                if (fromLan && isHost) {
                    game.lanGame.server.diff = 3;
                    game.lanGame.server.sendDiff();
                }
            }
        });
        textButtonArray.get(1).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                diff = 4;
                if (fromLan && isHost) {
                    game.lanGame.server.diff = 4;
                    game.lanGame.server.sendDiff();
                }
            }
        });
        textButtonArray.get(2).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                diff = 5;
                if (fromLan && isHost) {
                    game.lanGame.server.diff = 5;
                    game.lanGame.server.sendDiff();
                }
            }
        });
        textButtonArray.get(3).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                diff = 6;
                if (fromLan && isHost) {
                    game.lanGame.server.diff = 6;
                    game.lanGame.server.sendDiff();
                }
            }
        });
        textButtonArray.get(4).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                diff = 7;
                if (fromLan && isHost) {
                    game.lanGame.server.diff = 7;
                    game.lanGame.server.sendDiff();
                }
            }
        });
        textButtonArray.get(5).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                diff = 8;
                if (fromLan && isHost) {
                    game.lanGame.server.diff = 8;
                    game.lanGame.server.sendDiff();
                }
            }
        });
        textButtonArray.get(6).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (fromLan) {
                    if (isHost) {
                        game.lanGame.server.serverStop();
                    } else {
                        game.lanGame.client.disconnect();
                        game.lanGame.client.client.stop();
                        game.lanGame.client.start();
                    }
                    game.setScreen(new LanMenu(game));
                } else
                    game.setScreen(new MainMenu(game));
            }
        });
        textButtonArray.get(7).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (fromLan) {
                    if (isHost) {
                        game.lanGame.server.ready();
                        ready = true;
                        if (game.lanGame.server.opponentReady)
                            timer = new Timer(game, 3);
                    } else {
                        game.lanGame.client.ready();
                        ready = true;
                        if (game.lanGame.client.opponentReady)
                            timer = new Timer(game, 3);
                    }
                    textButtonArray.get(7).setDisabled(true);
                } else {
                    game.setScreen(new GameScreen(game, diff, fromLan, isHost));
                }
            }
        });

        ButtonGroup<TextButton> buttonGroup = new ButtonGroup<TextButton>(
                textButtonArray.get(0),
                textButtonArray.get(1),
                textButtonArray.get(2),
                textButtonArray.get(3),
                textButtonArray.get(4),
                textButtonArray.get(5));
        buttonGroup.setMaxCheckCount(1);

        int space = Gdx.graphics.getHeight() / 4;
        if (fromLan)
            space = Gdx.graphics.getHeight() / 5;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                textButtonArray.get(i * 2 + j).setPosition(Gdx.graphics.getWidth() / 2 - 500 + (600 * j), Gdx.graphics.getHeight() - ((i + 1) * space));
            }
        }

        style = new Label.LabelStyle();
        style.font = game.gameInterface.font;

        loadStage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    private void loadStage() {
        updateRate = 0;
        stage.clear();
        if (fromLan) {
            Label name = new Label(game.player.name, style);
            name.setPosition(150, textButtonArray.get(5).getY() - 270);

            stage.addActor(name);

            TextButton stats = new TextButton("stats", game.gameInterface.shortButtonStyle);
            stats.setHeight(100);
            stats.setWidth(150);
            stats.setPosition(550, textButtonArray.get(5).getY() - 300);
            stats.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(isHost)
                        game.lanGame.server.unready();
                    else
                        game.lanGame.client.unready();
                    game.setScreen(new StatsScreen(game, fromLan, isHost, true));
                }
            });
            textButtonArray.add(stats);

            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.up = game.gameInterface.skin.getDrawable("unpressed");
            textButtonStyle.checked = game.gameInterface.skin.getDrawable("pressed");
            textButtonStyle.font = game.gameInterface.font;
            TextButton isReady = new TextButton("ready", textButtonStyle);
            isReady.setWidth(100);
            isReady.setHeight(100);
            if (ready) {
                isReady.setChecked(true);
                if(isHost){
                    game.lanGame.server.ready();
                }
                else {
                    game.lanGame.client.ready();
                }
            }
            isReady.setPosition(700, textButtonArray.get(5).getY() - 300);
            isReady.setTouchable(Touchable.disabled);
            stage.addActor(isReady);

            if (isHost) {
                if (game.lanGame.server.opponentConnected) {
                    Label name1 = new Label(game.lanGame.server.playerPacket.player.name, style);
                    name1.setPosition(150, textButtonArray.get(5).getY() - 370);

                    TextButton stats1 = new TextButton("stats", game.gameInterface.shortButtonStyle);
                    stats1.setHeight(100);
                    stats1.setWidth(150);
                    stats1.setPosition(550, textButtonArray.get(5).getY() - 400);
                    stats1.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            game.setScreen(new StatsScreen(game, fromLan, isHost, false));
                        }
                    });

                    TextButton isReady1 = new TextButton("ready", textButtonStyle);
                    isReady1.setPosition(700, textButtonArray.get(5).getY() - 400);
                    if (game.lanGame.server.opponentReady) {
                        isReady1.setChecked(true);
                    }
                    isReady1.setTouchable(Touchable.disabled);
                    isReady1.setWidth(100);
                    isReady1.setHeight(100);
                    stage.addActor(isReady1);
                    stage.addActor(stats1);
                    stage.addActor(name1);
                }
            } else {
                Label name1 = new Label(game.lanGame.client.playerPacket.player.name, style);
                name1.setPosition(150, textButtonArray.get(5).getY() - 370);

                TextButton stats1 = new TextButton("stats", game.gameInterface.shortButtonStyle);
                stats1.setHeight(100);
                stats1.setWidth(150);
                stats1.setPosition(550, textButtonArray.get(5).getY() - 400);
                stats1.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        game.setScreen(new StatsScreen(game, fromLan, isHost, false));
                    }
                });

                TextButton isReady1 = new TextButton("ready", textButtonStyle);
                isReady1.setPosition(700, textButtonArray.get(5).getY() - 400);
                if (game.lanGame.client.opponentReady) {
                    isReady1.setChecked(true);
                }
                isReady1.setTouchable(Touchable.disabled);
                isReady1.setWidth(100);
                isReady1.setHeight(100);
                stage.addActor(isReady1);
                stage.addActor(stats1);
                stage.addActor(name1);

                for (int i = 0; i < 6; i++)
                    textButtonArray.get(i).setTouchable(Touchable.disabled);
                textButtonArray.get(game.lanGame.client.diff - 3).setChecked(true);
            }

        }

        textButtonArray.get(6).setPosition(Gdx.graphics.getWidth() / 2 - 500, 50);
        textButtonArray.get(7).setPosition(Gdx.graphics.getWidth() / 2 + 100, 50);


        for (int i = 0; i < textButtonArray.size; i++) {
            stage.addActor(textButtonArray.get(i));
        }

        textButtonArray.get(diff - 3).setChecked(true);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (fromLan) {
            if (isHost) {
                if (ready && game.lanGame.server.opponentReady) {
                    for (int i = 0; i < 6; i++)
                        textButtonArray.get(i).setTouchable(Touchable.disabled);
                    if(timer == null || game.lanGame.server.restartTimer){
                        game.lanGame.server.restartTimer = false;
                        timer = new Timer(game, 3);
                    }
                    timer.drawTime();
                    if (timer.start)
                        game.setScreen(new GameScreen(game, diff, fromLan, isHost));
                }
                if(!game.lanGame.server.opponentConnected){
                    for (int i = 0; i < 6; i++)
                        textButtonArray.get(i).setTouchable(Touchable.enabled);
                }
            } else {
                if (ready && game.lanGame.client.opponentReady) {
                    if(timer == null || game.lanGame.client.restartTimer){
                        game.lanGame.client.restartTimer = false;
                        timer = new Timer(game, 3);
                    }
                    timer.drawTime();
                    if (timer.start)
                        game.setScreen(new GameScreen(game, diff, fromLan, isHost));
                }
                if(game.lanGame.client.updateDiff){
                    game.lanGame.client.updateDiff = false;
                    textButtonArray.get(game.lanGame.client.diff - 3).setChecked(true);
                }
            }
        }

        if (updateRate > 100) {
            loadStage();
        }
        updateRate++;

        if (!isHost && game.lanGame.client.serverStop) {
            game.lanGame.client.serverStop = false;
            game.lanGame.client.client.stop();
            game.lanGame.client.start();
            game.setScreen(new LanMenu(game));
        }

        if (isHost && fromLan) {
            if (game.lanGame.server.sendPlayer) {
                game.lanGame.server.sendPlayer(game.player);
                game.lanGame.server.sendRequest();
            }
        }

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
