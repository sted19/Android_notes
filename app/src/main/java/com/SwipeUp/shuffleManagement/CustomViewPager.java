package com.SwipeUp.shuffleManagement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager{

    private boolean disable = false;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !disable && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !disable && super.onTouchEvent(ev);
    }

    /**
     *
     * @param disable false --> no swiping in viewPager
     */


    public void disableScroll(boolean disable){
        this.disable = disable;
    }
}
