package com.SwipeUp.firstPageManagement;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.SwipeUp.shuffleManagement.ShuffleActivity;
import com.SwipeUp.utilities.Constants;
import com.SwipeUp.utilities.R;
import com.SwipeUp.utilities.fullScreen.FullScreen;
import com.bumptech.glide.Glide;

public class FirstPageActivity extends AppCompatActivity{

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page_activity_layout);

        FullScreen fullScreen = new FullScreen(getWindow().getDecorView());
        fullScreen.setUIFullScreen();
        fullScreen.fullScreenKeeper();

    }

    public void firstScreenManButtonPressed(View v){
        Intent intent = new Intent(getBaseContext(), ShuffleActivity.class);
        intent.putExtra(Constants.GENDER,"MAN");
        startActivity(intent);
        finish();
    }

    public void firstScreenWomanButtonPressed(View v){
        Intent intent = new Intent(getBaseContext(), ShuffleActivity.class);
        intent.putExtra(Constants.GENDER,"WOMAN");
        startActivity(intent);
        finish();
    }
}
