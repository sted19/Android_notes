package com.SwipeUp.swipeUpMenu;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.SwipeUp.swipeUp.R;



public class SwipeUpMenuCustomAdapter extends PagerAdapter{

    private Context ctx;

    public SwipeUpMenuCustomAdapter(Context context){
        this.ctx = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        assert inflater != null;
        View v = inflater.inflate(R.layout.swipeup_viewpager_item,container,false);

        final TouchImageView image = (TouchImageView) v.findViewById(R.id.swipeUp_menu_swipe_image);

        switch(position){
            case 0:
                /**
                 * Quando faccio zoom-out dalla size minima, si vede la foto che si riduce, ma dietro resta la foto intera
                 * questo accade solo con gigi e con il cuore, con la foto dei pantaloni va bene invece.
                 */
                image.setImageResource(R.drawable.gigiproietti);
                break;
            case 1:
                image.setImageResource(R.drawable.pants);
                break;
            case 2:
                image.setImageResource(R.drawable.green_heart);
                break;
        }


        container.addView(v,position);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }

}
