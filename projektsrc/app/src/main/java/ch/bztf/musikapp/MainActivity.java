package ch.bztf.musikapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    RecyclerView songs_da;
    TextView keine_songs;
    //Der ArrayList ist schneller als der "Normale" beim Zugriff auf einzelne Elemente
    ArrayList<AudioModel> liste = new ArrayList<>();

    /**
     * Hier werden alle grundlegenden Initialisierungsarbeiten gemacht, die nur einmal in der ganzen ersten Activity gebraucht werden.
     * Alles was hier gespeichert wird, passiert nur einmal.
     * Mit dem @Override "Befehl", wird geprüft, ob die Klasse MainActivity wirklich die Daten hat, die er braucht.
     */
    @Override
    //Dank savedInstanceState wird der Zustand der Activity nicht Verändert
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songs_da = findViewById(R.id.songs_da);
        keine_songs = findViewById(R.id.keine_songs);
        /*
        Hier checkt er, ob die Erlaubnis vorhanden nicht.
        Wenn nicht, dann holt er sich diese.
        */
        if(Ueberpruefeerlaubnis() == false){
            Holerlaubins();
            return;
        }
        String[] datenanzeige ={
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
        };
        String auswaehlen = MediaStore.Audio.Media.IS_MUSIC + " !=0 ";
        //Hier werden alle Songs in die songdaten gespeichert.
        //getContentResolver() = Damit holt er sich "Infos" von Daten, die nicht auf der App sind.
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,datenanzeige, auswaehlen, null, null);
        //Damit erstelt man ein neuse AudioModel.
        while(cursor.moveToNext()){

            AudioModel songdaten = new AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2));
            //Falls Song x existiert, wird dieser in die Liste eingetragen.
            if(new File(songdaten.getPfad()).exists())
                /*
                Falls ein Song gelöscht wird, bleibt dieser in der Datenbank.
                Wenn man einen Song aus der Datenbank/Handy entfert, ist er auch bei der Übersicht weg.
                */
                liste.add(songdaten);
        }
        //Falls er keine Songs findet, soll er den Text "KEINE SONGS GEFUNDEN" anzeigen.
        if(liste.size() == 0){
            keine_songs.setVisibility(View.VISIBLE);
        }
        else{
            songs_da.setLayoutManager(new LinearLayoutManager(this));
            songs_da.setAdapter(new MusikListe(liste, getApplicationContext()));
        }
    }
    /**
     * Diese Methode ist dafür da, um zu überprüfen, ob er die Erlaubnis hat auf lokale Dateien zuzugreiffen.
     * @return true Wert
     */
    public boolean Ueberpruefeerlaubnis(){
        //Manifest = Informationen über die in einem JAR-Datei gespeicherten Dateien.
        int check = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        //Falls er die Erlaubnis bekommen hat, gibt er hier true an sonst false.
        if(check == PackageManager.PERMISSION_GRANTED){
            return true;
        } else{
            return false;
        }
    }
    /**
     * Diese Methode holt sich die Erlaubnis, damit er auf die mp3-Dateien auf dem Handy zugreifen kann.
     */
    public void Holerlaubins(){
        //Wenn man die Erlaubnis denined, dann gibt er jedes mal den Toast aus.
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            //length_short = kurze Anzeigedauer
            Toast.makeText(MainActivity.this, "Wir brauchen noch eine Erlaubniss von Ihnen, damit wir auf Ihre MP3-Dateien zugreiffen können!", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 69);
        }
    }
}