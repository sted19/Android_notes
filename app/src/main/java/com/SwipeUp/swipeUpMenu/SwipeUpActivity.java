package com.SwipeUp.swipeUpMenu;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.SwipeUp.swipeUp.FullScreen;
import com.SwipeUp.swipeUp.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SwipeUpActivity extends AppCompatActivity {
    private Spinner sizeSpinner;
    private Spinner colorSpinner;
    private ViewPager viewPager;
    private SwipeUpMenuCustomAdapter adapter;

    private FullScreen fullScreen;

    private ArrayList<String> sizeArray = new ArrayList<>(
            Arrays.asList("S", "M", "L", "XL"));
    private ArrayList<String> colorArray = new ArrayList<>(
            Arrays.asList("Rosa", "Blu", "Fosforescente"));

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
        setContentView(R.layout.swipe_up_menu1);

        //keeps the activity in fullscreen
        fullScreen = new FullScreen(getWindow().getDecorView());
        fullScreen.setUIFullScreen();
        fullScreen.fullScreenKeeper();

        findElements();

        setUpSpinners();

        setupViewPager();

    }


    public void setupViewPager(){
        viewPager = (ViewPager)findViewById(R.id.swipeUp_menu_viewPager);
        adapter = new SwipeUpMenuCustomAdapter(SwipeUpActivity.this);
        viewPager.setAdapter(adapter);


    }

    public void findElements(){
        sizeSpinner = findViewById(R.id.taglia_spinner);
        colorSpinner = findViewById(R.id.color_spinner);
    }

    private void setUpSpinners(){
        ArrayAdapter<String> adpSize=new ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, sizeArray);
        sizeSpinner.setAdapter(adpSize);

        ArrayAdapter<String> adpColor=new ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, colorArray);
        colorSpinner.setAdapter(adpColor);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    public void backButtonPressed(View v){
        finish();
    }
}
