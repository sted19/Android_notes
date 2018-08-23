package com.SwipeUp.shuffleManagement;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

public class ShuffleFragmentAdapter extends FragmentStatePagerAdapter{
    private FragmentManager mFragmentManager;

    public ShuffleFragmentAdapter(ShuffleActivity shuffleActivity) {
        super(shuffleActivity.getSupportFragmentManager());
        mFragmentManager = shuffleActivity.getSupportFragmentManager();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Fragment getItem(int position) {

        Log.w("getItem frag:"," "+position);
        /*
         *  If the fragment in a certain position already exists, if it returned, without creating a
         *  new one
         */
        List<Fragment> fragments = mFragmentManager.getFragments();

        for(Fragment fragment : fragments){
            if(fragment.getClass() == ShuffleFragment.class && ((ShuffleFragment)fragment)
                    .getPosition() == position)
                return fragment;
        }

        // the fragment does not exist, a new one will be created
        return ShuffleFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
