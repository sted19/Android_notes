package com.example.SwipeUp.swipeUp.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.SwipeUp.swipeUp.MainActivity;


import static java.lang.Thread.sleep;

public class TapRecognizer extends AsyncTask<Void, Void, Void> {
    public static final int LONG_PRESS_MILLIS = 75;

    private boolean slept = false;
    // TODO: improve synchronized here
    public synchronized void setSlept(boolean b){
        slept = b;
    }

    public synchronized boolean getSlept(){
        return slept;
    }

    public TapRecognizer(){
        super();
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
    }
}