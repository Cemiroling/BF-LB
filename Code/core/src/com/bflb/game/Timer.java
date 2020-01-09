package com.bflb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Timer {
    private float deltaTime = 0;
    public String str;
    public boolean stop;
    public com.badlogic.gdx.scenes.scene2d.ui.Label label;
    public Stage stage;
    public boolean countdown;
    public boolean start;
    public int seconds;
    public boolean startCount;

    public Timer(MyGame game) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = game.gameInterface.font;
        label = new Label("Time:", style);
        label.setPosition((int) (Gdx.graphics.getWidth() * 0.25), (int) (Gdx.graphics.getHeight() * 0.8));
        label.setFontScale(3.f, 3.f);
        stage = new Stage();
        stage.addActor(label);
    }

    public Timer(MyGame game, int seconds) {
        this.seconds = seconds;
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = game.gameInterface.font;
        label = new Label("", style);
        label.setPosition((int) (Gdx.graphics.getWidth() / 2 - 200), (int) (Gdx.graphics.getHeight() * 1 / 3));
        label.setFontScale(3.f, 3.f);
        stage = new Stage();
        stage.addActor(label);
        countdown = true;
    }

    public void start() {
        startCount = true;
    }

    public void setTime(String string) {
        str = string;
    }

    public void update() {
        if (!stop && startCount) {
            deltaTime += Gdx.graphics.getDeltaTime();
            str = Float.toString(deltaTime);
            str = str.replace('.', ':');
            str = str.split(":")[0] + str.substring(str.indexOf(":"), str.indexOf(":") + 3);
        }
    }

    public void drawTime() {
        if (!countdown) {
            update();
            label.setText("Time: " + str);
            stage.draw();
        } else {
            deltaTime += Gdx.graphics.getDeltaTime();
            float remaining = seconds - deltaTime;
            if (remaining < 0)
                start = true;
            str = Float.toString(remaining);
            str = str.replace('.', ':');
            str = str.split(":")[0] + str.substring(str.indexOf(":"), str.indexOf(":") + 3);
            label.setText(str);
            stage.draw();
        }
    }


    public float getTime() {
        return deltaTime;
    }

    public String getStr(){
        update();
        return str;
    }

    public void setStop() {
        stop = true;
    }
}