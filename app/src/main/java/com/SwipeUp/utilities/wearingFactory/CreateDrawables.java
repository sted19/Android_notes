package com.SwipeUp.utilities.wearingFactory;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;

import java.io.IOException;
import java.io.InputStream;

public class CreateDrawables implements Runnable{

    private boolean executed;
    private AssetManager assetManager;
    private Integer position;
    private Drawable[] drawables;
    private Resources resources;
    private boolean failed;

    public CreateDrawables(Integer position, AssetManager assetManager, Resources resources){
        this.assetManager = assetManager;
        this.position = position;
        this.resources = resources;
    }

    @Override
    public void run() {
        if(executed) return;


        try {
            String[] images = assetManager.list(position.toString());
            int availableImages = images.length;
            drawables = new Drawable[availableImages];
            InputStream inputStream;
            for (int i = 0; i < images.length; i++) {
                inputStream = assetManager.open(position+"/" + images[i]);
                Drawable drawable = Drawable.createFromResourceStream(resources, new TypedValue(), inputStream, null);
                drawables[i] = drawable;
            }
        }
        catch (IOException e) {
            Log.e("Wearing factory", "Error in initialization of the images");
            e.printStackTrace();
            failed = true;
        }

        executed = true;
    }

    public boolean getFailed(){
        return  failed;
    }

    public Drawable[] getDrawables(){
        return drawables;
    }

    public boolean getExecuted(){
        return executed;
    }
}
