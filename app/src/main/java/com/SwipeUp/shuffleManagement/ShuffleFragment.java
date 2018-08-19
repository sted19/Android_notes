package com.SwipeUp.shuffleManagement;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.SwipeUp.shuffleManagement.shuffleListeners.shuffleOnGestureListener;
import com.SwipeUp.swipeUpManagement.SwipeUpActivity;
import com.SwipeUp.utilities.R;
import com.SwipeUp.utilities.asyncTasks.ButtonHider;
import com.SwipeUp.utilities.progressBar.ProgressBarWrapper;
import com.bumptech.glide.Glide;

import static android.view.MotionEvent.ACTION_UP;

public class ShuffleFragment extends Fragment{
    private static boolean first = true;
    private static final String POSITION_KEY = "position";
    private ImageView imageView;
    private int position;
    private ShuffleActivity mShuffleActivity;
    public ProgressBarWrapper progressBarWrapper;
    private GestureDetector gestureDetector;

    private ButtonHider buttonHider;

    /**
     * get a new ShuffleFragment
     * @param position the position of the shuffleFragment in the viewPager
     * @return an instance of ShuffleFragment
     */
    public static ShuffleFragment newInstance(int position){
        Bundle args = new Bundle();
        args.putInt(POSITION_KEY, position);

        ShuffleFragment shuffleFragment = new ShuffleFragment();
        shuffleFragment.setArguments(args);
        return shuffleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shuffle_viewpager_fragment_item, container,false);
        findViews(v);
        setUpListeners(v);

        position = this.getArguments().getInt(POSITION_KEY);

        //WearingFactory wearingFactory = new WearingFactory((ShuffleActivity) this.getActivity());

        switch(position%2){
            case 0:
                Glide
                        .with(v)
                        .load(R.drawable.gigiproietti)
                        .into(imageView);
                break;
            case 1:
                Glide
                        .with(v)
                        .load(R.drawable.pants)
                        .into(imageView);
                break;
        }

        return v;
    }

    /**
     * Private method used to find views and to save them in instance variables
     */
    private void findViews(View v){
        mShuffleActivity = (ShuffleActivity) getActivity();
        imageView = v.findViewById(R.id.swipe_image);
        progressBarWrapper = new ProgressBarWrapper((ProgressBar) v.findViewById(R.id.progressbar),
                mShuffleActivity);

        if(first){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                progressBarWrapper.resumeBarAnimation();
            }
            mShuffleActivity.setFirstFragment(this);
            first = false;
        }
    }

    /**
     * private method called by onCreate: sets listeners of the passed view
     */
    private void setUpListeners(View view){
        gestureDetector = setupGestureDetector(mShuffleActivity);

        view.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == ACTION_UP){
                    buttonHider.cancel(true);
                    if(buttonHider.getSlept())
                        mShuffleActivity.showButtons();
                    progressBarWrapper.resumeBarAnimation();
                }
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    /**
     * Private method called on initialization, sets the gestureDetector
     */
    private GestureDetector setupGestureDetector(final ShuffleActivity shuffleActivity){
        return new GestureDetector(shuffleActivity, new shuffleOnGestureListener(this,
                mShuffleActivity));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resumeAnimations(){
        progressBarWrapper.restartAnimation();
    }

    /**
     * Launches the SwipeUpActivity
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startSwipeUpActivity()
    {
        progressBarWrapper.stopBarAnimation();

        Intent intent = new Intent(mShuffleActivity, SwipeUpActivity.class);

        startActivity(intent);
    }

    public int getPosition() {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void leftTap(){
        progressBarWrapper.restartAnimation();
        mShuffleActivity.LeftTap();
        nextImage();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void rightTap(){
        progressBarWrapper.restartAnimation();
        mShuffleActivity.RightTap();
        previousImage();
    }

    /**
     * updates imageView with next image to be shown
     */
    public void nextImage(){
        //TODO: set correctly next image to be shown
        imageView.setImageResource(R.drawable.heart);
    }

    /**
     * updates imageView with previous image to be shown
     */
    public void previousImage(){
        //TODO: set correctly previous image to be shown
        imageView.setImageResource(R.drawable.jackets_shuffle);
    }

    public void setButtonHider(ButtonHider buttonHider){
        this.buttonHider = buttonHider;
    }

    public void executeButtonHider(){
        buttonHider.execute(mShuffleActivity, this);
    }

    public void interruptButtonHider() {
        if(buttonHider != null)
            buttonHider.cancel(true);
    }
}
