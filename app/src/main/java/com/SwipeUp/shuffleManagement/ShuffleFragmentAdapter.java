package com.SwipeUp.shuffleManagement;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ShuffleFragmentAdapter extends FragmentStatePagerAdapter{
    private boolean firstTime;
    private ShuffleActivity shuffleActivity;
    private FragmentManager mFragmentManager;

    public ShuffleFragmentAdapter(ShuffleActivity shuffleActivity) {
        super(shuffleActivity.getSupportFragmentManager());
        mFragmentManager = shuffleActivity.getSupportFragmentManager();
        this.shuffleActivity = shuffleActivity;
        firstTime = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Fragment getItem(int position) {
        return ShuffleFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
