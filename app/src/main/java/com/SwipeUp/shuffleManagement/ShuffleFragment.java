package com.SwipeUp.shuffleManagement;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.SwipeUp.shuffleManagement.shuffleListeners.shuffleOnGestureListener;
import com.SwipeUp.swipeUpManagement.SwipeUpActivity;
import com.SwipeUp.utilities.R;
import com.SwipeUp.utilities.asyncTasks.ButtonHider;
import com.SwipeUp.utilities.asyncTasks.ShowLogo;
import com.SwipeUp.utilities.progressBar.ProgressBarWrapper;
import com.SwipeUp.utilities.wearingFactory.WearingFactory;
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
    private WearingFactory mWearingFactory;

    private ButtonHider buttonHider;

    private int availableImages;
    private int imageIndex = 0;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shuffle_viewpager_fragment_item, container,false);
        findViews(v);
        mShuffleActivity.setActualFragment(this);
        setUpListeners(v);

        position = this.getArguments().getInt(POSITION_KEY);

        mWearingFactory = WearingFactory.getInstanceOf(mShuffleActivity);

        instantiateProgressBars(v);

        Glide
                .with(v)
                .load(mWearingFactory.getNextImage())
                .into(imageView);

        return v;
    }

    /**
     * Private method used to find views and to save them in instance variables
     */
    private void findViews(View v){
        mShuffleActivity = (ShuffleActivity) getActivity();
        imageView = v.findViewById(R.id.swipe_image);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStop(){
        super.onStop();
        progressBarWrapper.restartAnimation();
        progressBarWrapper.stopBarAnimation();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        progressBarWrapper.restartAnimation();
    }

    /**
     * Private method called on initialization, sets the gestureDetector
     */
    private GestureDetector setupGestureDetector(final ShuffleActivity shuffleActivity){
        return new GestureDetector(shuffleActivity, new shuffleOnGestureListener(this,
                mShuffleActivity));
    }

    /**
     * Private method called by onCreate to instantiate the progress bars
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void instantiateProgressBars(View v){

        this.availableImages = mWearingFactory.getAvailableImages();

        ProgressBar[] vectProgressBar = new ProgressBar[availableImages];

        for(int i=0; i<availableImages; i++){

            LinearLayout linearLayout = v.findViewById(R.id.space_for_progress_bars);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    mShuffleActivity.DisplayWidth / availableImages, 6);

            layoutParams.setMargins(3, 15 ,3, 5);

            ProgressBar progressBar=new ProgressBar(mShuffleActivity.getApplicationContext(),
                    null, android.R.attr.progressBarStyleHorizontal);
            progressBar.setLayoutParams(layoutParams);

            progressBar.setBackgroundColor(Color.GRAY); // TODO change background color
            progressBar.getProgressDrawable().setColorFilter(Color.WHITE,android.graphics.PorterDuff.Mode.SRC_IN);

            vectProgressBar[i]=progressBar;
            linearLayout.addView(progressBar);
        }

        this.progressBarWrapper=new ProgressBarWrapper(vectProgressBar, this);

        if(first){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && progressBarWrapper!=null) {
                progressBarWrapper.resumeBarAnimation();
            }
            mShuffleActivity.setFirstFragment(this);
            first = false;
        }
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void leftTap(){
        Log.i("imageIndex", "is "+imageIndex);
        if(imageIndex == 0)
            triggerLeftSwipe();
        else {
            progressBarWrapper.startPrevAnimation();
            mShuffleActivity.LeftTap();
            previousImage();
            imageIndex--;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void rightTap(){
        Log.i("imageIndex", "is "+imageIndex);
        if(imageIndex == availableImages - 1)
            triggerRightSwipe();
        else {
            progressBarWrapper.startNextAnimation();
            mShuffleActivity.RightTap();
            nextImage();
            imageIndex++;
        }
    }

    /**
     * updates imageView with next image to be shown
     */
    public void nextImage(){
        View v = getView();
        if(v == null) return;
        Glide
                .with(v)
                .load(mWearingFactory.getNextImage())
                .into(imageView);
    }

    /**
     * updates imageView with previous image to be shown
     */
    public void previousImage(){
        View v = getView();
        if(v == null) return;
        Glide
                .with(v)
                .load(mWearingFactory.getPreviousImage())
                .into(imageView);
    }

    /**
     * Setter fot the buttonHider field
     */
    public void setButtonHider(ButtonHider buttonHider){
        this.buttonHider = buttonHider;
    }

    /**
     * Starts the buttonHider (does not check if it is null)
     */
    public void executeButtonHider(){
        buttonHider.execute(mShuffleActivity, this);
    }

    /**
     * Interrupts the buttonHider (checking if it is null)
     */
    public void interruptButtonHider() {
        if(buttonHider != null)
            buttonHider.cancel(true);
    }

    /**
     * Method called when all the available images for the current fragment have been shown
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void triggerRightSwipe(){
        if(mShuffleActivity.setPagerPosition(position + 1))
            progressBarWrapper.stopBarAnimation();
    }

    /**
     * Method called on left tap at the first image
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void triggerLeftSwipe(){
        if(mShuffleActivity.setPagerPosition(position - 1))
            progressBarWrapper.stopBarAnimation();
    }
//
//    /**
//     * Starts a timer to show central swipe up logo
//     */
//    public void showLogo(){
//        logoShower = new ShowLogo(this);
//        logoShower.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//    }
}
