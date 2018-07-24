package com.example.SwipeUp.swipeManagement;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.SwipeUp.swipeUp.R;
import com.example.SwipeUp.wearingFactory.WearingFactory;

import java.util.LinkedList;
import java.util.List;

public class CustomAdapter extends PagerAdapter{

    private int[] images = {R.drawable.like_pressed, R.drawable.like, R.drawable.dislike, R.drawable.dislike_pressed,R.drawable.swiped};
    List<Drawable> drawables = new LinkedList<>();
    private LayoutInflater inflater;
    private Context ctx;
    private WearingFactory wearingFactory;

    public CustomAdapter(Context ctx, WearingFactory wearingFactory){
        this.ctx = ctx;
        this.wearingFactory = wearingFactory;
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

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) ctx.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        View v = inflater.inflate(R.layout.swipe,container,false);

        ImageView image = (ImageView)v.findViewById(R.id.swipe_image);
        Glide
                .with(v)
                .load(drawables.get(position))
                .into(image);
        container.addView(v);
        return v;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }
}
