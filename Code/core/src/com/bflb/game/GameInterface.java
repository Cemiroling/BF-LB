package com.bflb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameInterface {
    public TextButton.TextButtonStyle shortButtonStyle;
    public TextButton.TextButtonStyle longButtonStyle;
    public ImageButton.ImageButtonStyle pressedCircleButton;
    public ImageButton.ImageButtonStyle unpressedCircleButton;
    public TextButton.TextButtonStyle diffButtonsStyle;
    public SpriteBatch batch;
    public Skin skin;
    public TextureAtlas atlas;
    public BitmapFont font;

    GameInterface(){
        atlas = new TextureAtlas(Gdx.files.internal("texture.atlas"));
        skin = new Skin(atlas);
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        batch = new SpriteBatch();

        shortButtonStyle = new TextButton.TextButtonStyle();
        longButtonStyle = new TextButton.TextButtonStyle();
        pressedCircleButton = new ImageButton.ImageButtonStyle();
        unpressedCircleButton = new ImageButton.ImageButtonStyle();
        diffButtonsStyle = new TextButton.TextButtonStyle();

        diffButtonsStyle.checked = skin.getDrawable("pressed");
        diffButtonsStyle.over = skin.getDrawable("pressed");
        diffButtonsStyle.up = skin.getDrawable("unpressed");
        diffButtonsStyle.font = font;

        pressedCircleButton.up = skin.getDrawable("pressed");
        unpressedCircleButton.up = skin.getDrawable("unpressed");

        longButtonStyle.down = skin.getDrawable("longpressed");
        longButtonStyle.over = skin.getDrawable("longpressed");
        longButtonStyle.up = skin.getDrawable("longunpressed");
        longButtonStyle.font = font;

        shortButtonStyle.down = skin.getDrawable("shortpressed");
        shortButtonStyle.over = skin.getDrawable("shortpressed");
        shortButtonStyle.up = skin.getDrawable("shortunpressed");
        shortButtonStyle.font = font;
    }
}
