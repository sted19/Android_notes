package com.example.stefano.fabio;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private ImageSwitcher switcher;
    private int DisplayWidth = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        switcher = (ImageSwitcher) findViewById(R.id.switcher);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        FullScreen fullScreen = new FullScreen(decorView);
        Thread onFull = new Thread(fullScreen);
        onFull.start();

        ViewSwitcher.ViewFactory view = new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                image = new ImageView(getApplicationContext());
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return image;
            }
        };
        switcher.setFactory(view);

        switcher.setImageResource(R.drawable.gigiproietti);

        //Animation animation = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        (new TapCalculation()).doInBackground(event);
        return true;
    }

    private void LeftTap()
    {
        switcher.setImageResource(R.drawable.gigiproietti);
    }

    private void RightTap()
    {
        switcher.setImageResource(R.drawable.gigidag);
    }

    public class TapCalculation extends AsyncTask<MotionEvent, Void, Void>
    {

        @Override
        protected Void doInBackground(MotionEvent... motionEvents) {
            MotionEvent event = motionEvents[0];
            int x = (int)event.getX();

            if(DisplayWidth == 0)
            {
                //calculate displayWidth
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                DisplayWidth = metrics.widthPixels;
            }

            boolean isLeftTap= x < DisplayWidth /2;

            if(isLeftTap)
                LeftTap();
            else
                RightTap();
            return null;
        }
    }


}
