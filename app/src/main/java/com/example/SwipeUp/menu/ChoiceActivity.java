package com.example.SwipeUp.menu;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.SwipeUp.swipeUp.FullScreen;
import com.example.SwipeUp.swipeUp.R;

public class ChoiceActivity extends AppCompatActivity {

    private Button manButton;
    private Button womanButton;
    private ScrollView shuffles;

    @RequiresApi(api = Build.VERSION_CODES.M)
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

        findElements();

        manButtonPressed(null);
    }

    private void findElements(){
        manButton = findViewById(R.id.man_button);
        womanButton = findViewById(R.id.woman_button);
        shuffles = findViewById(R.id.shuffles);
    }

    public void loginPressed(View view){
        Toast.makeText(this.getBaseContext(), "Login to start swiping", Toast.LENGTH_SHORT).show();
    }

    public void topHitsPressed(View view){
        Toast.makeText(this.getBaseContext(), "Top hits swiping", Toast.LENGTH_SHORT).show();
    }

    public void pantsShufflePressed(View view){
        Toast.makeText(this.getBaseContext(), "Pants swiping", Toast.LENGTH_SHORT).show();
    }

    public void jacketShufflePressed(View view){
        Toast.makeText(this.getBaseContext(), "Jackets swiping", Toast.LENGTH_SHORT).show();
    }

    public void tshirtShufflePressed(View view){
        Toast.makeText(this.getBaseContext(), "T-Shirt swiping", Toast.LENGTH_SHORT).show();
    }

    public void newShufflePressed(View view){
        Toast.makeText(this.getBaseContext(), "New swiping", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void manButtonPressed(View view){

        //change man button
        manButton.setTextColor(getColor(R.color.pressed_button_light_blue));
        manButton.setBackgroundResource(R.drawable.button_man_selected);

        //change woman button
        womanButton.setTextColor(getColor(R.color.light_grey));
        womanButton.setBackgroundResource(R.drawable.button_woman_unselected);

        shuffles.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void womanButtonPressed(View view){

        //change woman button
        womanButton.setTextColor(getColor(R.color.pressed_button_light_blue));
        womanButton.setBackgroundResource(R.drawable.button_woman_selected);

        //change man button
        manButton.setTextColor(getColor(R.color.light_grey));
        manButton.setBackgroundResource(R.drawable.button_man_unselected);

        shuffles.setVisibility(View.INVISIBLE);
    }
}