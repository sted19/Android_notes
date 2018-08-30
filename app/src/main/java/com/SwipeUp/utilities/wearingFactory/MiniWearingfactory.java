package com.SwipeUp.utilities.wearingFactory;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.IOException;

public class MiniWearingfactory {

    private int availableImages;
    private Drawable[] drawables;
    private CreateDrawables createDrawables;

    public MiniWearingfactory(Fragment fragmentCaller, Integer position){
        AssetManager assetManager = fragmentCaller.getActivity().getAssets();
        Resources resources = fragmentCaller.getResources();
        createDrawables = new CreateDrawables(position,assetManager,resources);
        createDrawables.run();
        String[] images = null;
        try {
            images = assetManager.list(position.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        availableImages = images.length;
        Log.e("AVAILABLE_IMAGES",availableImages+"");
    }

    public int getAvailableImages(){
        return availableImages;
    }

    public Drawable getImage(int index){
        if(drawables == null){
            while(!createDrawables.getExecuted()){
                if(createDrawables.getFailed()) break;
                try {
                    wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            drawables = createDrawables.getDrawables();
        }
        return drawables[index];
    }


}
