package com.bflb.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Statistics {
    public String name;
    public float[] bestTimes = new float[6];
    public float[] avgTimes = new float[6];
    public int[] playTimes= new int[6];
    public int lanWins;
    public int lanLosts;

    Statistics(){
        loadStats();
    }

    public void loadStats(){
        boolean exists = Gdx.files.local("stats.txt").exists();
        if(!exists){
            createFile();
        }
        FileHandle file = Gdx.files.local("stats.txt");
        String text = file.readString();
        splitString(text);
    }

    public void splitString(String text){
        String[] parts = text.split("~");
        name = parts[0];
        String[] parts1 = parts[1].split("!");
        for (int i = 0; i < parts1.length; i++){
            bestTimes[i] = Float.valueOf(parts1[i]);
        }
        String[] parts2 = parts[2].split("!");
        for (int i = 0; i < parts2.length; i++){
            avgTimes[i] = Float.valueOf(parts2[i]);
        }
        String[] parts3 = parts[3].split("!");
        for (int i = 0; i < parts3.length; i++){
            playTimes[i] = Integer.valueOf(parts3[i]);
        }
        lanWins = Integer.valueOf(parts[4]);
        lanLosts = Integer.valueOf(parts[5]);
    }

    public String createString(){
        StringBuilder str= new StringBuilder();
        str.append(name);
        str.append("~");
        for (int i = 0; i < bestTimes.length; i++){
            str.append(bestTimes[i]+"!");
        }
        str.deleteCharAt(str.lastIndexOf("!"));
        str.append("~");
        for (int i = 0; i < avgTimes.length; i++){
            str.append(avgTimes[i]+"!");
        }
        str.deleteCharAt(str.lastIndexOf("!"));
        str.append("~");
        for (int i = 0; i < playTimes.length; i++){
            str.append(playTimes[i]+"!");
        }
        str.deleteCharAt(str.lastIndexOf("!"));
        str.append("~");
        str.append(lanWins);
        str.append("~");
        str.append(lanLosts);
        return str.toString();
    }

    public void saveStats(){
        FileHandle file = Gdx.files.local("stats.txt");
        file.writeString(createString(), false);
    }

    public void changeStatistics(int diff, float time){
        playTimes[diff-3]++;
        avgTimes[diff-3] = (float)(Math.round((((avgTimes[diff-3]*(playTimes[diff-3]-1)) +  (float)(Math.round(time * 100.0) / 100.0))/playTimes[diff-3])* 100.0) / 100.0);
        if((bestTimes[diff-3] > Math.round(time * 100.0) / 100.0) ||(bestTimes[diff-3] == -1)){
            bestTimes[diff-3] = (float)(Math.round(time * 100.0) / 100.0);
        }
        saveStats();
    }

    public float calcWinrate(){
        int winrate;
        if(lanLosts == 0)
        {
            if(lanWins == 0)
                winrate = 0;
            else
                winrate = 1;
        }
        else {
            winrate = (lanWins*100 )/(lanWins + lanLosts);
        }
        return winrate;
    }

    public void createFile(){
        FileHandle file = Gdx.files.local("stats.txt");
        file.writeString("player228~-1!-1!-1!-1!-1!-1~-1!-1!-1!-1!-1!-1~0!0!0!0!0!0~0~0", false);
    }
}
