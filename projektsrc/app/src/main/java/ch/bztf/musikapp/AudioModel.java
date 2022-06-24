package ch.bztf.musikapp;

import java.io.*;

public class AudioModel implements Serializable{
    String pfad;
    String name;
    String dauer;

    /**
     * Bei dieser Methode kann man von "aussen" alle Perameter setzten.
     * @param pfad
     * @param name
     * @param dauer
     */
    public AudioModel(String pfad, String name, String dauer){
        this.pfad = pfad;
        this.name = name;
        this.dauer = dauer;
    }

    /**
     * Damit kann man sich dem Pfad holen.
     * @return pfad
     */
    public String getPfad(){
        return pfad;
    }

    /**
     * Damit wird der Pfad wo der Song liegt gesetzt.
     * @param pfad
     */
    public void setPfad(String pfad){
        this.pfad = pfad;
    }

    /**
     * Damit kann man sich die Information des Namens holen.
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Mit dieser Methode wird der Name gesetzt.
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Damit kann man sich die Dauer holen.
     * @return dauer
     */
    public String getDauer(){
        return dauer;
    }

    /**
     * Damit wird die Dauer gesetzt.
     * @param dauer
     */
    public void setDauer(String dauer){
        this.dauer = dauer;
    }

}
