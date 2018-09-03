package com.SwipeUp.swipeUpManagement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.SwipeUp.utilities.R;
import com.SwipeUp.utilities.wearingFactory.MiniWearingfactory;
import com.SwipeUp.utilities.wearingFactory.WearingFactoryNew;


public class SwipeUpMenuCustomAdapter extends PagerAdapter{

    private Context ctx;
    private int index;
    private MiniWearingfactory miniWearingfactory;

    public SwipeUpMenuCustomAdapter(Context context,int position,int index){
        this.ctx = context;
        miniWearingfactory= WearingFactoryNew.getInstance().getMiniWearingFactory(position);
        this.index=index;

    }

    @Override
    public int getCount() {
        return 1;
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

            /**
             * Quando faccio zoom-out dalla size minima, si vede la foto che si riduce, ma dietro resta la foto intera
             * questo accade solo con la prima e l'ultima foto, quando sono 3.
             */
            case 0:
                image.setImageDrawable(miniWearingfactory.getImage(index));
                break;

            case 1:
                image.setImageDrawable(miniWearingfactory.getImage(index));
                break;

            case 2:
                image.setImageDrawable(miniWearingfactory.getImage(index));
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
