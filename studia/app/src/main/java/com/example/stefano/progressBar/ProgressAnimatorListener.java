package com.example.stefano.progressBar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.widget.ProgressBar;

public class ProgressAnimatorListener implements Animator.AnimatorListener {
    private ProgressBarWrapper progressBar;
    private ValueAnimator valueAnimator;

    public ProgressAnimatorListener(ProgressBarWrapper progressBar, ValueAnimator valueAnimator) {
        this.progressBar = progressBar;
        this.valueAnimator = valueAnimator;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        progressBar.reinitialize();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        progressBar.reinitialize();
        animation.start();
    }
}
