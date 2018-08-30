package com.SwipeUp.swipeUpManagement;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.SwipeUp.utilities.Constants;
import com.SwipeUp.utilities.fullScreen.FullScreen;
import com.SwipeUp.utilities.R;

public class SwipeUpActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
        setContentView(R.layout.swipeup_activity_layout);

        int position = getIntent().getIntExtra(Constants.POSITION,-1); //per chiedere alla wearing factory il capo corretto
        int index=getIntent().getIntExtra(Constants.INDEX,-1);

        /**
         * to keep the activity fullScreen
         */
        FullScreen fullScreen = new FullScreen(getWindow().getDecorView());
        fullScreen.setUIFullScreen();
        fullScreen.fullScreenKeeper();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = new SwipeUpFragment();
            fm
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }


    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }



}
