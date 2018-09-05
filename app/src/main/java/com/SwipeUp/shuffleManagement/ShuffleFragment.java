package com.SwipeUp.shuffleManagement;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.SwipeUp.mainMenuManagement.MainMenuActivity;
import com.SwipeUp.shuffleManagement.shuffleListeners.ShuffleOnGestureListener;
import com.SwipeUp.swipeUpManagement.SwipeUpActivity;
import com.SwipeUp.utilities.Constants;
import com.SwipeUp.utilities.R;
import com.SwipeUp.utilities.asyncTasks.ShowLogo;
import com.SwipeUp.utilities.progressBar.ProgressBarWrapper;
import com.SwipeUp.utilities.wearingFactory.MiniWearingFactory;
import com.SwipeUp.utilities.wearingFactory.WearingFactory;
import com.bumptech.glide.Glide;

import static android.view.MotionEvent.ACTION_UP;

public class ShuffleFragment extends Fragment{
    private static final int progressBarPadding = 5;

    private ShuffleActivity mShuffleActivity;
    private MiniWearingFactory miniWearingFactory;
    private GestureDetector mGestureDetector;

    private boolean mLikePressed;
    private boolean mDislikesPressed;
    private boolean hidden;

    private int displayWidth;

    private int position;
    private int index;
    private int availableImages = 0;

    private ProgressBarWrapper progressBarWrapper;

    private RelativeLayout blueBar;

    private ImageView swipeImage;
    private ImageView swipeArrow;
    private ImageView swipeUpLogo;
    private ImageButton dislike;
    private ImageButton like;
    private ImageButton xButton;
    private ImageButton brandLogo;
    private ImageView heart;
    private ImageView mBlueRound;
    private TextView swipeUpText;

    private View view;
    
    private Animation mAppearanceLikeX;
    private Animation mPulse;
    private Animation mAppearance;
    private Animation mDisappearance;
    private Animation mAppearanceFading;
    private Animation mDisappearanceFading;

    private ShowLogo mShowLogo;

    public static ShuffleFragment newInstance(int position,int index){
        Bundle args = new Bundle();
        args.putInt(Constants.POSITION, position);
        args.putInt(Constants.INDEX, index);

        ShuffleFragment shuffleFragment = new ShuffleFragment();
        shuffleFragment.setArguments(args);
        return shuffleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShuffleActivity = (ShuffleActivity) getActivity();
        displayWidth = mShuffleActivity.getDisplayWidth();

        retrieveArguments();

        initializeWearingFactory();
    }

    /**
     * Get variables among the arguments, called by onCreate
     */
    private void retrieveArguments(){
        position = getArguments().getInt(Constants.POSITION);
        index = getArguments().getInt(Constants.INDEX);
    }

    /**
     * Initialize the wearing factory, called by onCreate
     */
    private void initializeWearingFactory(){
        miniWearingFactory = new MiniWearingFactory(this, position == -1 ? 0 : position);
        availableImages = miniWearingFactory.getAvailableImages();
        WearingFactory wearingFactory = WearingFactory.getInstance();
        wearingFactory.addMiniWearingFactory(miniWearingFactory,position);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shuffle_viewpager_fragment_item,container,false);

        findUIElements(view);
        setupListener(view);
        instantiateProgressBars(view);

        loadImage(miniWearingFactory.getImage(index));

        //Just for now every image will have the same generic brand
        brandLogo.setImageResource(R.drawable.brand_logo);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onStop(){
        super.onStop();
        progressBarWrapper.stopBarAnimation();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        progressBarWrapper.destroyBars();
    }

    /**
     * Finds fragment elements and stores them in instance variables
     */
    private void findUIElements(View v){
        swipeImage = v.findViewById(R.id.swipe_image);
        swipeArrow = v.findViewById(R.id.swipe_arrow);
        swipeUpLogo = v.findViewById(R.id.swipe_up_logo);
        swipeUpText = v.findViewById(R.id.swipe_up_text);
        dislike = v.findViewById(R.id.dislike);
        like = v.findViewById(R.id.like);
        heart = v.findViewById(R.id.heart);
        xButton = v.findViewById(R.id.x_button);
        brandLogo = v.findViewById(R.id.brand_logo);
        mBlueRound = v.findViewById(R.id.brand_logo_blue_round);
        blueBar = v.findViewById(R.id.blue_bar);

        mAppearanceLikeX = AnimationUtils.loadAnimation(getContext(), R.anim.appearance_like_x);
        mAppearance = AnimationUtils.loadAnimation(getContext(), R.anim.appearance);
        mPulse = AnimationUtils.loadAnimation(getContext(), R.anim.zoom_and_disappearance);
        mDisappearance = AnimationUtils.loadAnimation(getContext(), R.anim.disappearance);
        mAppearanceFading = AnimationUtils.loadAnimation(getContext(),R.anim.appearance_fading);
        mDisappearanceFading = AnimationUtils.loadAnimation(getContext(),R.anim.disappearance_fading);
    }

    /**
     * Set up listeners for the elements in the fragment
     */
    private void setupListener(@NonNull View v){

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePressed();
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislikePressed();
            }
        });

        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xButtonPressed();
            }
        });

        mGestureDetector = new GestureDetector(getContext(), new ShuffleOnGestureListener(this,
                displayWidth));

        v.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == ACTION_UP){
                    if(hidden){
                        Log.e("ACTION_UP","ACTION_UP");
                        showButtons();
                        ShuffleActivity shuffleActivity=(ShuffleActivity) getActivity();
                        shuffleActivity.disableScroll(false);
                    }
                    progressBarWrapper.resumeBarAnimation();
                }
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }

    private void likePressed(){
        if(!mLikePressed){
            mLikePressed = true;
            mDislikesPressed = false;

            //properly sets images of the buttons
            like.setImageResource(R.drawable.like_pressed);
            dislike.setImageResource(R.drawable.dislike);

            like.startAnimation(mAppearance);
            heart.startAnimation(mPulse);
        }
        else{
            mLikePressed = false;
            like.setImageResource(R.drawable.like);
            like.startAnimation(mAppearanceLikeX);
        }

    }

    private void dislikePressed(){
        if(!mDislikesPressed){
            mLikePressed = false;
            mDislikesPressed =true;

            //properly sets images of the buttons
            like.setImageResource(R.drawable.like);

            dislike.setImageResource(R.drawable.dislike_pressed);
            dislike.startAnimation(mAppearanceLikeX);
        }
        else{
            mDislikesPressed = false;
            dislike.setImageResource(R.drawable.dislike);
            dislike.startAnimation(mAppearanceLikeX);
        }

    }

    private void xButtonPressed(){
        Intent intent = new Intent(getActivity(),MainMenuActivity.class);
        getActivity().overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    public void hideButtons(){
        hidden = true;


        dislike.setVisibility(View.INVISIBLE);
        mBlueRound.setVisibility(View.INVISIBLE);
        brandLogo.setVisibility(View.INVISIBLE);
        like.setVisibility(View.INVISIBLE);
        xButton.setVisibility(View.INVISIBLE);
        swipeArrow.setVisibility(View.INVISIBLE);
        swipeUpText.setVisibility(View.INVISIBLE);
        blueBar.setVisibility(View.INVISIBLE);

        progressBarWrapper.hideBars();

        dislike.startAnimation(mDisappearance);
        mBlueRound.startAnimation(mDisappearance);
        brandLogo.startAnimation(mDisappearance);
        like.startAnimation(mDisappearance);
        xButton.startAnimation(mDisappearanceFading);
        swipeArrow.startAnimation(mDisappearanceFading);
        swipeUpText.startAnimation(mDisappearanceFading);
        blueBar.startAnimation(mDisappearanceFading);
    }

    public void showButtons(){
        hidden = false;

        dislike.setVisibility(View.VISIBLE);
        mBlueRound.setVisibility(View.VISIBLE);
        brandLogo.setVisibility(View.VISIBLE);
        like.setVisibility(View.VISIBLE);
        xButton.setVisibility(View.VISIBLE);
        swipeArrow.setVisibility(View.VISIBLE);
        swipeUpText.setVisibility(View.VISIBLE);
        blueBar.setVisibility(View.VISIBLE);

        progressBarWrapper.showBars();

        dislike.startAnimation(mAppearance);
        mBlueRound.startAnimation(mAppearance);
        brandLogo.startAnimation(mAppearance);
        like.startAnimation(mAppearance);
        xButton.setAnimation(mAppearanceFading);
        swipeArrow.startAnimation(mAppearanceFading);
        swipeUpText.startAnimation(mAppearanceFading);
        blueBar.startAnimation(mAppearanceFading);
    }

    private void resetButtons(){
        if(mLikePressed)
            like.setImageResource(R.drawable.like);
        else if(mDislikesPressed)
            dislike.setImageResource(R.drawable.dislike);
        //like.clearAnimation();
        mLikePressed = false;
        mDislikesPressed = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void instantiateProgressBars(View v){
        ProgressBar[] vectProgressBar = drawBarVector(v);

        progressBarWrapper= new ProgressBarWrapper(vectProgressBar, this, index);
        progressBarWrapper.stopBarAnimation();

        if(position == -1){
            progressBarWrapper.restartAnimation();
            position = 0;
        }
    }

    /**
     * Allocates the vector of progress bars and draws each element
     */
    private ProgressBar[] drawBarVector(View v){
        ProgressBar[] vectProgressBar = new ProgressBar[availableImages];
        LinearLayout linearLayout = v.findViewById(R.id.progress_bar_layout);

        for(int i=0; i<availableImages; i++){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    (displayWidth - ( 2 * progressBarPadding * (availableImages+1))) /
                            availableImages, 6);
            if(i==0)
                layoutParams.setMargins(progressBarPadding *2, 15 , progressBarPadding, 5);
            else if( i == availableImages - 1 )
                layoutParams.setMargins(progressBarPadding, 15 , progressBarPadding *2, 5);
            else
                layoutParams.setMargins(progressBarPadding, 15 , progressBarPadding, 5);

            //Sets bar appearance
            ProgressBar progressBar=new ProgressBar(getContext(),
                    null, android.R.attr.progressBarStyleHorizontal);
            progressBar.setLayoutParams(layoutParams);
            progressBar.setBackgroundColor(Color.GRAY);
            progressBar.getProgressDrawable().setColorFilter(Color.WHITE,android.graphics.PorterDuff.Mode.SRC_IN);

            vectProgressBar[i]=progressBar;
            linearLayout.addView(progressBar);
        }
        return vectProgressBar;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startSwipeUpActivity(){
        stopBarAnimation();

        Intent intent = new Intent(getContext(), SwipeUpActivity.class);
        intent.putExtra(Constants.POSITION, position);
        intent.putExtra(Constants.INDEX,index);
        startActivity(intent);
        // TODO inform through bundle which picture to show
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void stopBarAnimation(){
        progressBarWrapper.stopBarAnimation();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startProgressBar(){
        if(progressBarWrapper != null)
            progressBarWrapper.restartAnimation();
    }

    /**
     * Reset current used bar
     * @param stopIfRunning if true the bar will be stopped
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void resetLastBar(boolean stopIfRunning){
        progressBarWrapper.resetLastBarAnimation();
        if(stopIfRunning)
            progressBarWrapper.stopBarAnimation();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void leftTap(){
        Log.i("imageIndex", "is "+index);
        if(index == 0) {
            if(position == 0)
                resetLastBar(false);
            else{
                mShuffleActivity.triggerLeftSwipe(position);
                resetLastBar(true);
            }
        }
        else {
            progressBarWrapper.startPrevAnimation();
            resetButtons();
            index = (--index) % availableImages;
            loadImage(miniWearingFactory.getImage(index));
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void rightTap(){
        Log.i("imageIndex", "is "+index);
        if(index == availableImages - 1){
            if(mShuffleActivity.triggerRightSwipe(position))
                resetLastBar(true);
        }
        else {
            progressBarWrapper.startNextAnimation();
            resetButtons();
            index = (++index) % availableImages;
            loadImage(miniWearingFactory.getImage(index));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resumeProgressBar(){
        Log.e("resume","resume");
        if(progressBarWrapper!= null)
            progressBarWrapper.resumeBarAnimation();
    }

    /**
     * Load image in the current ImageView
     */
    private void loadImage(Drawable image){
        Glide
                .with(view)
                .load(image)
                .into(swipeImage);
    }

    public int getIndex(){
        return index;
    }

    public void upgradeView(){
        loadImage(miniWearingFactory.getImage(index));
    }

    /**
     * Start showLogo AsyncTask
     */
    public void startShowLogo(){
        mShowLogo = new ShowLogo();
        mShowLogo.execute(this);
    }

    /**
     * Interrupt ShowLogo AsyncTask
     */
    public void interruptShowLogo(){ mShowLogo.cancel(true); }

    public void showSwipeUpImage(){
        swipeUpLogo.setVisibility(View.VISIBLE);
    }

}
