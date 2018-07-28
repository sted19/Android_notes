package com.SwipeUp.swipeUp.asyncTasks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.SwipeUp.swipeUp.MainActivity;


import static java.lang.Thread.sleep;

public class ButtonHider extends AsyncTask<Void, Void, Void> {
    public static final int LONG_PRESS_MILLIS = 300;

    @SuppressLint("StaticFieldLeak")
    private MainActivity mainActivity;

    private boolean slept = false;
    // TODO: improve synchronized here
    public synchronized void setSlept(boolean b){
        slept = b;
    }

    public synchronized boolean getSlept(){
        return slept;
    }

    public ButtonHider(MainActivity mainActivity){
        super();
        this.mainActivity = mainActivity;
    }

    protected Void doInBackground(Void... voids) {
        try{
            sleep(LONG_PRESS_MILLIS);
        }
        catch(InterruptedException e){
            Log.d("Interrupted", "Someone interrupted me");
            return null;
        }

        setSlept(true);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mainActivity.hideButtons();
    }
}
