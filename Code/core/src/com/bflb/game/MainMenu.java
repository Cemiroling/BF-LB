package com.bflb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

public class MainMenu implements Screen {
    private final MyGame game;
    private Array<TextButton> textButtonArray;
    private Stage stage;

    public MainMenu(final MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        textButtonArray = new Array<TextButton>();

        TextButton textButton = new TextButton("Singleplayer", game.gameInterface.longButtonStyle);
        textButton.setWidth(1000);
        textButton.setHeight(300);
        textButton.getLabel().setFontScale(2f, 2f);
        textButton.setPosition(40, 640);
        textButtonArray.add(textButton);
        TextButton textButton1 = new TextButton("LAN", game.gameInterface.longButtonStyle);
        textButton1.setWidth(1000);
        textButton1.setHeight(300);
        textButton1.getLabel().setFontScale(2f, 2f);
        textButton1.setPosition(40, 340);
        textButtonArray.add(textButton1);
        TextButton textButton2 = new TextButton("FAQ", game.gameInterface.shortButtonStyle);
        textButton2.setWidth(450);
        textButton2.setHeight(300);
        textButton2.getLabel().setFontScale(2f, 2f);
        textButton2.setPosition(40, 40);
        textButtonArray.add(textButton2);
        TextButton textButton3 = new TextButton("Exit", game.gameInterface.shortButtonStyle);
        textButton3.setWidth(450);
        textButton3.setHeight(300);
        textButton3.getLabel().setFontScale(2f, 2f);
        textButton3.setPosition(590, 40);
        textButtonArray.add(textButton3);
        TextButton textButton4 = new TextButton("Statistics", game.gameInterface.shortButtonStyle);
        textButton4.setWidth(450);
        textButton4.setHeight(300);
        textButton4.getLabel().setFontScale(2f, 2f);
        textButton4.setPosition(590, 940);
        textButtonArray.add(textButton4);

        textButtonArray.get(0).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.clear();
                switchScreen(game, new DifficultyMenu(game, 3,false,false));
            }
        });
        textButtonArray.get(1).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.clear();
                switchScreen(game, new LanMenu(game));
            }
        });
        textButtonArray.get(2).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchScreen(game, new FAQScreen(game));
            }
        });
        textButtonArray.get(3).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        textButtonArray.get(4).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchScreen(game, new StatsScreen(game, false, false, false));
            }
        });

        Pixmap labelColor = new Pixmap(200, 100, Pixmap.Format.RGB888);
        labelColor.setColor(Color.LIGHT_GRAY);
        labelColor.fill();
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.fontColor = Color.CHARTREUSE;
        textFieldStyle.font = game.gameInterface.font;
        textFieldStyle.background = new Image(new Texture(labelColor)).getDrawable();

        final TextField textField = new TextField(game.player.name, textFieldStyle);
        textField.setHeight(80);
        textField.setWidth(220);
        textField.setMaxLength(15);
        textField.setPosition(300, 1050);
        stage.addActor(textField);

        TextButton changeName = new TextButton("Change name", game.gameInterface.longButtonStyle);
        changeName.setWidth(220);
        changeName.setHeight(100);
        changeName.getLabel().setFontScale(1f, 1f);
        changeName.setPosition(300, 950);
        changeName.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!textField.getText().equals(""))
                    game.player.setName(textField.getText());
                game.player.statistics.saveStats();
            }
        });
        stage.addActor(changeName);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = game.gameInterface.font;
        Label name = new Label("Your name:", style);
        name.setPosition(100, 1070);
        name.setFontScale(1.f, 1.f);
        stage.addActor(name);

        for(int i = 0; i< 5; i++) {
            stage.addActor(textButtonArray.get(i));
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    public void switchScreen(final MyGame game, final Screen newScreen) {
        stage.getRoot().getColor().a = 1;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(fadeOut(0.3f));
        sequenceAction.addAction(run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(newScreen);
            }
        }));
        stage.getRoot().addAction(sequenceAction);
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
