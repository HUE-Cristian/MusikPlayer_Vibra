package ch.bztf.musikapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusikInfo extends AppCompatActivity{

    TextView title;
    ImageView pauseplay,next,previous,replay;
    ArrayList<AudioModel> songListe;
    AudioModel aktueller_song;
    MediaPlayer player = MP3Player.getMP();
    String dauer;

    /**
     * Hier werden alle grundlegenden Initialisierungsarbeiten gemacht, die nur einmal in der ganzen ersten Activity gebraucht werden.
     * Mit dem @Override "Befehl", wird geprüft, ob die Klasse MusikInfo wirklich die Daten hat, die er braucht.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musik_info);

        //Hier werden alle Variablen den IDs des xml files zugeordnet.
        title = findViewById(R.id.song_titel);
        pauseplay = findViewById(R.id.pause_play);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        replay = findViewById(R.id.replay);

        //Damit bekommt man die Songliste.
        songListe = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LISTE");

        setAlleDaten();
    }

    /**
     * Hier werden die Buttons gesezt.
     */
    public void setAlleDaten(){
        aktueller_song = songListe.get(MP3Player.aktuellausgewaelt);
        title.setText(aktueller_song.getName());
        dauer = aktueller_song.getDauer();

        //Buttons um zum nächsten/vorherigen Song zu gehen oder zu pausieren.
        //Hier werden die Buttons "Klickbereit gemacht."
        pauseplay.setOnClickListener(v-> pausePlay());
        next.setOnClickListener(v-> nextSong());
        previous.setOnClickListener(v-> previousSong());
        replay.setOnClickListener(v-> replay());

        spieleMusik();
    }

    /**
     * Hier wird alles "vorbereitet, damit der erste/nächste Song gespielt werden kann."
     */
    private void spieleMusik(){

        player.reset();
        try{
        player.setDataSource(aktueller_song.getPfad());
        player.prepare();
        player.start();
        }
        catch (IOException bla){
            bla.printStackTrace();
        }
    }

    /**
     * Damit wird der Parameter aktuellausgewaelt um eins erhöht und somit wird zum nöchsten Song vorgespult.
     * Die If-Else Abfrage ist damit die App nicht abstürzt.
     */
    private void nextSong(){

            //Wenn letzter Song, geht er zum ersten
            if (MP3Player.aktuellausgewaelt == songListe.size()-1) {
                MP3Player.aktuellausgewaelt = 0;
            }
            //Wenn auf "next-Button" geklickt, dann ein SOng weiter.
            else
                MP3Player.aktuellausgewaelt += 1;
                player.reset();
                setAlleDaten();
    }

    /**
     * Mit dieser Methode, wird bei dem drücken des "Previous-Buttons" ein Song nach hinten gespult.
     * Wie auch schon bei der letzten Methode wird hier der Song, falls dieser der erste ist auf den letzten der Liste hingespult.
     */
    private void previousSong(){
        //Damit kann man verhindern, dass die App abstürzt, sobald es "hinter dem Array" gehen will.
        //Das heisst, dass es zum letzten Song springt sobald man beim ersten zurückspult.
        if(MP3Player.aktuellausgewaelt == 0){
            MP3Player.aktuellausgewaelt = songListe.size()-1;
        }
        else MP3Player.aktuellausgewaelt -=1;
            player.reset();
            setAlleDaten();
    }

    /**
     * Mit dieser Methode wird der Song neu gestertet.
     */
    private void replay(){
        player.reset();
        player.start();
        //Hier wird der Button nochmals neu gesetzt, damit falls man zuerst Stopp gedrückt hat und dann au replay der der Button sich wieder zurücksetzt.
        pauseplay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
        setAlleDaten();
    }

    /**
     * Damit wird per Knopfdruck der Pause/Play-Taste der Song gestoppt oder weitergespielt.
     */
    private void pausePlay(){
        //Wenn Musik spielt, und man Pausiert, wird das Bild geendert.
        if(player.isPlaying()){
            player.pause();
            pauseplay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        }
        //Wenn das Lied dann wieder weiter geht, wird es wieder zurückgesetzt
        else{
            player.start();
            pauseplay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
        }
    }
}