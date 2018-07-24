package com.example.SwipeUp.swipeUp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.SwipeUp.buttonsListener.ButtonsListener;
import com.example.SwipeUp.swipeManagement.SwipeTouchListener;
import com.example.SwipeUp.swipeUp.asyncTasks.ButtonHider;
import com.example.SwipeUp.progressBar.ProgressBarWrapper;
import com.example.SwipeUp.swipeUp.asyncTasks.TapCalculation;
import com.example.SwipeUp.wearingFactory.WearingFactory;

import java.util.logging.Logger;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;
import static java.util.logging.Logger.getLogger;

public class MainActivity extends AppCompatActivity {

    public ImageButton like;
    public ImageButton dislike;
    private ImageButton swipeUp;

    public int DisplayWidth = 0;
    public ProgressBarWrapper progressBarWrapper;
    public boolean like_pressed;
    public boolean dislike_pressed;
    private WearingFactory wearingFactory;
    public static final int SwitchingDuration = 6000;
    public ButtonHider buttonHider;

    private ButtonsListener.LikeListener likeListener;

    private CustomAdapter adapter;
    private ViewPager viewPager;

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
    @SuppressLint({"WrongViewCast", "ClickableViewAccessibility"})
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        wearingFactory = new WearingFactory(this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new CustomAdapter(this,wearingFactory);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeTransformer());

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();

                switch(action){
                    case ACTION_DOWN:
                        progressBarWrapper.stopBarAnimation();
                        Log.d("stop", "stop");
                        buttonHider = new ButtonHider(MainActivity.this);
                        buttonHider.execute();
                        break;

                    case ACTION_UP:
                        buttonHider.cancel(true);   //stops previously started buttonHider
                        if (!buttonHider.getSlept()){ //Simple tap
                            (new TapCalculation(MainActivity.this)).doInBackground(event);
                        }
                        else //long press
                            progressBarWrapper.resumeBarAnimation();
                        if(buttonHider.getSlept())
                            showButtons();

                        break;
                }

                return false;
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageSelected(int position) {
                progressBarWrapper.restartAnimation();

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //if (state == ViewPager.SCROLL_STATE_IDLE) showButtons();
                //else if (state == ViewPager.SCROLL_STATE_DRAGGING) hideButtons();
            }


        });



        like = (ImageButton) findViewById(R.id.like);
        dislike = (ImageButton) findViewById(R.id.dislike);
        swipeUp = (ImageButton) findViewById(R.id.swipeUp);

        // listener assignments
        like.setOnClickListener(likeListener = new ButtonsListener.LikeListener(this));
        dislike.setOnClickListener(new ButtonsListener.DislikeListener(this));
        swipeUp.setOnClickListener(new ButtonsListener.SwipeUpListener());

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        FullScreen fullScreen = new FullScreen(decorView);
        Thread onFull = new Thread(fullScreen);
        onFull.start();

        progressBarWrapper = new ProgressBarWrapper((ProgressBar) findViewById(R.id.progressbar),this);



        decorView.setOnTouchListener(new SwipeTouchListener(this));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void go_back(View view)
    {
        progressBarWrapper.stopBarAnimation();//per ricominciare?
        Intent intent = new Intent(this,ChoiceActivity.class);
        //putExtra method to send information to the new Activity
        startActivity(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void LeftTap()
    {
        progressBarWrapper.restartAnimation();
        resetButtons();
        likeListener.clearAnimation();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void RightTap()
    {
        progressBarWrapper.restartAnimation();
        resetButtons();
        likeListener.clearAnimation();
    }

    public void resetButtons()
    {
        like.setImageResource(R.drawable.like);
        dislike.setImageResource(R.drawable.dislike);
        like_pressed = false;
        dislike_pressed = false;
    }

    public void hideButtons(){
        Animation disappearence=AnimationUtils.loadAnimation(this, R.anim.disappearence);
        this.dislike.setVisibility(View.INVISIBLE);
        this.like.setVisibility(View.INVISIBLE);
        this.swipeUp.setVisibility(View.INVISIBLE);

        this.dislike.startAnimation(disappearence);
        this.like.startAnimation(disappearence);
        this.swipeUp.startAnimation(disappearence);
    }

    public void showButtons(){
        Animation appearance=AnimationUtils.loadAnimation(this, R.anim.appearance);
        this.dislike.setVisibility(View.VISIBLE);
        this.like.setVisibility(View.VISIBLE);
        this.swipeUp.setVisibility(View.VISIBLE);

        this.dislike.startAnimation(appearance);
        this.like.startAnimation(appearance);
        this.swipeUp.startAnimation(appearance );
    }

}
