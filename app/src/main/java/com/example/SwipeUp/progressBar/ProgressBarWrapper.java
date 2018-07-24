package com.example.SwipeUp.progressBar;

import android.animation.ValueAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import com.example.SwipeUp.swipeUp.MainActivity;

public class ProgressBarWrapper {
    private ProgressBar progressBar;
    private int SwitchingDuration= MainActivity.SwitchingDuration;
    private ValueAnimator valueAnimator;
    private SwitchingAnimatorListener animatorListener;
    private MainActivity mainActivity;

    public ProgressBarWrapper(ProgressBar progressBar, MainActivity mainActivity) {
        this.progressBar = progressBar;
        this.mainActivity= mainActivity;
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
        mainActivity.RightTap();
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
