package com.example.SwipeUp.swipeUp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

import com.example.SwipeUp.swipeUp.asyncTasks.ButtonHider;
import com.example.SwipeUp.swipeUp.asyncTasks.DislikeComputing;
import com.example.SwipeUp.swipeUp.asyncTasks.LikeComputing;
import com.example.SwipeUp.swipeUp.asyncTasks.SwipeUpComputing;
import com.example.SwipeUp.swipeUp.asyncTasks.TapCalculation;
import com.example.SwipeUp.progressBar.ProgressBarWrapper;
import com.example.SwipeUp.wearingFactory.WearingFactory;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private ImageSwitcher switcher;
    private ImageButton like;
    private ImageButton dislike;
    private ImageButton swipeUp;

    public int DisplayWidth = 0;
    private ProgressBarWrapper progressBarWrapper;
    private boolean like_pressed;
    private boolean dislike_pressed;
    private WearingFactory wearingFactory;
    public static final int SwitchingDuration = 6000;
    public ButtonHider buttonHider;

    private final static int SWIPE_DISTANCE = 1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        progressBarWrapper.resumeBarAnimation();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    Animation pulse;
    ImageView heart;
    @SuppressLint("WrongViewCast")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        switcher = (ImageSwitcher) findViewById(R.id.switcher);
        like = (ImageButton) findViewById(R.id.like);
        dislike = (ImageButton) findViewById(R.id.dislike);
        swipeUp = (ImageButton) findViewById(R.id.swipeUp);

        heart = findViewById(R.id.heart);
        heart.setVisibility(View.INVISIBLE);
        pulse = AnimationUtils.loadAnimation(this, R.anim.zoom_and_disappearence);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!like_pressed) {
                    like_pressed = true;
                    dislike_pressed=false;
                    like.setImageResource(R.drawable.like_pressed);
                    dislike.setImageResource(R.drawable.dislike);

                    heart.startAnimation(pulse);



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

        progressBarWrapper = new ProgressBarWrapper((ProgressBar) findViewById(R.id.progressbar),this);

        wearingFactory = new WearingFactory(this);
        switcher.setImageDrawable(wearingFactory.getNextImage());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void go_back(View view)
    {
        progressBarWrapper.stopBarAnimation();//per ricominciare?
        Intent intent = new Intent(this,ChoiceActivity.class);
        //putExtra method to send information to the new Activity
        startActivity(intent);

    }

    float x1=0;
    float x2=0;
    long t1=0;//millisecondi
    long t2=0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();

        switch(action) {
            case ACTION_DOWN:
                x1 = event.getX();
                t1 = event.getEventTime();
                progressBarWrapper.stopBarAnimation();
                Log.d("stop", "stop");
                buttonHider = new ButtonHider(this, event);
                buttonHider.execute();
                break;

            case ACTION_MOVE:
                Log.d("Move", "I'm moving to " + event.getX() + " " + event.getY());
                buttonHider.cancel(true);
                break;

            case ACTION_UP:
                x2 = event.getX();
                t2 = event.getEventTime();
                buttonHider.cancel(true);
                if (!buttonHider.getSlept()){
                    if (x1 - x2 > SWIPE_DISTANCE) {
                        Log.d("swipe a destra", "swipe a destra");
                        switcher.setImageResource(R.drawable.shirt);
                        progressBarWrapper.restartAnimation();
                        this.resetButtons();
                    } else if (x2 - x1 > SWIPE_DISTANCE) {
                        Log.d("swipe a sinistra", "swipe a sinistra");
                        switcher.setImageResource(R.drawable.gigiproietti);
                        progressBarWrapper.restartAnimation();
                        resetButtons();
                    }
                    else {
                        Log.d("tocco semplice", "tocco semplice");
                        (new TapCalculation(this)).doInBackground(event);
                    }

                    //(new TapCalculation()).doInBackground(event);
                    Log.d("riparte", "riparte");
                    x1 = 0;
                    x2 = 0;

                }
                else if (buttonHider.getSlept()) { // long press
                    Log.d("riparte", "riparte");
                    x1 = 0;
                    x2 = 0;
                    progressBarWrapper.resumeBarAnimation();
                    this.showButtons();
                }
                break;
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void LeftTap()
    {
        switcher.setImageDrawable(wearingFactory.getPreviousImage());
        progressBarWrapper.restartAnimation();
        resetButtons();
        heart.clearAnimation();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void RightTap()
    {
        switcher.setImageDrawable(wearingFactory.getNextImage());
        progressBarWrapper.restartAnimation();
        resetButtons();
        heart.clearAnimation();
    }

    private void resetButtons()
    {
        like.setImageResource(R.drawable.like);
        dislike.setImageResource(R.drawable.dislike);
        like_pressed = false;
        dislike_pressed = false;
    }

    public void hideButtons(){
        this.dislike.setVisibility(View.INVISIBLE);
        this.like.setVisibility(View.INVISIBLE);
        this.swipeUp.setVisibility(View.INVISIBLE);
    }

    public void showButtons(){
        this.dislike.setVisibility(View.VISIBLE);
        this.like.setVisibility(View.VISIBLE);
        this.swipeUp.setVisibility(View.VISIBLE);
    }
}
