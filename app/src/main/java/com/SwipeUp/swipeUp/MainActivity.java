package com.SwipeUp.swipeUp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.SwipeUp.shuffleListeners.shuffleOnGestureListener;
import com.SwipeUp.swipeUpMenu.SwipeUpActivity;
import com.SwipeUp.shuffleListeners.ButtonsListener;
import com.SwipeUp.shuffleListeners.PageChangeListener;
import com.SwipeUp.swipeManagement.CubeTransformer;
import com.SwipeUp.swipeManagement.CustomAdapter;
import com.SwipeUp.menu.ChoiceActivity;
import com.SwipeUp.swipeUp.asyncTasks.ButtonHider;
import com.SwipeUp.progressBar.ProgressBarWrapper;
import com.SwipeUp.wearingFactory.WearingFactory;

import static android.view.MotionEvent.ACTION_UP;

public class MainActivity extends AppCompatActivity {

    private FullScreen fullScreen;
    private ViewPager viewPager;

    private boolean isRunning=true;

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

    private ButtonHider buttonHider;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"WrongViewCast", "ClickableViewAccessibility"})
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shuffle_layout);

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
        this.isRunning=true;
        progressBarWrapper.resumeBarAnimation();
        fullScreen.setUIFullScreen();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onPause(){
        this.isRunning=false;
        super.onPause();
        progressBarWrapper.stopBarAnimation();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void go_back(View view)
    {
        progressBarWrapper.stopBarAnimation();//per ricominciare?
        Intent intent = new Intent(this,ChoiceActivity.class);
        startActivity(intent);
    }

    /**
     * Launches the SwipeUpActivity
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startSwipeUpActivity()
    {
        progressBarWrapper.stopBarAnimation();

        Intent intent = new Intent(this, SwipeUpActivity.class);

        startActivity(intent);
    }

    /**
     * Method called each time a left tap has been performed or the timeout of the image has expired:
     * show next image resetting buttons and progress bar
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void LeftTap()
    {
        progressBarWrapper.restartAnimation();
        resetButtons();
        likeListener.clearAnimation();

        adapter.currentImageView.setImageDrawable(wearingFactory.getPreviousImage());
    }

    /**
     * Method called each time a right tap has been performed: shows the previous image resetting
     * progress bar and buttons
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void RightTap()
    {
        progressBarWrapper.restartAnimation();
        resetButtons();
        likeListener.clearAnimation();

        adapter.currentImageView.setImageDrawable(wearingFactory.getNextImage());
    }

    /**
     * Private method call by onCreate
     */
    private void setupButtons(){
        like = (ImageButton) findViewById(R.id.like);
        dislike = (ImageButton) findViewById(R.id.dislike);
        swipeUp = (ImageButton) findViewById(R.id.swipeUp);

        like.setOnClickListener(likeListener = new ButtonsListener.LikeListener(this));
        dislike.setOnClickListener(new ButtonsListener.DislikeListener(this));
        swipeUp.setOnClickListener(new ButtonsListener.SwipeUpListener(this));
    }

    /**
     * Reset button to their unchecked state, called each time the image is switched
     */
    public void resetButtons()
    {
        like.setImageResource(R.drawable.like);
        dislike.setImageResource(R.drawable.dislike);
        like_pressed = false;
        dislike_pressed = false;
    }

    /**
     * Hides buttons, called whenever a long press has been detected
     */
    public void hideButtons() {

        Animation disappearence=AnimationUtils.loadAnimation(this, R.anim.disappearence);
        this.dislike.setVisibility(View.INVISIBLE);
        this.like.setVisibility(View.INVISIBLE);
        this.swipeUp.setVisibility(View.INVISIBLE);

        this.dislike.startAnimation(disappearence);
        this.like.startAnimation(disappearence);
        this.swipeUp.startAnimation(disappearence);
    }

    /**
     * Shows hidden buttons, called after a long press
     */
    public void showButtons(){
        Animation appearance=AnimationUtils.loadAnimation(this, R.anim.appearance);
        this.dislike.setVisibility(View.VISIBLE);
        this.like.setVisibility(View.VISIBLE);
        this.swipeUp.setVisibility(View.VISIBLE);

        this.dislike.startAnimation(appearance);
        this.like.startAnimation(appearance);
        this.swipeUp.startAnimation(appearance );
    }

    /**
     * Private method call by onCreate
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setupViewPager(){
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new CustomAdapter(this.getApplicationContext(),wearingFactory,this);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeTransformer());

        /**
         * Setting Up ViewPager Listeners
         */

        viewPager.addOnPageChangeListener(new PageChangeListener(this,adapter, progressBarWrapper));

        final GestureDetector gestureDetector = setupGestureDetector(this);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == ACTION_UP){
                    buttonHider.cancel(true);
                    if(buttonHider.getSlept()) showButtons();
                    progressBarWrapper.resumeBarAnimation();
                }
                return  gestureDetector.onTouchEvent(event);
            }
        });

    }

    /**
     * Private method call by onCreate
     */
    private GestureDetector setupGestureDetector(final MainActivity mainActivity){
        return new GestureDetector(mainActivity, new shuffleOnGestureListener(this, buttonHider));
    }

    /**
     * Returns true if the main activity is running, false otherwise
     * @return a boolean
     */
    public boolean getRunning(){
        return isRunning;
    }

}
