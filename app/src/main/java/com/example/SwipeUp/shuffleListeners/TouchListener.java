package com.example.SwipeUp.shuffleListeners;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.SwipeUp.swipeUp.MainActivity;
import com.example.SwipeUp.swipeUp.asyncTasks.ButtonHider;
import com.example.SwipeUp.swipeUp.asyncTasks.TapCalculation;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class TouchListener implements OnTouchListener {

    private MainActivity mainActivity;

    public TouchListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();

        switch(action){
            case ACTION_DOWN:
                mainActivity.progressBarWrapper.stopBarAnimation();
                Log.d("stop", "stop");
                mainActivity.buttonHider = new ButtonHider(mainActivity);
                mainActivity.buttonHider.execute();
                break;

            case ACTION_UP:
                mainActivity.buttonHider.cancel(true);   //stops previously started buttonHider
                if (!mainActivity.buttonHider.getSlept()){ //Simple tap
                    (new TapCalculation(mainActivity)).doInBackground(event);
                }
                else //long press
                    mainActivity.progressBarWrapper.resumeBarAnimation();
                if(mainActivity.buttonHider.getSlept())
                    mainActivity.showButtons();

                break;
        }

        return false;
    }
}
