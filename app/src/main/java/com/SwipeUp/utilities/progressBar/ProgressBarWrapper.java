package com.SwipeUp.utilities.progressBar;

import android.animation.ValueAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import com.SwipeUp.shuffleManagement.ShuffleActivity;
import com.SwipeUp.shuffleManagement.ShuffleFragment;

public class ProgressBarWrapper {
    private ProgressBar[] progressBars;
    private int SwitchingDuration = ShuffleActivity.SwitchingDuration;
    private ValueAnimator valueAnimator;
    private SwitchingAnimatorListener animatorListener;
    private ShuffleFragment mShuffleFragment;
    private int actual=0;

    public ProgressBarWrapper(ProgressBar[] progressBars, ShuffleFragment shuffleFragment) {
        this.progressBars = progressBars;
        this.mShuffleFragment = shuffleFragment;
        setUpBar();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void stopBarAnimation()
    {
        valueAnimator.pause();
    }

    //after a stop
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resumeBarAnimation()
    {
        if(valueAnimator.isStarted())
            valueAnimator.resume();
        else
            valueAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void startPrevAnimation() {
        if(actual!=0) {
            valueAnimator.setCurrentFraction(0);
            valueAnimator.pause();
            actual--;
            setUpBar();
            valueAnimator.start();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void startNextAnimation() {
        //progressBars[now].setProgress(progressBars[now].getMax());
        if(actual+1<progressBars.length) {
            valueAnimator.setCurrentFraction(1);
            valueAnimator.pause();
            actual++;
            setUpBar();
            valueAnimator.start();
        }

    }

    //this function should be used only by ProgressAnimatorListener and SwitchingAnimatorListener if needed
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void reinitialize()
    {
        mShuffleFragment.rightTap();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void restartAnimation()
    {
        valueAnimator.start();
    }

    /**
     * Sets up the bar, without starting it
     */
    private void setUpBar()
    {
        valueAnimator= ValueAnimator.ofInt(0, progressBars[actual].getMax());

        //the animator listener updates the bar animation
        animatorListener = new SwitchingAnimatorListener(progressBars[actual], SwitchingDuration);
        valueAnimator.addUpdateListener(animatorListener);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);

        //the valueAnimator changes its value with time, updating progress bar through animatorListener
        valueAnimator.setDuration(SwitchingDuration);
        valueAnimator.setInterpolator(new LinearInterpolator());

        //the progressAnimatorListener updates the view when the time of the bar is over
        valueAnimator.addListener(new ProgressAnimatorListener(this));

    }
}
