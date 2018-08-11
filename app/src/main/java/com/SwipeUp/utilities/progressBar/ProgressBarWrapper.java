package com.SwipeUp.utilities.progressBar;

import android.animation.ValueAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import com.SwipeUp.shuffleManagement.ShuffleActivity;

public class ProgressBarWrapper {
    private ProgressBar progressBar;
    private int SwitchingDuration= ShuffleActivity.SwitchingDuration;
    private ValueAnimator valueAnimator;
    private SwitchingAnimatorListener animatorListener;
    private ShuffleActivity shuffleActivity;

    public ProgressBarWrapper(ProgressBar progressBar, ShuffleActivity shuffleActivity) {
        this.progressBar = progressBar;
        this.shuffleActivity = shuffleActivity;
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
        valueAnimator.resume();
    }

    //this function should be used only by ProgressAnimatorListener and SwitchingAnimatorListener if needed
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void reinitialize()
    {
        shuffleActivity.RightTap();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void restartAnimation()
    {
        valueAnimator.start();
    }

    private void setUpBar()
    {
        valueAnimator= ValueAnimator.ofInt(0, progressBar.getMax());

        //the animator listener updates the bar animation
        animatorListener = new SwitchingAnimatorListener(progressBar, SwitchingDuration);
        valueAnimator.addUpdateListener(animatorListener);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);

        //the valueAnimator changes its value with time, updating progress bar through animatorListener
        valueAnimator.setDuration(SwitchingDuration);
        valueAnimator.setInterpolator(new LinearInterpolator());

        //the progressAnimatorListener updates the view when the time of the bar is over
        valueAnimator.addListener(new ProgressAnimatorListener(this));

        valueAnimator.start();
    }
}
