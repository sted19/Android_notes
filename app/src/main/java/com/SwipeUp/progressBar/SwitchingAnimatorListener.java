package com.SwipeUp.progressBar;

import android.animation.ValueAnimator;
import android.util.Log;
import android.widget.ProgressBar;

public class SwitchingAnimatorListener implements ValueAnimator.AnimatorUpdateListener {
    private ProgressBar progressBar;
    private int maxValue;
    private double duration;

    public SwitchingAnimatorListener(ProgressBar progressBar, int duration) {
        this.progressBar = progressBar;
        maxValue= progressBar.getMax();
        this.duration=(double)duration;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int newValue = (int) ((double)animation.getCurrentPlayTime()/duration * maxValue);
        progressBar.setProgress(newValue);
    }
}
