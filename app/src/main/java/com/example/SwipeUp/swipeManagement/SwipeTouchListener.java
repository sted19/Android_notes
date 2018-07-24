package com.example.SwipeUp.swipeManagement;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.SwipeUp.swipeUp.MainActivity;
import com.example.SwipeUp.swipeUp.R;
import com.example.SwipeUp.swipeUp.asyncTasks.ButtonHider;
import com.example.SwipeUp.swipeUp.asyncTasks.TapCalculation;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

public class SwipeTouchListener implements View.OnTouchListener {
    private final static int SWIPE_DISTANCE = 1;

    private MainActivity mainActivity;
    private float x1, x2;

    public SwipeTouchListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();

        switch(action) {
            case ACTION_DOWN:
                x1 = event.getX();
                mainActivity.progressBarWrapper.stopBarAnimation();
                Log.d("stop", "stop");
                mainActivity.buttonHider = new ButtonHider(mainActivity);
                mainActivity.buttonHider.execute();
                break;

            case ACTION_MOVE:
                Log.d("Move", "I'm moving to " + event.getX() + " " + event.getY());
                //TODO: animate here transition between images
                break;

            case ACTION_UP:
                x2 = event.getX();
                mainActivity.buttonHider.cancel(true);   //stops previously started buttonHider
                if (x1 - x2 > SWIPE_DISTANCE) { //right swipe
                    mainActivity.resetButtons();
                    mainActivity.progressBarWrapper.restartAnimation();
                }
                else if (x2 - x1 > SWIPE_DISTANCE) { //left  swipe
                    mainActivity.resetButtons();
                    mainActivity.progressBarWrapper.restartAnimation();
                }
                else if (!mainActivity.buttonHider.getSlept()){ //Simple tap
                    (new TapCalculation(mainActivity)).doInBackground(event);
                }
                else //long press
                    mainActivity.progressBarWrapper.resumeBarAnimation();
                if(mainActivity.buttonHider.getSlept())
                    mainActivity.showButtons();

                break;
        }
        return true;
    }
}
