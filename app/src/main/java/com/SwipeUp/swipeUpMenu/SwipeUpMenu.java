package com.SwipeUp.swipeUpMenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.SwipeUp.swipeUp.R;

public class SwipeUpMenu extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
        setContentView(R.layout.swipe_up_menu);

    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }
}
