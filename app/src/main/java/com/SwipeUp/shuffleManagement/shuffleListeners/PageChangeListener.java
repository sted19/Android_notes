package com.SwipeUp.shuffleManagement.shuffleListeners;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.SwipeUp.shuffleManagement.ShuffleFragment;
import com.SwipeUp.shuffleManagement.ShuffleActivity;

import java.util.List;

public class PageChangeListener implements OnPageChangeListener {
    private ShuffleActivity shuffleActivity;
    private ShuffleFragment currentFragment;

    public PageChangeListener(ShuffleActivity shuffleActivity) {
        this.shuffleActivity = shuffleActivity;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPageSelected(int position) {
        shuffleActivity.resetButtons();

        currentFragment = (ShuffleFragment) shuffleActivity.adapter.getItem(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE){
            currentFragment.interruptButtonHider();
            currentFragment.progressBarWrapper.resumeBarAnimation();
            shuffleActivity.showButtons();
        }
        //else if (state == ViewPager.SCROLL_STATE_DRAGGING) hideButtons();
    }

    public void setCurrentFragment(ShuffleFragment currentFragment) {
        this.currentFragment = currentFragment;
    }
}
