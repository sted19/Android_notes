package com.SwipeUp.shuffleManagement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager{

    private boolean disable = false;
    private boolean doubleTapBug=false;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {//se returna true-> chiama onTouchEvent

        return !doubleTapBug && !disable && super.onInterceptTouchEvent(ev);

    }

    @Override
    public  boolean onTouchEvent(MotionEvent ev) {
        return !doubleTapBug && !disable && super.onTouchEvent(ev);

    }

    /**
     *
     * @param disable false --> no swiping in viewPager
     */
    public void disableScroll(boolean disable){
        Log.e("disabled","disabled");this.disable = disable;
    }

    /**
     *
     * @param doubleTapBug false --> inibito il secondo tap
     */
    public  void setDoubleTapBug(boolean doubleTapBug) {
        this.doubleTapBug = doubleTapBug;
    }



}
