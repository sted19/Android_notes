package com.SwipeUp.utilities.wearingFactory;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;

/**
 *     Given some specific characteristics, an instance of this class returns the Drawable of a wearing that
 *     has that characteristics
 */
public class WearingFactory {


    private Drawable[] drawables;
    private ImageDownloader imageDownloader;
    private int availableImages;
    // sets this variable to false in order to disable connection requests to server and work with
    // available images in assets folder
    private static boolean DEBUG_CONNECTION = false;

    private android.support.v4.app.Fragment fragmentCaller;
    private int producer;

    private static WearingFactory instance;

    public static WearingFactory getInstance(android.support.v4.app.Fragment fragment, int producer){
        if(instance == null){
            instance = new WearingFactory(fragment,producer);
        }
        return instance;
    }


    public WearingFactory(android.support.v4.app.Fragment fragment, int producer) {

        this.fragmentCaller = fragment;
        this.producer = producer;

        if(DEBUG_CONNECTION){
            try {
                imageDownloader = new ImageDownloader(fragmentCaller.getActivity().getResources(), fragmentCaller.getContext());
            } catch (ConnectException e) {
                Toast.
                        makeText(fragmentCaller.getContext(), "No connection available, " +
                                "working locally", Toast.LENGTH_LONG)
                        .show();
            }
        }

        AssetManager assetManager = fragmentCaller.getActivity().getAssets();
        createDrawables(assetManager);

    }

    /**
     * Private method called by the constructor
     */
    private void createDrawables(AssetManager assetManager) {
        try {
            String[] images = assetManager.list("clothes");
            availableImages = images.length;
            drawables = new Drawable[availableImages];
            InputStream inputStream;
            Resources resources = fragmentCaller.getResources();
            for (int i = 0; i < images.length; i++) {
                inputStream = fragmentCaller.getActivity().getAssets().open("clothes/" + images[i]);
                Drawable drawable = Drawable.createFromResourceStream(resources, new TypedValue(), inputStream, null);
                drawables[i] = drawable;
            }
        }
        catch (IOException e) {
            Log.e("Wearing factory", "Error in initialization of the images");
            e.printStackTrace();
        }

        if(DEBUG_CONNECTION && imageDownloader != null) {
            drawables[0] = imageDownloader.downloadRandomImage();
            Log.i("Connection", "Available images fro brand 1: " +
                    imageDownloader.getAvailableImagesForBrand(1));
        }
    }

    /**
     * @return the number of available images in the assets folder
     */
    public int getAvailableImages()
    {
        return availableImages;
    }

    /**
     * @return the next image to be drawn on the screen
     */
    public Drawable getImage(int index)
    {
        return drawables[index];
    }

    /**
     * Method not used yet, called by the neural network
     */
    public void getWearing(int color, int wtype)
    {
        System.out.println("I'm giving you a "+color+" wear");
        System.out.println("I'm giving you a "+wtype);

    }

}