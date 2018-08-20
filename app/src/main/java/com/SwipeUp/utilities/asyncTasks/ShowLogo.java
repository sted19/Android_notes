package com.SwipeUp.utilities.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.SwipeUp.shuffleManagement.ShuffleActivity;

public class ShowLogo extends AsyncTask {

    private ShuffleActivity shuffleActivity;

    public ShowLogo(ShuffleActivity shuffleActivity){
        this.shuffleActivity = shuffleActivity;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        shuffleActivity.setSwipeUpImage();
    }
}
