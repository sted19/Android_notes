package com.example.stefano.fabio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private ImageSwitcher switcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        switcher = (ImageSwitcher) findViewById(R.id.switcher);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE );

        FullScreen fullScreen = new FullScreen(decorView);
        Thread onFull = new Thread(fullScreen);
        onFull.start();

        ViewSwitcher.ViewFactory view = new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                image = new ImageView(getApplicationContext());
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return image;
            }
        };
        switcher.setFactory(view);

        switcher.setImageResource(R.drawable.gigiproietti);

        Animation animation = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);

        Button b2 = (Button) findViewById(R.id.button2);
        Button b1 = (Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher.setImageResource(R.drawable.gigiproietti);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Next Image",
                        Toast.LENGTH_LONG).show();
                switcher.setImageResource(R.drawable.gigidag);
            }
        });


    }



}
