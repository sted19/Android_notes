package com.example.stefano.progressBar;

import android.animation.ValueAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.animation.LinearInterpolator;
import android.widget.ImageSwitcher;
import android.widget.ProgressBar;

import com.example.stefano.fabio.MainActivity;
import com.example.stefano.fabio.R;

public class ProgressBarWrapper {
    private ProgressBar progressBar;
    private int SwitchingDuration= MainActivity.SwitchingDuration;
    private ValueAnimator valueAnimator;
    private SwitchingAnimatorListener animatorListener;
    private  ImageSwitcher imageSwitcher;

    public ProgressBarWrapper(ProgressBar progressBar, ImageSwitcher imageSwitcher) {
        this.progressBar = progressBar;
        this.imageSwitcher=imageSwitcher;

        setUpBar();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void stopBar()
    {
        valueAnimator.pause();
    }

    //after a stop
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void restartBar()
    {
        valueAnimator.resume();
    }

    public void reinitialize()
    {
        imageSwitcher.setImageResource(R.drawable.gigidag);
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
        valueAnimator.addListener(new ProgressAnimatorListener(this, valueAnimator));

        valueAnimator.start();
    }
}
