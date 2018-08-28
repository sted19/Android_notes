package com.SwipeUp.shuffleManagement.shuffleListeners;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.SwipeUp.shuffleManagement.ShuffleFragment;

public class ShuffleOnGestureListener implements GestureDetector.OnGestureListener {

    private ShuffleFragment mShuffleFragment;
    private int displayWidth;

    public ShuffleOnGestureListener(ShuffleFragment mShuffleFragment, int displayWidth) {
        this.mShuffleFragment = mShuffleFragment;
        this.displayWidth = displayWidth;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onDown(MotionEvent e) {
        mShuffleFragment.stopBarAnimation();
        return false;
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onShowPress(MotionEvent e) {
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        tapCalculation(e);
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        mShuffleFragment.hideButtons();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float dy=e1.getY()-e2.getY();
        float dx=e1.getX()-e2.getX();
        if(dx<0)
            dx=-dx;

        if(dy>0 && (dy>dx)){
            mShuffleFragment.startSwipeUpActivity();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void tapCalculation(MotionEvent event){
        int x = (int)event.getX();

        boolean isLeftTap= x <  displayWidth /2;

        if(isLeftTap)
            mShuffleFragment.leftTap();
        else
            mShuffleFragment.rightTap();
    }
}