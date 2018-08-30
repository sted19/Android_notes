package com.SwipeUp.utilities.wearingFactory;


/**
 *     Given some specific characteristics, an instance of this class returns the Drawable of a wearing that
 *     has that characteristics
 */
public class WearingFactoryNew {

    private MiniWearingfactory previous;
    private MiniWearingfactory current;
    private MiniWearingfactory next;

    private int current_pos = -1;

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
        if(current_pos == -1){
            current_pos = position;
            current = miniWearingfactory;
        }
        else if(position > current_pos){
            previous = current;
            current = next;
            next = miniWearingfactory;
            current_pos++;
        }
        else if(position < current_pos){
            next = current;
            current = previous;
            previous = miniWearingfactory;
            current_pos--;
        }
    }

    public void resetWearingFactory(){
        previous = null;
        current = null;
        next = null;
        current_pos = -1;
    }


}