package com.example.SwipeUp.swipeUp.asyncTasks;

import android.app.usage.UsageEvents;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;

import com.example.SwipeUp.neuralNetwork.Main;
import com.example.SwipeUp.swipeUp.MainActivity;

import java.util.List;

import static java.lang.Thread.sleep;

public class ButtonHider extends AsyncTask<Void, Void, Void> {
    public static final int LONG_PRESS_MILLIS = 500;

    private MainActivity mainActivity;
    private MotionEvent event;

    private boolean slept = false;
    // TODO: improve this
    public synchronized void setSlept(boolean b){
        slept = b;
    }

    public synchronized boolean getSlept(){ return slept; }

    public ButtonHider(MainActivity mainActivity, MotionEvent event){
        super();
        this.mainActivity = mainActivity;
        this.event = event;
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
