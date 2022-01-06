package com.cvetici.beeorganised;

import android.text.NoCopySpan;
import android.widget.EditText;

abstract class TaskWorker {

    protected EditText ime;
    protected int duration;

    public TaskWorker(EditText ime,int duration){
        this.ime = ime;
        this.duration = duration;

    }

}

class ManualTask extends  TaskWorker{

    //Ovo je klasa za Manuleni task
    String pocetnoVreme,kranjeVreme;
    public ManualTask(EditText ime,String pocetnoVreme, int duration) {
        super(ime, duration);
        this.pocetnoVreme = pocetnoVreme;
    }
    public ManualTask(EditText ime,String pocetnoVreme,String krajnjeVreme){
        super(ime,0);
        this.pocetnoVreme = pocetnoVreme;
        this.kranjeVreme = krajnjeVreme;

    }
    public boolean isWorkSet(){
        //ovde ce da bude da li je task uspesno postavljen nakon klika na caluclate dugme;
        return true;
    }


}


class AiTask extends TaskWorker{

    int priority;
    int vreme;
    public AiTask(EditText ime, int duration,int priority,int vreme) {
        super(ime, duration);
        this.priority = priority;
        this.vreme = vreme;
    }

    public boolean isWorkSet(){
        //ovde ce da bude da li je task uspesno postavljen nakon klika na caluclate dugme;
        return true;
    }

    public String getPocetnoVreme(){


        return "";
    }

    public String getKrajnjeVreme(){

        return "";
    }


}