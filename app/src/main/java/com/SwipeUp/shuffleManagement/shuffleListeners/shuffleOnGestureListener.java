package com.SwipeUp.shuffleManagement.shuffleListeners;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.SwipeUp.shuffleManagement.ShuffleActivity;
import com.SwipeUp.utilities.asyncTasks.ButtonHider;
import com.SwipeUp.utilities.asyncTasks.TapCalculation;

public class shuffleOnGestureListener implements GestureDetector.OnGestureListener {
    private ShuffleActivity shuffleActivity;
    private ButtonHider buttonHider;

    public shuffleOnGestureListener(ShuffleActivity shuffleActivity) {
        this.shuffleActivity = shuffleActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onDown(MotionEvent e) {
        shuffleActivity.progressBarWrapper.stopBarAnimation();
        shuffleActivity.setButtonHider(new ButtonHider(shuffleActivity));
        shuffleActivity.executeButtonHider();
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onShowPress(MotionEvent e) {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        (new TapCalculation(shuffleActivity)).doInBackground(e);
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float dy=e1.getY()-e2.getY();
        float dx=e1.getX()-e2.getX();
        if(dx<0)
            dx=-dx;

        Log.e("fling","fling");
        Log.e("dy",""+dy);
        Log.e("dx",""+dx);
        Log.e("tempo",""+e2.getDownTime());

        if(shuffleActivity.getRunning()  && dy>0 && (dy>dx)){
            shuffleActivity.startSwipeUpActivity();
        }
        return false;
    }
}
