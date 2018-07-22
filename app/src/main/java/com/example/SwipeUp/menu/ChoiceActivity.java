package com.example.SwipeUp.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.SwipeUp.swipeUp.FullScreen;
import com.example.SwipeUp.swipeUp.R;

public class ChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Intent intent = getIntent();

        //sets the application in fullscreen
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        FullScreen fullScreen = new FullScreen(decorView);
        Thread onFull = new Thread(fullScreen);
        onFull.start();
    }

    public void loginPressed(View view){
        Toast.makeText(this.getBaseContext(), "Login to start swiping", Toast.LENGTH_SHORT).show();
    }

    public void topHitsPressed(View view){
        Toast.makeText(this.getBaseContext(), "Top hits swiping", Toast.LENGTH_SHORT).show();
    }

    public void newShufflePressed(View view){
        Toast.makeText(this.getBaseContext(), "New swiping", Toast.LENGTH_SHORT).show();
    }
}