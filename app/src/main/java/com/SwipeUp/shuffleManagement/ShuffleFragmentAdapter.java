package com.SwipeUp.shuffleManagement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ShuffleFragmentAdapter extends FragmentStatePagerAdapter{

    public ShuffleFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ShuffleFragment shuffleFragment = ShuffleFragment.newInstance(position);

        return shuffleFragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
