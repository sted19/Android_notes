package com.SwipeUp.utilities.progressBar;

import android.animation.ValueAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import com.SwipeUp.shuffleManagement.ShuffleActivity;
import com.SwipeUp.shuffleManagement.ShuffleFragment;
import com.SwipeUp.utilities.Constants;
import com.SwipeUp.utilities.R;

public class ProgressBarWrapper {

    private ProgressBar[] progressBars;
    private int SwitchingDuration = Constants.SWIPE_DURATION;
    private ValueAnimator valueAnimator;
    private SwitchingAnimatorListener animatorListener;
    private ShuffleFragment mShuffleFragment;
    private int actual=0;

    public ProgressBarWrapper(ProgressBar[] progressBars, ShuffleFragment shuffleFragment,int index) {
        this.progressBars = progressBars;
        this.mShuffleFragment = shuffleFragment;
        setUpBar();

        for(int i=0;i<index;i++){
            startNextAnimation();
        }
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    /*
        set the progress of the last bar started at 0
     */
    public void resetLastBarAnimation(){
        valueAnimator.setCurrentFraction(0);
    }

    //this function should be used only by ProgressAnimatorListener and SwitchingAnimatorListener if needed
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void reinitialize()
    {
        if(mShuffleFragment.getActivity()!=null && !mShuffleFragment.getActivity().isDestroyed())
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

    public void destroyBars(){
        progressBars = null;
        valueAnimator.removeAllListeners();
        animatorListener = null;
    }

     public void hideBars(){
         Animation disappearance= AnimationUtils.loadAnimation(mShuffleFragment.getContext(), R.anim.disappearance);
        for(int i=0;i<progressBars.length;i++){
            progressBars[i].setVisibility(View.INVISIBLE);
            progressBars[i].startAnimation(disappearance);
        }
     }

     public void showBars(){
         Animation appearance=AnimationUtils.loadAnimation(mShuffleFragment.getContext(), R.anim.appearance);
         for(int i=0;i<progressBars.length;i++){
             progressBars[i].setVisibility(View.VISIBLE);
             progressBars[i].startAnimation(appearance);
         }

     }
}
