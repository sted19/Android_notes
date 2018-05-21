package com.example.SwipeUp.wearingFactory;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;

import com.example.SwipeUp.swipeUp.MainActivity;

import java.io.IOException;
import java.io.InputStream;

public class WearingFactory {
    //given some specific characteristics, an instance of this class returns a wearing that has that characteristics
    private MainActivity mainActivity;
    private int position;
    private Drawable[] drawables;
    private int availableImages;

    public WearingFactory(MainActivity mainActivity)
    {
        this.mainActivity=mainActivity;
        position = -1;

        //getting images from assets/clothes folder
        AssetManager assetManager = mainActivity.getAssets();
        try {
            String [] images = assetManager.list("clothes");
            availableImages = images.length;
            Resources resources = mainActivity.getResources();
            drawables = new Drawable[availableImages];
            InputStream inputStream;
            for (int i = 0; i < images.length; i++) {
                inputStream = mainActivity.getAssets().open("clothes/" + images[i]);
                Drawable drawable =  Drawable.createFromResourceStream(resources, new TypedValue(),inputStream,null);
                drawables[i] = drawable;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Drawable getNextImage()
    {
        position=(position + 1)%availableImages;
        return drawables[position];
    }

    public Drawable getPreviousImage()
    {
        position=(position -1);
        if(position<0)
            position+=availableImages;
        return drawables[position];
    }

    public void getWearing(int color, int wtype)
    {
        //neuralNetwork.NeuralNetwork.Color.;
        System.out.println("I'm giving you a "+color+" wear");
        System.out.println("I'm giving you a "+wtype);
        
    }
}
