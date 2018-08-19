package com.SwipeUp.utilities.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.SwipeUp.shuffleManagement.ShuffleActivity;
import com.SwipeUp.shuffleManagement.ShuffleFragment;


/**
 *  AsyncTask started whenever a tap has been recognized
 */
public class TapCalculation extends AsyncTask<MotionEvent, Void, Void>
{
    private ShuffleActivity shuffleActivity;
    private ShuffleFragment mShuffleFragment;

    public TapCalculation(ShuffleActivity shuffleActivity) {
        this.shuffleActivity = shuffleActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Void doInBackground(MotionEvent... motionEvents) {
        MotionEvent event = motionEvents[0];
        int x = (int)event.getX();

        if(shuffleActivity.DisplayWidth == 0)
        {
            //calculate displayWidth
            DisplayMetrics metrics = new DisplayMetrics();
            shuffleActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            shuffleActivity.DisplayWidth = metrics.widthPixels;
        }

        boolean isLeftTap= x < shuffleActivity.DisplayWidth /2;

        if(isLeftTap)
            mShuffleFragment.leftTap();
        else
            mShuffleFragment.rightTap();
        return null;
    }
}
