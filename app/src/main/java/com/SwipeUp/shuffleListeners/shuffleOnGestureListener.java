package com.SwipeUp.shuffleListeners;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.SwipeUp.swipeUp.MainActivity;
import com.SwipeUp.swipeUp.asyncTasks.ButtonHider;
import com.SwipeUp.swipeUp.asyncTasks.TapCalculation;

public class shuffleOnGestureListener implements GestureDetector.OnGestureListener {
    private MainActivity mainActivity;
    private ButtonHider buttonHider;

    public shuffleOnGestureListener(MainActivity mainActivity, ButtonHider buttonHider) {
        this.mainActivity = mainActivity;
        this.buttonHider = buttonHider;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onDown(MotionEvent e) {
        mainActivity.progressBarWrapper.stopBarAnimation();
        buttonHider = new ButtonHider(mainActivity);
        buttonHider.execute();
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onShowPress(MotionEvent e) {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        (new TapCalculation(mainActivity)).doInBackground(e);
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

        if(mainActivity.getRunning()  && dy>0 && (dy>dx)){
            mainActivity.startSwipeUpActivity();
        }
        return false;
    }
}
