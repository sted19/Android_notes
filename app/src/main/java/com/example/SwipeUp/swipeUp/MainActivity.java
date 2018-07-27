package com.example.SwipeUp.swipeUp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.SwipeUp.shuffleListeners.ButtonsListener;
import com.example.SwipeUp.shuffleListeners.PageChangeListener;
import com.example.SwipeUp.shuffleListeners.TouchListener;
import com.example.SwipeUp.swipeManagement.CubeTransformer;
import com.example.SwipeUp.swipeManagement.CustomAdapter;
import com.example.SwipeUp.menu.ChoiceActivity;
import com.example.SwipeUp.swipeUp.asyncTasks.ButtonHider;
import com.example.SwipeUp.progressBar.ProgressBarWrapper;
import com.example.SwipeUp.swipeUp.asyncTasks.TapCalculation;
import com.example.SwipeUp.swipeUp.asyncTasks.TapRecognizer;
import com.example.SwipeUp.wearingFactory.WearingFactory;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class MainActivity extends AppCompatActivity {

    private FullScreen fullScreen;
    private ViewPager viewPager;

    public ImageButton like;
    public ImageButton dislike;
    private ImageButton swipeUp;

    public int DisplayWidth = 0;
    public ProgressBarWrapper progressBarWrapper;
    public boolean like_pressed;
    public boolean dislike_pressed;
    private WearingFactory wearingFactory;
    public static final int SwitchingDuration = 6000;

    private ButtonsListener.LikeListener likeListener;

    private CustomAdapter adapter;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"WrongViewCast", "ClickableViewAccessibility"})
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        progressBarWrapper = new ProgressBarWrapper((ProgressBar) findViewById(R.id.progressbar),this);

        /**
         * Handling Full Screen in a different class
         */

        fullScreen = new FullScreen(getWindow().getDecorView());
        fullScreen.setUIFullScreen();
        fullScreen.fullScreenKeeper();

        /**
         * Setting up viewPager in a separate function
         */
        wearingFactory = new WearingFactory(this);
        setupViewPager();


        /**
         * Setting up buttons in a separate function (I think we should modify the ButtonsListener)
         */

        setupButtons();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        progressBarWrapper.resumeBarAnimation();
        fullScreen.setUIFullScreen();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onPause(){
        super.onPause();
        progressBarWrapper.stopBarAnimation();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void go_back(View view)
    {
        progressBarWrapper.stopBarAnimation();//per ricominciare?
        Intent intent = new Intent(this,ChoiceActivity.class);
        //TODO: putExtra method to send needed information to the new Activity
        startActivity(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void LeftTap()
    {
        progressBarWrapper.restartAnimation();
        resetButtons();
        likeListener.clearAnimation();

        adapter.currentImageView.setImageDrawable(wearingFactory.getPreviousImage());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void RightTap()
    {
        progressBarWrapper.restartAnimation();
        resetButtons();
        likeListener.clearAnimation();

        adapter.currentImageView.setImageDrawable(wearingFactory.getNextImage());
    }

    public void setupButtons(){
        like = (ImageButton) findViewById(R.id.like);
        dislike = (ImageButton) findViewById(R.id.dislike);
        swipeUp = (ImageButton) findViewById(R.id.swipeUp);

        like.setOnClickListener(likeListener = new ButtonsListener.LikeListener(this));
        dislike.setOnClickListener(new ButtonsListener.DislikeListener(this));
        swipeUp.setOnClickListener(new ButtonsListener.SwipeUpListener());
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

    @SuppressLint("ClickableViewAccessibility")
    public void setupViewPager(){
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new CustomAdapter(this.getApplicationContext(),wearingFactory,this);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeTransformer());

        //setting up viewPager listeners
        viewPager.setOnTouchListener(new TouchListener(this));
        viewPager.addOnPageChangeListener(new PageChangeListener(adapter, progressBarWrapper));
    }

    public CustomAdapter getCustomAdapter(){
        return  adapter;
    }

}
