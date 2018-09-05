package com.SwipeUp.utilities.wearingFactory;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.IOException;

public class MiniWearingFactory {

    private int availableImages;
    private Drawable[] drawables;
    private CreateDrawables createDrawables;

    public MiniWearingFactory(Fragment fragmentCaller, Integer position){
        AssetManager assetManager = fragmentCaller.getActivity().getAssets();
        Resources resources = fragmentCaller.getResources();
        createDrawables = new CreateDrawables(position, assetManager, resources);
        createDrawables.run();

        //TODO mettere il thread con t.start fa fare male l animazione di swipe

        String[] images;
        try {
            images = assetManager.list(position.toString());
            availableImages = images.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getAvailableImages(){
        return availableImages;
    }

    public Drawable getImage(int index){
        if(drawables == null)
            drawables = createDrawables.getDrawables();
        return drawables[index];
    }

}
