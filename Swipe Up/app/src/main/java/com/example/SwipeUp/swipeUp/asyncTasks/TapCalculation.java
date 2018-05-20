package com.example.SwipeUp.swipeUp.asyncTasks;

import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.example.SwipeUp.swipeUp.MainActivity;

public class TapCalculation extends AsyncTask<MotionEvent, Void, Void>
{
    MainActivity mainActivity;

    public TapCalculation(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public Void doInBackground(MotionEvent... motionEvents) {
        MotionEvent event = motionEvents[0];
        int x = (int)event.getX();

        if(mainActivity.DisplayWidth == 0)
        {
            //calculate displayWidth
            DisplayMetrics metrics = new DisplayMetrics();
            mainActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            mainActivity.DisplayWidth = metrics.widthPixels;
        }

        boolean isLeftTap= x < mainActivity.DisplayWidth /2;

        if(isLeftTap)
            mainActivity.LeftTap();
        else
            mainActivity.RightTap();
        return null;
    }
}
