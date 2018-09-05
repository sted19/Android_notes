package com.SwipeUp.mainMenuManagement;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.SwipeUp.accessManagement.AccessActivity;
import com.SwipeUp.utilities.fullScreen.FullScreen;
import com.SwipeUp.utilities.R;

public class MainMenuActivity extends AppCompatActivity {

    private FullScreen fullScreen;

    private boolean manPressed;
    private int color;

    private FragmentManager fm;
    private Fragment manMenuFragment;
    private Fragment womanMenuFragment;

    private TextView man;
    private TextView woman;
    private View manView;
    private View womanView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting up animations and layout
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.mainmenu_activity_layout);

        initializeFragments();
        keepFullscreen();

        color = getResources().getColor(R.color.pressed_button_light_blue);
        findUIElements();

        manButtonPressed(null);
    }

    private void initializeFragments(){
        fm = getSupportFragmentManager();
        manMenuFragment = fm.findFragmentById(R.id.mainmenu_fragment_container);
        womanMenuFragment = fm.findFragmentById(R.id.mainmenu_fragment_container);
        manMenuFragment = new ManMenuFragment();
        womanMenuFragment = new WomanMenuFragment();
    }

    //keeps the activity in fullscreen
    private void keepFullscreen(){
        fullScreen = new FullScreen(getWindow().getDecorView());
        fullScreen.setUIFullScreen();
        fullScreen.fullScreenKeeper();
    }

    private void findUIElements(){
        man = findViewById(R.id.man_button_text);
        woman = findViewById(R.id.woman_button_text);
        manView = findViewById(R.id.man_view);
        womanView = findViewById(R.id.woman_view);
    }

    public void loginPressed(View view){
        Intent intent = new Intent(this, AccessActivity.class);
        startActivity(intent);
    }

    public void manButtonPressed(View v){
        if(!manPressed){
            setmanUI();

            fm
                    .beginTransaction()
                    .replace(R.id.mainmenu_fragment_container,manMenuFragment)
                    .commit();

            manPressed = true;
        }
    }

    public void womanButtonPressed(View v){
        if(manPressed){
            setWomanUI();

            fm
                    .beginTransaction()
                    .replace(R.id.mainmenu_fragment_container,womanMenuFragment)
                    .commit();

            manPressed = false;
        }
    }

    public void setmanUI(){
        manView.setBackgroundColor(color);
        man.setTypeface(null, Typeface.BOLD);
        man.setTextColor(color);
        womanView.setBackgroundColor(Color.LTGRAY);
        woman.setTypeface(null, Typeface.NORMAL);
        woman.setTextColor(Color.LTGRAY);
    }

    public void setWomanUI(){
        womanView.setBackgroundColor(color);
        woman.setTypeface(null, Typeface.BOLD);
        woman.setTextColor(color);
        manView.setBackgroundColor(Color.LTGRAY);
        man.setTypeface(null, Typeface.NORMAL);
        man.setTextColor(Color.LTGRAY);
    }

}
