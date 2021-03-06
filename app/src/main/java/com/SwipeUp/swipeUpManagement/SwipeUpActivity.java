package com.SwipeUp.swipeUpManagement;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.SwipeUp.utilities.Constants;
import com.SwipeUp.utilities.fullScreen.FullScreen;
import com.SwipeUp.utilities.R;

public class SwipeUpActivity extends AppCompatActivity {
    private int position;
    private int index;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
        setContentView(R.layout.swipeup_activity_layout);

        retrieveArguments();
        keepFullscreen();

        initializeMainFragment();
    }

    private void retrieveArguments(){
        position = getIntent().getIntExtra(Constants.POSITION,-1); //per chiedere alla wearing factory il capo corretto
        index = getIntent().getIntExtra(Constants.INDEX,-1);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    /**
     * to keep the activity fullScreen
     */
    private void keepFullscreen(){
        FullScreen fullScreen = new FullScreen(getWindow().getDecorView());
        fullScreen.setUIFullScreen();
        fullScreen.fullScreenKeeper();
    }

    private void initializeMainFragment(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = SwipeUpFragment.newInstance(position, index);
            fm
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
