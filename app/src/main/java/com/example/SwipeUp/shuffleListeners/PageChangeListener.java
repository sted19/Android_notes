package com.example.SwipeUp.shuffleListeners;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.SwipeUp.progressBar.ProgressBarWrapper;
import com.example.SwipeUp.swipeManagement.CustomAdapter;

public class PageChangeListener implements OnPageChangeListener {
    private int lastPosition = 0;
    private boolean lastSwipeWasRigth = true;
    private CustomAdapter adapter;
    private ProgressBarWrapper progressBarWrapper;

    public PageChangeListener(CustomAdapter adapter, ProgressBarWrapper progressBarWrapper) {
        this.adapter = adapter;
        this.progressBarWrapper = progressBarWrapper;
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPageSelected(int position) {
        progressBarWrapper.restartAnimation();

        int positionsDifference = position - lastPosition;
        boolean isRightSwipe = positionsDifference > 0;
        boolean isLastPosition = (position == adapter.getCount()-1);

        if(positionsDifference == 0) return;
        else if(position == 0 || position == adapter.getCount()-1){ //first element
            if ((!lastSwipeWasRigth && position == 0) || (lastSwipeWasRigth && isLastPosition)){
                adapter.previousImageView = adapter.currentImageView;
                adapter.currentImageView = adapter.nextImageView;
            }
            else{
                ImageView tmp = adapter.previousImageView;
                adapter.previousImageView = adapter.currentImageView;
                adapter.currentImageView = tmp;
            }
        }
        else if(isRightSwipe == lastSwipeWasRigth){
            adapter.previousImageView = adapter.currentImageView;
        }
        else{
            adapter.nextImageView = adapter.previousImageView;
            adapter.previousImageView = adapter.currentImageView;
        }

        lastSwipeWasRigth = isRightSwipe;
        lastPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //if (state == ViewPager.SCROLL_STATE_IDLE) showButtons();
        //else if (state == ViewPager.SCROLL_STATE_DRAGGING) hideButtons();
    }



}
