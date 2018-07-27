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
import com.example.SwipeUp.swipeUp.asyncTasks.TapRecognizer;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class TouchListener implements OnTouchListener {

    private MainActivity mainActivity;

    private TapRecognizer tapRecognizer;
    private ButtonHider buttonHider;

    public TouchListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();

        switch(action){
            case ACTION_DOWN:
                Log.d("ACTION_DOWN", "stop");
                mainActivity.progressBarWrapper.stopBarAnimation();
                tapRecognizer = new TapRecognizer();
                tapRecognizer.execute();
                buttonHider = new ButtonHider(mainActivity);
                buttonHider.execute();
                break;

            case ACTION_UP:
                buttonHider.cancel(true);       //stops previously started buttonHider
                tapRecognizer.cancel(true);     //stops previously started tapRecognizer
                if (!tapRecognizer.getSlept()){
                    Log.d("SIMPLE TAP","Tocco Semplice");
                    (new TapCalculation(mainActivity)).doInBackground(event);
                }
                else{
                    mainActivity.progressBarWrapper.resumeBarAnimation();
                }
                if(buttonHider.getSlept())
                    mainActivity.showButtons();

                break;
        }

        return false;
    }
}
