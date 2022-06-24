package ch.bztf.musikapp;

import android.media.MediaPlayer;

public class MP3Player{

    static MediaPlayer MP;

    /**
     * Wenn es das erste mal ist, dann erschafft er einen neuen Mediaplayer.
     * Wenn er es schon einmal gemacht hat, bekommt er der der schon vorhanden ist.
     * @return MP
     */
    public static MediaPlayer getMP(){
        if(MP == null){
            MP = new MediaPlayer();
        }
        return MP;

        

    }

    /**
     * Der Song ist nicht ausgewählt
     */
    public static int aktuellausgewaelt = -1;
}
