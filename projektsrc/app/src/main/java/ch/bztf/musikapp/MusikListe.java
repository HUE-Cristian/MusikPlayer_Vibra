package ch.bztf.musikapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusikListe extends RecyclerView.Adapter<MusikListe.ViewHolder>{

    ArrayList<AudioModel> songliste;
    //Zugriff auf spezifische Recourcen und Klassen
    Context context;

    /**
     * Die parameter, die eingeschrieben werden,werden an diese Klasse weitergegeben.
     * @param songliste
     * @param context
     */
    public MusikListe(ArrayList<AudioModel> songliste, Context context){
        this.songliste = songliste;
        this.context = context;
    }

    /**
     * Damit wird der recycler_item zu Viewholder "übertragen".
     * Mit dem @Override "Befehl", wird geprüft, ob die Klasse MusikListe wirklich die Daten hat, die er braucht.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View songansicht = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new MusikListe.ViewHolder(songansicht);
    }

    /**
     * Damit holt er sich aus der Songliste den Songnamen.
     * Mit @SuppressLint werden alle Warungen unterdrückt und "ignoriert".
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position){
    AudioModel songdaten = songliste.get(position);
    holder.titel.setText(songdaten.getName());

    //Damit kann man kann auf die einzelnen Songs klicken.
    holder.itemView.setOnClickListener(new View.OnClickListener(){

        /**
         * Damit navigiert er zu einer anderen Klasse/Seite
         */
        @Override
        public void onClick(View view){

            // Ab hier geht die App in die nächste Sektion.
            MP3Player.getMP().reset();
            MP3Player.aktuellausgewaelt = position;
            //Von hier aus geht er auf die neue Seite.
            //Context = Zustand der App.
            Intent intent = new Intent(context, MusikInfo.class);
            //Neue Daten im Intent hinzufügen.
            intent.putExtra("LISTE",songliste);
            //Was wird mit dem Intend gemacht (umgang damit)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    });
    }

    /**
     * @return wie viele Songs auf der Liste/Datenbank vorhanden sind.
     */
    @Override
    public int getItemCount(){
        return songliste.size();
    }

    /**
     * Hiermit werden die IDs der xml-Datei in einem Parameter gesetzt.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titel;
        ImageView logo;
        public ViewHolder(View itemView){
            super(itemView);
            titel = itemView.findViewById(R.id.songname_text);
            logo = itemView.findViewById(R.id.icon);
        }
    }
}