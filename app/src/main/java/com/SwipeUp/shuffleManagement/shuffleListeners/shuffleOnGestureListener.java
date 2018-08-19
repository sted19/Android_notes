package com.SwipeUp.shuffleManagement.shuffleListeners;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.SwipeUp.shuffleManagement.ShuffleActivity;
import com.SwipeUp.shuffleManagement.ShuffleFragment;
import com.SwipeUp.utilities.asyncTasks.ButtonHider;

public class shuffleOnGestureListener implements GestureDetector.OnGestureListener {
    private ShuffleFragment mShuffleFragment;
    private ShuffleActivity shuffleActivity;

    public shuffleOnGestureListener(ShuffleFragment mShuffleFragment, ShuffleActivity shuffleActivity) {
        this.mShuffleFragment = mShuffleFragment;
        this.shuffleActivity = shuffleActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onDown(MotionEvent e) {
        mShuffleFragment.progressBarWrapper.stopBarAnimation();
        mShuffleFragment.setButtonHider(new ButtonHider());
        mShuffleFragment.executeButtonHider();
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
//        (new TapCalculation(shuffleActivity)).doInBackground(e);
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void tapCalculation(MotionEvent event){
        int x = (int)event.getX();

        //TODO: consider putting metrics in a static private variable
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

        if(shuffleActivity.getRunning() && dy>0 && (dy>dx)){
            mShuffleFragment.startSwipeUpActivity();
        }
        return false;
    }
}
