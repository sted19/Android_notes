package com.SwipeUp.shuffleManagement;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.SwipeUp.mainMenuManagement.MainMenuActivity;
import com.SwipeUp.utilities.R;
import com.SwipeUp.utilities.fullScreen.FullScreen;
import com.SwipeUp.utilities.wearingFactory.WearingFactory;

public class ShuffleActivity extends AppCompatActivity {

    private FullScreen fullScreen;
    private CustomViewPager viewPager;
    private ShuffleFragmentAdapter mShuffleAdapter;

    private int displayWidth;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shuffle_activity_layout);

        fullScreen = new FullScreen(getWindow().getDecorView());
        fullScreen.setUIFullScreen();
        fullScreen.fullScreenKeeper();

        findDisplayWidth();

        WearingFactory wearingFactory = WearingFactory.getInstance();
        wearingFactory.resetWearingFactory();

        setupViewPager();


    }


    /**
     * faccio override di onBackPressed per fargli fare la stessa cosa del xButtonPressed nel fragment, ma non dovrebbe funzionare così
     * in generale, perché crea la mainActivity anche se non ci siamo mai passati prima.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainMenuActivity.class);
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setupViewPager() {

        RelativeLayout relativeLayout = findViewById(R.id.viewpager_host_layout);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewPager = new CustomViewPager(getBaseContext());
        viewPager.setLayoutParams(layoutParams);
        viewPager.setId(View.generateViewId());
        relativeLayout.addView(viewPager);

        FragmentManager fm = getSupportFragmentManager();

        mShuffleAdapter = new ShuffleFragmentAdapter(fm);
        viewPager.setAdapter(mShuffleAdapter);
        viewPager.setPageTransformer(true, new CubeTransformer());
        viewPager.setOffscreenPageLimit(5);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        fullScreen.setUIFullScreen();
        mShuffleAdapter.resumeFragment();
    }

    public void findDisplayWidth(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        displayWidth = metrics.widthPixels;
    }

    public int getDisplayWidth(){
        return displayWidth;
    }

    public void triggerLeftSwipe(int position){
        if(position > 0){
            viewPager.setCurrentItem(position-1);
            //viewPager.setDoubleTapBug(true);
            /*
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    //viewPager.setDoubleTapBug(false);
                }
            }, 5);*/
        }
    }

    /**
     * Trigger a right swipe by changing the position of the pager if possible
     * @param position the new position of the pager
     * @return true if page is changed, false otherwise
     */
    public boolean triggerRightSwipe(int position){
        if(position < mShuffleAdapter.getCount() - 1){
            viewPager.setCurrentItem(position+1);
            /*
            viewPager.setDoubleTapBug(true);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    viewPager.setDoubleTapBug(false);
                }
            }, 5);*/ //TODO può funzionare per gestire il doppio tap che non fa fare lo swipe
            return true;
        }
        else{  // all'ultimo swipe si va al menu
            Intent intent = new Intent(getBaseContext(),MainMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return false;
        }
    }

    public void disableScroll(boolean b){
        this.viewPager.disableScroll(b);
    }


}
