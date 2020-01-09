package com.bflb.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class FieldCell {
    public ImageButton imageButton;
    private boolean state;

    public FieldCell(final MyGame game) {
        imageButton = new ImageButton(game.gameInterface.unpressedCircleButton);
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeStyle(game);
            }
        });
    }

    public boolean isState() {
        return state;
    }

    private void changeStyle(final MyGame game){
        if(state) {
            imageButton.setStyle(game.gameInterface.unpressedCircleButton);
            state = false;
        }
        else{
            imageButton.setStyle(game.gameInterface.pressedCircleButton);
            state = true;
        }
    }

}
