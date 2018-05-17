package com.example.stefano.fabio;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

import com.example.stefano.progressBar.ProgressAnimatorListener;
import com.example.stefano.progressBar.ProgressBarWrapper;
import com.example.stefano.progressBar.SwitchingAnimatorListener;
import com.example.stefano.progressBar.ProgressBarWrapper;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private ImageSwitcher switcher;
    private ImageButton like;
    private ImageButton dislike;
    private ImageButton swipeUp;
    private int DisplayWidth = 0;
    private ProgressBarWrapper progressBarWrapper;

    public static final int SwitchingDuration = 3000;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        switcher = (ImageSwitcher) findViewById(R.id.switcher);
        like = (ImageButton) findViewById(R.id.like);
        dislike = (ImageButton) findViewById(R.id.dislike);
        swipeUp = (ImageButton) findViewById(R.id.swipeUp);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like.setImageResource(R.drawable.like_pressed);
                new LikeComputing().doInBackground();
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislike.setImageResource(R.drawable.dislike_pressed);
                new DislikeComputing().doInBackground();
            }
        });

        swipeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SwipeUpComputing().doInBackground();
            }
        });

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

        switcher.setImageResource(R.drawable.pants);

        progressBarWrapper = new ProgressBarWrapper((ProgressBar) findViewById(R.id.progressbar), switcher);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        (new TapCalculation()).doInBackground(event);
        return true;
    }

    private void LeftTap()
    {
        switcher.setImageResource(R.drawable.pants);
    }

    private void RightTap()
    {
        switcher.setImageResource(R.drawable.shirt);
    }

    public class LikeComputing extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }


    public class DislikeComputing extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    public class SwipeUpComputing extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
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
