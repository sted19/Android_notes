package com.SwipeUp.mainMenuManagement;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.SwipeUp.accessManagement.AccessActivity;
import com.SwipeUp.utilities.fullScreen.FullScreen;
import com.SwipeUp.utilities.R;

public class MainMenuActivity extends AppCompatActivity {

    private FullScreen fullScreen;

    private RelativeLayout manButton;
    private RelativeLayout womanButton;
    private ScrollView shuffles;
    private TextView womanTextView;
    private TextView manTextView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting up animations and layout
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.mainmenu_activity_layout);

        //keeps the activity in fullscreen
        fullScreen = new FullScreen(getWindow().getDecorView());
        fullScreen.setUIFullScreen();
        fullScreen.fullScreenKeeper();

        findElements();

        manButtonPressed(null);
    }

    private void findElements(){
        manButton = findViewById(R.id.man_button);
        womanButton = findViewById(R.id.woman_button);
        shuffles = findViewById(R.id.shuffles);
        manTextView = findViewById(R.id.man_button_text);
        womanTextView = findViewById(R.id.woman_button_text);
    }

    public void loginPressed(View view){
        Intent intent = new Intent(this, AccessActivity.class);
        startActivity(intent);
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
        //Toast.makeText(this.getBaseContext(), "New swiping", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void manButtonPressed(View view){

        //change man button
        manTextView.setTextColor(getColor(R.color.pressed_button_light_blue));
        manButton.setBackgroundResource(R.drawable.menu_button_selected);
        manTextView.setTypeface(null, Typeface.BOLD);

        //change woman button
        womanTextView.setTextColor(getColor(R.color.light_grey));
        womanButton.setBackgroundResource(R.drawable.menu_button_unselected);
        womanTextView.setTypeface(null, Typeface.NORMAL);

        shuffles.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void womanButtonPressed(View view){

        //change woman button
        womanTextView.setTextColor(getColor(R.color.pressed_button_light_blue));
        womanButton.setBackgroundResource(R.drawable.menu_button_selected);
        womanTextView.setTypeface(null, Typeface.BOLD);

        //change man button
        manTextView.setTextColor(getColor(R.color.light_grey));
        manButton.setBackgroundResource(R.drawable.menu_button_unselected);
        manTextView.setTypeface(null, Typeface.NORMAL);

        shuffles.setVisibility(View.INVISIBLE);
    }
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_top);
    }
}
