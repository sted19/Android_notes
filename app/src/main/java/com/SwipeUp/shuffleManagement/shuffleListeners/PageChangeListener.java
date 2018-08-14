package com.SwipeUp.shuffleManagement.shuffleListeners;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

import com.SwipeUp.shuffleManagement.ShuffleFragment;
import com.SwipeUp.shuffleManagement.ShuffleFragmentAdapter;
import com.SwipeUp.utilities.progressBar.ProgressBarWrapper;
import com.SwipeUp.shuffleManagement.CustomAdapter;
import com.SwipeUp.shuffleManagement.ShuffleActivity;

import java.util.List;

public class PageChangeListener implements OnPageChangeListener {
    private ShuffleFragmentAdapter adapter;
    private ProgressBarWrapper progressBarWrapper;
    private ShuffleActivity shuffleActivity;
    private FragmentManager fragmentManager;

    public PageChangeListener(ShuffleActivity shuffleActivity, ShuffleFragmentAdapter adapter, ProgressBarWrapper progressBarWrapper) {
        this.adapter = adapter;
        this.progressBarWrapper = progressBarWrapper;
        this.shuffleActivity = shuffleActivity;
        fragmentManager = shuffleActivity.getSupportFragmentManager();
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPageSelected(int position) {
        progressBarWrapper.restartAnimation();

        shuffleActivity.resetButtons();

        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment: fragments){
            if(fragment.getClass() == ShuffleFragment.class && ((ShuffleFragment)fragment).
                    getPosition() == position)
                shuffleActivity.setCurrentFragment((ShuffleFragment) fragment);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //if (state == ViewPager.SCROLL_STATE_IDLE) showButtons();
        //else if (state == ViewPager.SCROLL_STATE_DRAGGING) hideButtons();
    }



}
