package com.example.stefano.fabio;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

import com.example.stefano.progressBar.ProgressBarWrapper;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private ImageSwitcher switcher;
    private ImageButton like;
    private ImageButton dislike;
    private ImageButton swipeUp;
    private int DisplayWidth = 0;
    private ProgressBarWrapper progressBarWrapper;
    private boolean like_pressed;
    private boolean dislike_pressed;

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
                if(!like_pressed) {
                    like_pressed = true;
                    dislike_pressed=false;
                    like.setImageResource(R.drawable.like_pressed);
                    dislike.setImageResource(R.drawable.dislike);
                }
                else{
                    like_pressed = false;
                    like.setImageResource(R.drawable.like);
                }
                new LikeComputing().doInBackground();
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dislike_pressed) {
                    like_pressed = false;
                    dislike_pressed=true;
                    like.setImageResource(R.drawable.like);
                    dislike.setImageResource(R.drawable.dislike_pressed);
                }
                else{
                    dislike_pressed = false;
                    dislike.setImageResource(R.drawable.dislike);
                }
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

        progressBarWrapper = new ProgressBarWrapper((ProgressBar) findViewById(R.id.progressbar),this);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        (new TapCalculation()).doInBackground(event);
        return true;
    }

    private void LeftTap()
    {
        switcher.setImageResource(R.drawable.pants);
        progressBarWrapper.restartAnimation();
        like.setImageResource(R.drawable.like);
        dislike.setImageResource(R.drawable.dislike);
        like_pressed = false;
        dislike_pressed = false;
    }

    public void RightTap()
    {
        switcher.setImageResource(R.drawable.gigidag);
        progressBarWrapper.restartAnimation();
        like.setImageResource(R.drawable.like);
        dislike.setImageResource(R.drawable.dislike);
        like_pressed = false;
        dislike_pressed = false;
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
