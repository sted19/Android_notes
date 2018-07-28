package com.SwipeUp.progressBar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.widget.ProgressBar;

public class ProgressAnimatorListener implements Animator.AnimatorListener {
    private ProgressBarWrapper progressBar;

    public ProgressAnimatorListener(ProgressBarWrapper progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        progressBar.reinitialize();
    }
}
