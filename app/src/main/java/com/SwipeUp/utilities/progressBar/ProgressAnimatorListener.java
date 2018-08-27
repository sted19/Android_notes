package com.SwipeUp.utilities.progressBar;

import android.animation.Animator;
import android.os.Build;
import android.support.annotation.RequiresApi;

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onAnimationRepeat(Animator animation) {
        progressBar.reinitialize();
    }
}
