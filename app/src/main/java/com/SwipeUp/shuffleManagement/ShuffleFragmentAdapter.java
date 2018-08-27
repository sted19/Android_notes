package com.SwipeUp.shuffleManagement;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

public class ShuffleFragmentAdapter extends FragmentStatePagerAdapter{

    private ShuffleFragment mCurrentFragment;
    private Integer[] indexes = new Integer[5];
    private int lastPosition = -1;

    public ShuffleFragmentAdapter(FragmentManager fm) {
        super(fm);
        for(int i = 0; i<5; i++){
            indexes[i] = 0;
        }
    }


    @Override
    public Fragment getItem(int position) {
        Log.e("log2","getItem");
        if(lastPosition == -1) return ShuffleFragment.newInstance(lastPosition,indexes[position]);
        return ShuffleFragment.newInstance(position,indexes[position]);
    }

    @Override
    public int getCount() {
        return 5;
    }


    /*
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {

        Log.e("log1","setPrimaryItem");

        ShuffleFragment shuffleFragment = (ShuffleFragment) object;
        if(lastPosition != position) {
            if (shuffleFragment != mCurrentFragment) {
                if (mCurrentFragment != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        mCurrentFragment.resetLastBar();
                    }
                    mCurrentFragment.stopBarAnimation();
                }
                if (shuffleFragment != null) {
                    shuffleFragment.startProgressBar();
                }

                mCurrentFragment = shuffleFragment;
            }
            lastPosition = position;
        }
    }
    */
}
