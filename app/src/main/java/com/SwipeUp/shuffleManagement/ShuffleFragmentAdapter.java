package com.SwipeUp.shuffleManagement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ShuffleFragmentAdapter extends FragmentStatePagerAdapter{
    private boolean firstTime;
    private ShuffleActivity shuffleActivity;

    public ShuffleFragmentAdapter(ShuffleActivity shuffleActivity) {
        super(shuffleActivity.getSupportFragmentManager());
        this.shuffleActivity = shuffleActivity;
        firstTime = true;
    }

    @Override
    public Fragment getItem(int position) {
        ShuffleFragment shuffleFragment = ShuffleFragment.newInstance(position);

        // needed since onPageSelected is not called on the firstPage
        if(firstTime) {
            shuffleActivity.setCurrentFragment(shuffleFragment);
            firstTime = false;
        }

        return shuffleFragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
