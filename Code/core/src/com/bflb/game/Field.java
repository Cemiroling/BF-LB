package com.bflb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class Field {
    private Array<Texture> rowArray;
    private Array<FieldCell> fieldCells;
    private int diff;
    private int[] mas;
    private String[] strings;
    private String[] strings1;
    private Texture uncorrectrow;
    private Texture uncorrectcolumn;
    private Texture correctrow;
    private Texture correctcolumn;
    private boolean[] state;

    Field(int diff, MyGame game){
        this.diff = diff;
        fieldCells = new Array<FieldCell>();
        rowArray = new Array<Texture>();
        state = new boolean[diff*2];

        uncorrectrow = new Texture(Gdx.files.internal("uncorrectrow.png"));
        uncorrectcolumn = new Texture(Gdx.files.internal("uncorrectcolumn.png"));
        correctrow = new Texture(Gdx.files.internal("correctrow.png"));
        correctcolumn = new Texture(Gdx.files.internal("correctcolumn.png"));

        for(int i = 0; i < diff * diff; i++){
            FieldCell fieldCell = new FieldCell(game);
            fieldCell.imageButton.setWidth(900/diff);
            fieldCell.imageButton.setHeight(900/diff);
            fieldCells.add(fieldCell);
        }

        for(int i = 0; i < diff; i++) {
            for(int j = 0; j < diff; j++) {
                fieldCells.get(i*diff +j).imageButton.setPosition(Gdx.graphics.getWidth()/2 - 500 + ((900/diff) *j) , Gdx.graphics.getHeight()/2 - 500 + (i * (900/diff)));
            }
        }


        mas = new int[2 * diff];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < diff; i++) {
            int num = random.nextInt((int) Math.pow(2, diff));
            mas[i] = num;
        }

        strings = new String[2 * diff];
        strings1 = new String[2 * diff];

        for (int i = 0; i < diff; i++) {
            String temp = Integer.toBinaryString(mas[i]);
            int width = diff;
            char fill = '0';
            String tmp = new String(new char[width - temp.length()]).replace('\0', fill) + temp;
            strings[i] = tmp;
        }

        for (int i = diff; i < 2 * diff; i++) {
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j < diff; j++) {
                temp.append(strings[j].charAt(i - diff) - 48);
            }
            strings[i] = temp.toString();
        }

        for (int i = diff; i < 2 * diff; i++) {
            int num = Integer.parseInt(strings[i], 2);
            mas[i] = num;
        }
    }

    public void updateRows(){
        for (int i = 0; i < diff * 2; i++){
            if(strings[i].equals(strings1[i])){
                state[i] = true;
            }
            else {
                state[i] = false;
            }
        }
    }

    public void loadRowsColumns(){
        rowArray.clear();
        for (int i = 0; i < 2 * diff; i++){
            if(i < diff){
                if(state[diff - 1 - i])
                    rowArray.add(correctrow);
                else {
                    rowArray.add(uncorrectrow);
                }
            }
            else {
                if (state[i]){
                    rowArray.add(correctcolumn);
                }
                else {
                    rowArray.add(uncorrectcolumn);
                }
            }
        }
    }

    public boolean isDone(){
        for (int i = 0; i < diff *2; i++){
            if(!state[i])
                return false;

        }
        return true;
    }

    public void saveState(){
        for (int i = 0; i < diff; i++) {
            StringBuilder binary = new StringBuilder();
            for (int j = 0; j < diff; j++) {
                if (getFieldCells().get(diff * i + j).isState())
                    binary.append(1);
                else
                    binary.append(0);
            }
            strings1[diff - 1 - i] = binary.toString();

        }

        for (int i = 0; i < diff; i++) {
            StringBuilder binary = new StringBuilder();
            for (int j = 0; j < diff; j++) {
                if (getFieldCells().get(diff * diff - (j+1) * diff + i).isState())
                    binary.append(1);
                else
                    binary.append(0);
            }
            strings1[diff + i] = binary.toString();
        }
    }

    public void setActors(Stage stage){
        for(int i = 0; i<diff * diff; i++) {
            stage.addActor(fieldCells.get(i).imageButton);
        }
    }

    public void disable(){
        for (int i = 0; i < diff * diff; i++)
            fieldCells.get(i).imageButton.setTouchable(Touchable.disabled);
    }

    public void draw(Stage stage, MyGame game){
        saveState();
        updateRows();
        loadRowsColumns();
        game.gameInterface.batch.begin();
        for(int i = 0; i < diff; i++){
            game.gameInterface.batch.draw(rowArray.get(i),fieldCells.get(i * diff).imageButton.getX() + 50, fieldCells.get(i * diff).imageButton.getY() + 800/(diff *3),
                    Gdx.graphics.getWidth() - 250, 1000/(diff*3));
            game.gameInterface.font.draw(game.gameInterface.batch, Integer.toString(mas[diff - 1 - i]), Gdx.graphics.getWidth()- 100, fieldCells.get(i * diff).imageButton.getY() + 1500/(diff *3));
        }

        for(int i = 0; i < diff; i++){
            game.gameInterface.batch.draw(rowArray.get(i + diff),fieldCells.get(i).imageButton.getX() + 1000/(diff *3), fieldCells.get(i).imageButton.getY() + 50,
                    1000/(diff*3), Gdx.graphics.getWidth() - 250);
            game.gameInterface.font.draw(game.gameInterface.batch, Integer.toString(mas[i + diff]), fieldCells.get(i).imageButton.getX() + 1000/(diff *3), fieldCells.get(i).imageButton.getY() - 25);
        }
        game.gameInterface.batch.end();
        stage.draw();
    }

    public Array<FieldCell> getFieldCells() {
        return fieldCells;
    }
}
