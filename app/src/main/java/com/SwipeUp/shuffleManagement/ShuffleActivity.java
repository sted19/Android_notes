package com.SwipeUp.shuffleManagement;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.SwipeUp.utilities.asyncTasks.ShowLogo;
import com.SwipeUp.utilities.fullScreen.FullScreen;
import com.SwipeUp.utilities.R;
import com.SwipeUp.shuffleManagement.shuffleListeners.ButtonsListener;
import com.SwipeUp.shuffleManagement.shuffleListeners.PageChangeListener;
import com.SwipeUp.mainMenuManagement.MainMenuActivity;

public class ShuffleActivity extends AppCompatActivity {
    public int DisplayWidth;

    private FullScreen fullScreen;

    private ViewPager viewPager;
    public ShuffleFragmentAdapter adapter;

    // TODO: improve activity recognition with appropriated method
    private boolean isRunning = true;

    private boolean isSwiped;
    private boolean buttonsHidden;
    public boolean like_pressed;
    public boolean dislike_pressed;

    public ShowLogo logoShower;

    public ImageButton like;
    public ImageButton dislike;
    private ImageView swipeUp;
    private ImageView swipeUpSwiped;

    public static final int SwitchingDuration = 6000;

    private ButtonsListener.LikeListener likeListener;
    private PageChangeListener mPageChangeListener;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"WrongViewCast", "ClickableViewAccessibility"})
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculateWidth();

        setContentView(R.layout.shuffle_activity_layout);

        showLogo();

        /*
          Handling Full Screen in a different class
         */

        fullScreen = new FullScreen(getWindow().getDecorView());
        fullScreen.setUIFullScreen();
        fullScreen.fullScreenKeeper();

        /*
         * Setting up viewPager in a separate function
         */
        setupViewPager();

        /*
         * Setting up buttons in a separate function (I think we should modify the ButtonsListener)
         */
        setupButtons();

    }

    @Override
    protected void onResume() {
        super.onResume();
        resetSwipeUpImage();
        if(!isSwiped) showLogo();
        this.isRunning = true;
        fullScreen.setUIFullScreen();
    }

    public void onPause(){
        this.isRunning = false;
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void go_back(View view)
    {
//        progressBarWrapper.stopBarAnimation();//per ricominciare? (probabilmente non necessario)
        Intent intent = new Intent(this,MainMenuActivity.class);
        startActivity(intent);
    }

    /**
     * Method called each time a left tap has been performed or the timeout of the image has expired:
     * show next image resetting buttons and progress bar (should be called only by a ShuffleFragment)
     */
    public void LeftTap()
    {
        resetButtons();
        likeListener.clearAnimation();
    }

    /**
     * Method called each time a right tap has been performed: shows the previous image resetting
     * progress bar and buttons (should be called only by a ShuffleFragment)
     */
    public void RightTap()
    {
        resetButtons();
        likeListener.clearAnimation();
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
        resetSwipeUpImage();

        logoShower.cancel(true);
        showLogo();
    }

    /**
     * Hides buttons, called whenever a long press has been detected
     */
    public void hideButtons() {

        Animation disappearence=AnimationUtils.loadAnimation(this, R.anim.disappearence);
        this.dislike.setVisibility(View.INVISIBLE);
        this.like.setVisibility(View.INVISIBLE);
        if(isSwiped){
            this.swipeUpSwiped.setVisibility(View.INVISIBLE);
            this.swipeUpSwiped.startAnimation(disappearence);
        }
        else{
            this.swipeUp.setVisibility(View.INVISIBLE);
            this.swipeUp.startAnimation(disappearence);
        }

        this.dislike.startAnimation(disappearence);
        this.like.startAnimation(disappearence);
        buttonsHidden = true;
        logoShower.cancel(true);

    }

    /**
     * Shows hidden buttons, called after a long press
     */
    public void showButtons(){
        Animation appearance=AnimationUtils.loadAnimation(this, R.anim.appearance);
        this.dislike.setVisibility(View.VISIBLE);
        this.like.setVisibility(View.VISIBLE);
        if(isSwiped){
            this.swipeUpSwiped.setVisibility(View.VISIBLE);
            this.swipeUpSwiped.startAnimation(appearance);
        }
        else{
            this.swipeUp.setVisibility(View.VISIBLE);
            this.swipeUp.startAnimation(appearance);
            showLogo();
        }

        this.dislike.startAnimation(appearance);
        this.like.startAnimation(appearance);
        buttonsHidden = false;
    }

    /**
     * Private method called by onCreate
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setupViewPager(){
        viewPager = findViewById(R.id.pager);

        adapter = new ShuffleFragmentAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeTransformer());

        /*
         * Setting Up ViewPager Listener
         */
        mPageChangeListener = new PageChangeListener(this);
        viewPager.addOnPageChangeListener(mPageChangeListener);
    }

    /**
     * Returns true if the main activity is running, false otherwise
     * @return a boolean
     */
    public boolean getRunning(){
        return isRunning;
    }

    /**
     * due funzioni che gestiscono la comparsa del logo nel momento dello swipeUp, il controllo
     * ulteriore con il booleano non è strettamente necessario, l'ho inserito per evitare che
     * il codice venga eseguito anche se non richiesto rendendo un bottone visibile
     * nuovamente visibile
     */

    public void setSwipeUpImage(){
        if(!isSwiped && !buttonsHidden){
            swipeUp.setVisibility(View.INVISIBLE);
            swipeUpSwiped.setVisibility(View.VISIBLE);
            isSwiped = true;
        }
    }

    public void resetSwipeUpImage(){
        if(isSwiped){
            swipeUpSwiped.setVisibility(View.INVISIBLE);
            swipeUp.setVisibility(View.VISIBLE);
            isSwiped = false;
        }
    }

    public void showLogo(){
        logoShower = new ShowLogo();
        logoShower.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this);
    }

    public void setFirstFragment(ShuffleFragment shuffleFragment){
        mPageChangeListener.setCurrentFragment(shuffleFragment);
    }

    /**
     * Changes the position of the pager with relative animation
     * @param position the new position of the pager
     * @return true if the position is valid, false otherwise
     */
    public boolean setPagerPosition(int position){
        if(position >= 0  && position < adapter.getCount()){
            viewPager.setCurrentItem(position);
            return true;
        }
        return false;
    }

    /**
     * method called at the instantiation of the activity, calculates the display width
     * @return
     */
    private void calculateWidth(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DisplayWidth = metrics.widthPixels;
    }
}
