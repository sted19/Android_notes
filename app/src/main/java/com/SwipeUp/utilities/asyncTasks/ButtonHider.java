package com.SwipeUp.utilities.asyncTasks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import com.SwipeUp.shuffleManagement.ShuffleActivity;

import static java.lang.Thread.sleep;

/**
 * Instances of this class are created to recognize a long press by waiting for a certain amount
 * of time (LONG_PRESS_MILLIS milliseconds)
 */
public class ButtonHider extends AsyncTask<Void, Void, Void> {
    public static final int LONG_PRESS_MILLIS = 300;

    @SuppressLint("StaticFieldLeak")
    private ShuffleActivity shuffleActivity;
    private boolean slept = false;

    /**
     * @param b the value that slept will assume
     */
    public synchronized void setSlept(boolean b){
        slept = b;
    }

    /**
     * @return true if the buttonHider waited LONG_PRESS_MILLIS without interruption, false otherwise
     */
    public synchronized boolean getSlept(){
        return slept;
    }

    public ButtonHider(ShuffleActivity shuffleActivity){
        super();
        this.shuffleActivity = shuffleActivity;
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
        shuffleActivity.hideButtons();
    }
}
