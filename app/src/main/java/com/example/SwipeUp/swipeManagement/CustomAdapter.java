package com.example.SwipeUp.swipeManagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.SwipeUp.swipeUp.MainActivity;
import com.example.SwipeUp.swipeUp.R;
import com.example.SwipeUp.swipeUp.asyncTasks.TapCalculation;
import com.example.SwipeUp.swipeUp.asyncTasks.TapRecognizer;
import com.example.SwipeUp.wearingFactory.WearingFactory;

import java.util.LinkedList;
import java.util.List;


public class CustomAdapter extends PagerAdapter{

    List<Drawable> drawables = new LinkedList<>();
    private LayoutInflater inflater;
    private Context ctx;
    private WearingFactory wearingFactory;

    public ImageView nextImageView;
    public ImageView currentImageView;
    public ImageView previousImageView;

    private MainActivity mainActivity;


    public CustomAdapter(Context ctx, WearingFactory wearingFactory,MainActivity mainActivity){
        this.ctx = ctx;
        this.wearingFactory = wearingFactory;
        this.mainActivity = mainActivity;

        //per creare una lista di drawables tramite la wearing factory
        for(int i = 0;i<wearingFactory.getAvailableImages(); i++){
            drawables.add(wearingFactory.getNextImage());
        }
    }

    @Override
    public int getCount() {
        return wearingFactory.getAvailableImages();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) ctx.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View v = inflater.inflate(R.layout.swipe,container,false);

        ImageView image = (ImageView)v.findViewById(R.id.swipe_image);
        currentImageView = nextImageView;
        nextImageView= image;

        Glide
                .with(v)
                .load(drawables.get(position))
                .into(image);
        container.addView(v, position);
        return v;
    }




    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }
}
