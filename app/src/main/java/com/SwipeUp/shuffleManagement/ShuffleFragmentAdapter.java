package com.SwipeUp.shuffleManagement;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

public class ShuffleFragmentAdapter extends FragmentStatePagerAdapter{

    private ShuffleFragment lastCreated;
    private ShuffleFragment mCurrentFragment;
    private Integer[] indexes = new Integer[3];
    private int lastPosition = -1;
    private boolean canGo=true;

    public ShuffleFragmentAdapter(FragmentManager fm) {
        super(fm);
        for(int i = 0; i<3; i++){
            indexes[i] = 0;
        }
    }

    @Override
    public Fragment getItem(int position) {
        Log.e("getItem", "getItem");

        if (lastPosition == -1) {
            lastPosition = 0;
            lastCreated = ShuffleFragment.newInstance(-1, indexes[position]);
        } else {
            lastCreated = ShuffleFragment.newInstance(position, indexes[position]);
        }
        return lastCreated;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

        ShuffleFragment shuffleFragment = (ShuffleFragment) object;
        indexes[position] = shuffleFragment.getIndex();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {

        ShuffleFragment shuffleFragment = (ShuffleFragment) object;

        if (shuffleFragment != mCurrentFragment) {
            if (mCurrentFragment != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                mCurrentFragment.resetLastBar(true);
            }
            if (shuffleFragment != null) {
                shuffleFragment.startProgressBar();
            }
            mCurrentFragment = shuffleFragment;
        }
        lastPosition = position;

        if(shuffleFragment != null) {
            shuffleFragment.resumeProgressBar();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resumeFragment(){
        //da chiamare per far ripartire il fragment, quando l activity fa onResume()

        if(mCurrentFragment!=null)
            mCurrentFragment.resumeProgressBar();

    }

    public void upgradeView(){
        lastCreated.upgradeView();
    }

}
