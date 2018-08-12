package com.SwipeUp.shuffleManagement;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.SwipeUp.shuffleManagement.shuffleListeners.shuffleOnGestureListener;
import com.SwipeUp.utilities.fullScreen.FullScreen;
import com.SwipeUp.utilities.R;
import com.SwipeUp.swipeUpManagement.SwipeUpActivity;
import com.SwipeUp.shuffleManagement.shuffleListeners.ButtonsListener;
import com.SwipeUp.shuffleManagement.shuffleListeners.PageChangeListener;
import com.SwipeUp.mainMenuManagement.MainMenuActivity;
import com.SwipeUp.utilities.asyncTasks.ButtonHider;
import com.SwipeUp.utilities.progressBar.ProgressBarWrapper;
import com.SwipeUp.utilities.wearingFactory.WearingFactory;

import static android.view.MotionEvent.ACTION_UP;

public class ShuffleActivity extends AppCompatActivity {

    private FullScreen fullScreen;

    private ViewPager viewPager;
    private ShuffleFragmentAdapter adapter;

    private boolean isRunning=true;

    public ImageButton like;
    public ImageButton dislike;
    private ImageView swipeUp;
    private ImageView swipeUpSwiped;

    public int DisplayWidth = 0;
    public ProgressBarWrapper progressBarWrapper;
    public boolean like_pressed;
    public boolean dislike_pressed;
    private boolean isSwiped;
    private WearingFactory wearingFactory;
    public static final int SwitchingDuration = 6000;

    private ButtonsListener.LikeListener likeListener;

    private ButtonHider buttonHider;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"WrongViewCast", "ClickableViewAccessibility"})
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shuffle_activity_layout);

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
        resetSwipeUpImage();
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
        Intent intent = new Intent(this,MainMenuActivity.class);
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

        //adapter.currentImageView.setImageDrawable(wearingFactory.getPreviousImage());
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

        //adapter.currentImageView.setImageDrawable(wearingFactory.getNextImage());
    }

    /**
     * Private method call by onCreate
     */
    private void setupButtons(){

        like = (ImageButton) findViewById(R.id.like);
        dislike = (ImageButton) findViewById(R.id.dislike);
        swipeUp = (ImageView) findViewById(R.id.swipeUp);
        swipeUpSwiped = (ImageView) findViewById(R.id.swipeUpSwiped);

        like.setOnClickListener(likeListener = new ButtonsListener.LikeListener(this));
        dislike.setOnClickListener(new ButtonsListener.DislikeListener(this));
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
        //adapter = new CustomAdapter(this.getApplicationContext(),wearingFactory,this);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new ShuffleFragmentAdapter(fm);
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
    private GestureDetector setupGestureDetector(final ShuffleActivity shuffleActivity){
        return new GestureDetector(shuffleActivity, new shuffleOnGestureListener(this));
    }

    /**
     * Returns true if the main activity is running, false otherwise
     * @return a boolean
     */
    public boolean getRunning(){
        return isRunning;
    }

    public void setButtonHider(ButtonHider buttonHider){
        this.buttonHider = buttonHider;
    }

    public void executeButtonHider(){
        buttonHider.execute();
    }

    /**
     * due funzioni che gestiscono la comparsa del logo nel momento dello swipeUp, il controllo ulteriore con il booleano non è
     * strettamente necessario, l'ho inserito per evitare che il codice venga eseguito anche se non richiesto rendendo un bottone visibile
     * nuovamente visibileq
     */

    public void setSwipeUpImage(){
        if(!isSwiped){
            swipeUp.setVisibility(View.INVISIBLE);
            swipeUpSwiped.setVisibility(View.VISIBLE);
            isSwiped = true;
        }
    }

    public void resetSwipeUpImage(){
        if(isSwiped){
            swipeUp.setVisibility(View.VISIBLE);
            swipeUpSwiped.setVisibility(View.INVISIBLE);
            isSwiped = false;
        }
    }

}
