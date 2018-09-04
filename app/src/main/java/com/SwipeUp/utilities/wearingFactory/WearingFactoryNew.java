package com.SwipeUp.utilities.wearingFactory;


import android.util.Log;

import java.util.HashMap;

/**
 *     Given some specific characteristics, an instance of this class returns the Drawable of a wearing that
 *     has that characteristics
 */
public class WearingFactoryNew {

    //private MiniWearingfactory previous;
    private MiniWearingfactory current;
    //private MiniWearingfactory next;

    private HashMap<Integer,MiniWearingfactory> mappa=new HashMap<>();

    private int current_pos = -1;   //può non essere corretta: quando arrivo all ultimo fragment non viene contato perchè
                                    //non vengono creati nuovi fragment, quindi non viene aggionrnato

    private static WearingFactoryNew instance;

    public static WearingFactoryNew getInstance(){
        if(instance == null){
            instance = new WearingFactoryNew();
        }
        return instance;
    }


    private WearingFactoryNew() {

    }

    public void addMiniWearingFactory(MiniWearingfactory miniWearingfactory, int position){

        Log.e("aggiunto"," "+position);
        mappa.put(position,miniWearingfactory);
        if(position>current_pos){
            mappa.remove(position-5);
            current_pos++;
        }
        else{
            mappa.remove(position+5);
            current_pos--;
        }
    }

    public void resetWearingFactory(){
        //previous = null;
        mappa=new HashMap<>();
        //next = null;
        current_pos = -1;
    }

    public MiniWearingfactory getMiniWearingFactory(int position) {
        Log.e("position","position "+position);
        return mappa.get(position);

    }
}