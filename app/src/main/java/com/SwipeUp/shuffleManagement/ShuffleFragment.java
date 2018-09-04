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

import com.SwipeUp.mainMenuManagement.MainMenuActivity;
import com.SwipeUp.shuffleManagement.shuffleListeners.ShuffleOnGestureListener;
import com.SwipeUp.swipeUpManagement.SwipeUpActivity;
import com.SwipeUp.utilities.Constants;
import com.SwipeUp.utilities.R;
import com.SwipeUp.utilities.asyncTasks.ShowLogo;
import com.SwipeUp.utilities.progressBar.ProgressBarWrapper;
import com.SwipeUp.utilities.wearingFactory.MiniWearingfactory;
import com.SwipeUp.utilities.wearingFactory.WearingFactoryNew;
import com.bumptech.glide.Glide;

import static android.view.MotionEvent.ACTION_UP;

public class ShuffleFragment extends Fragment{

    //private final static int NUM_ELEMENTS = 3;      // provvisorio, verrà preso dalla wearing

    private ShuffleActivity mShuffleActivity;
    //private WearingFactory wearingFactory;
    private MiniWearingfactory miniWearingfactory;

    private boolean likesPressed;
    private boolean dislikesPressed;
    private boolean hidden;

    private int displayWidth;

    private int position;
    private int index;
    private int availableImages = 0;

    private ProgressBarWrapper progressBarWrapper;

    private ImageView swipeImage;
    private ImageView swipeArrow;
    private ImageView swipeUpLogo;
    private ImageButton dislike;
    private ImageButton like;
    private ImageButton xButton;
    private ImageButton brandLogo;
    private ImageView heart;
    private ImageView mBlueRound;

    private View view;

    private ShowLogo mShowLogo;

    public static ShuffleFragment newInstance(int position,int index){
        Bundle args = new Bundle();
        args.putInt(Constants.POSITION, position);
        args.putInt(Constants.INDEX,index);

        ShuffleFragment shuffleFragment = new ShuffleFragment();
        shuffleFragment.setArguments(args);
        return shuffleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShuffleActivity = (ShuffleActivity) getActivity();
        displayWidth = mShuffleActivity.getDisplayWidth();

        position = getArguments().getInt(Constants.POSITION);
        index = getArguments().getInt(Constants.INDEX);

        //wearingFactory = WearingFactory.getInstance(this, 1);
        //availableImages = NUM_ELEMENTS;


        miniWearingfactory = new MiniWearingfactory(this,position == -1 ? 0 : position);
        availableImages = miniWearingfactory.getAvailableImages();
        WearingFactoryNew wearingFactory = WearingFactoryNew.getInstance();
        wearingFactory.addMiniWearingFactory(miniWearingfactory,position);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shuffle_viewpager_fragment_item,container,false);

        findUIElements(view);
        setupListener(view);
        instantiateProgressBars(view);

        Glide
                .with(view)
                .load(miniWearingfactory.getImage(index))
                .into(swipeImage);

        brandLogo.setImageResource(R.drawable.brand_logo);

        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onStop(){
        super.onStop();
        progressBarWrapper.stopBarAnimation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progressBarWrapper.destroyBars();
    }

    public void findUIElements(View v){
        swipeImage = v.findViewById(R.id.swipe_image);
        swipeArrow = v.findViewById(R.id.swipe_arrow);
        swipeUpLogo = v.findViewById(R.id.swipe_up_logo);
        dislike = v.findViewById(R.id.dislike);
        like = v.findViewById(R.id.like);
        heart = v.findViewById(R.id.heart);
        xButton = v.findViewById(R.id.x_button);
        brandLogo = v.findViewById(R.id.brand_logo);
        mBlueRound = v.findViewById(R.id.brand_logo_blue_round);

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

    }

    public void likePressed(){
        if(!likesPressed){
            likesPressed = true;
            dislikesPressed = false;
            like.setImageResource(R.drawable.like_pressed);
            dislike.setImageResource(R.drawable.dislike);
            Animation appearance = AnimationUtils.loadAnimation(getContext(),R.anim.appearance);
            like.startAnimation(appearance);
            Animation pulse = AnimationUtils.loadAnimation(getContext(),R.anim.zoom_and_disappearance);
            heart.startAnimation(pulse);
        }
        else{
            Animation appearence=AnimationUtils.loadAnimation(getContext(), R.anim.appearance_like_x);
            likesPressed = false;
            like.setImageResource(R.drawable.like);
            like.startAnimation(appearence);
        }

    }

    public void dislikePressed(){
        if(!dislikesPressed){
            likesPressed = false;
            dislikesPressed=true;
            like.setImageResource(R.drawable.like);
            dislike.setImageResource(R.drawable.dislike_pressed);
            Animation appearance=AnimationUtils.loadAnimation(getContext(), R.anim.appearance_like_x);
            dislike.startAnimation(appearance);
        }
        else{
            dislikesPressed = false;
            dislike.setImageResource(R.drawable.dislike);
            Animation appearance=AnimationUtils.loadAnimation(getContext(), R.anim.appearance_like_x);
            dislike.startAnimation(appearance);
        }

    }

    public void xButtonPressed(){
        Intent intent = new Intent(getActivity(),MainMenuActivity.class);
        getActivity().overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    public void hideButtons(){
        hidden = true;
        Animation disappearance = AnimationUtils.loadAnimation(getContext(), R.anim.disappearance);

        xButton.setVisibility(View.INVISIBLE);
        dislike.setVisibility(View.INVISIBLE);
        like.setVisibility(View.INVISIBLE);
        swipeArrow.setVisibility(View.INVISIBLE);
        brandLogo.setVisibility(View.INVISIBLE);
        mBlueRound.setVisibility(View.INVISIBLE);

        progressBarWrapper.hideBars();

        xButton.startAnimation(disappearance);
        dislike.startAnimation(disappearance);
        like.startAnimation(disappearance);
        swipeArrow.startAnimation(disappearance);
        brandLogo.startAnimation(disappearance);
        mBlueRound.startAnimation(disappearance);
    }

    public void showButtons(){
        hidden = false;
        Animation appearance = AnimationUtils.loadAnimation(getContext(), R.anim.appearance);

        xButton.setVisibility(View.VISIBLE);
        dislike.setVisibility(View.VISIBLE);
        like.setVisibility(View.VISIBLE);
        swipeArrow.setVisibility(View.VISIBLE);
        mBlueRound.setVisibility(View.VISIBLE);
        brandLogo.setVisibility(View.VISIBLE);
        progressBarWrapper.showBars();

        xButton.setAnimation(appearance);
        dislike.startAnimation(appearance);
        like.startAnimation(appearance);
        swipeArrow.startAnimation(appearance);
        mBlueRound.startAnimation(appearance);
        brandLogo.startAnimation(appearance);
    }

    public void setupListener(View v){

        final GestureDetector gestureDetector = new GestureDetector(getContext(),new ShuffleOnGestureListener(this,displayWidth));

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
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }

    public void resetButtons(){
        if(likesPressed)
            like.setImageResource(R.drawable.like);
        if(dislikesPressed)
            dislike.setImageResource(R.drawable.dislike);
        //like.clearAnimation();
        likesPressed = false;
        dislikesPressed = false;
//        swipeUpLogo.setVisibility(View.INVISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void instantiateProgressBars(View v){

        ProgressBar[] vectProgressBar = new ProgressBar[availableImages];

        LinearLayout linearLayout = v.findViewById(R.id.progress_bar_layout);

        int padding=5;

        for(int i=0; i<availableImages; i++){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    (displayWidth - (2 * padding * (availableImages+1))) / availableImages, 6);
            if(i==0) {
                layoutParams.setMargins(padding*2, 15 ,padding, 5);
            }
            else if(i==availableImages-1){
                layoutParams.setMargins(padding, 15 ,padding*2, 5);
            }
            else{
                layoutParams.setMargins(padding, 15 ,padding, 5);
            }


            ProgressBar progressBar=new ProgressBar(getContext(),
                    null, android.R.attr.progressBarStyleHorizontal);
            progressBar.setLayoutParams(layoutParams);

            progressBar.setBackgroundColor(Color.GRAY);
            progressBar.getProgressDrawable().setColorFilter(Color.WHITE,android.graphics.PorterDuff.Mode.SRC_IN);


            vectProgressBar[i]=progressBar;
            linearLayout.addView(progressBar);

        }

        progressBarWrapper= new ProgressBarWrapper(vectProgressBar, this,index);

        progressBarWrapper.stopBarAnimation();

        if(position == -1){
            progressBarWrapper.restartAnimation();
            position = 0;
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startSwipeUpActivity(){
        progressBarWrapper.stopBarAnimation();

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
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void resetLastBar(){
        Log.e("resetlastbar","resetlastbar");
        progressBarWrapper.resetLastBarAnimation();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void leftTap(){
//        interruptShowLogo();

        Log.i("imageIndex", "is "+index);
        if(index == 0) {
            if(position == 0)
                resetLastBar();
            else{
                mShuffleActivity.triggerLeftSwipe(position);
                progressBarWrapper.restartAnimation();
                progressBarWrapper.stopBarAnimation();
            }
        }
        else {               //sistemato il fatto che un tap a sx portasse al blocco del primo fragment
            progressBarWrapper.startPrevAnimation();
            resetButtons();
            index--;
            if(index < 0)
                index += availableImages;
            Drawable image = miniWearingfactory.getImage(index);
            setNextImage(image);
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void rightTap(){
//        interruptShowLogo();

        Log.i("imageIndex", "is "+index);
        if(index == availableImages - 1){
            if(mShuffleActivity.triggerRightSwipe(position)) {
                progressBarWrapper.restartAnimation();
                progressBarWrapper.stopBarAnimation();
            }
        }
        else {
            progressBarWrapper.startNextAnimation();
            resetButtons();
            index++;
            if(index >= availableImages){
                index = 0;
            }
            Drawable image = miniWearingfactory.getImage(index);
            setNextImage(image);
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
    private void setNextImage(Drawable image){
        Glide
                .with(view)
                .load(image)
                .into(swipeImage);
    }

    public int getIndex(){
        return index;
    }

    public void upgradeView(){
        setNextImage(miniWearingfactory.getImage(index));
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

    public void setSwipeUpImage(){
        swipeUpLogo.setVisibility(View.VISIBLE);
    }

}
