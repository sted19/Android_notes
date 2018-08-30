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
        Log.e("getItem","getItem");
        if(lastPosition == -1) {
            lastPosition = 0;
            return ShuffleFragment.newInstance(-1,indexes[position]);
        }
        return ShuffleFragment.newInstance(position,indexes[position]);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {

        Log.e("SET_PRIMARY_ITEM","OUTSIDE");

        ShuffleFragment shuffleFragment = (ShuffleFragment) object;


            if (shuffleFragment != mCurrentFragment) {
                Log.e("SET_PRIMARY_ITEM", "INSIDE");
                if (mCurrentFragment != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        mCurrentFragment.startProgressBar();
                        mCurrentFragment.stopBarAnimation();
                    }
                }
                if (shuffleFragment != null) {
                    shuffleFragment.startProgressBar();
                }
                mCurrentFragment = shuffleFragment;
            }
            lastPosition = position;

        if(shuffleFragment != null)
            shuffleFragment.resumeProgressBar();


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resumeFragment(){//da chiamare per far ripartire il fragment, quando l activity fa onResume()


        if(mCurrentFragment!=null)
            mCurrentFragment.resumeProgressBar();

    }

}
