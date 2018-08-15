package com.SwipeUp.shuffleManagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.SwipeUp.utilities.R;
import com.SwipeUp.utilities.wearingFactory.WearingFactory;
import com.bumptech.glide.Glide;

public class ShuffleFragment extends Fragment{

    private static final String POSITION_KEY = "position";
    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView textView;
    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shuffle_viewpager_fragment_item, container,false);

        position = this.getArguments().getInt(POSITION_KEY);

        findViews(v);

        WearingFactory wearingFactory = new WearingFactory((ShuffleActivity) this.getActivity());

        switch(position%2){
            case 0:
                Glide
                        .with(v)
                        .load(R.drawable.gigiproietti)
                        .into(imageView);
                break;
            case 1:
                Glide
                        .with(v)
                        .load(R.drawable.pants)
                        .into(imageView);
                break;
        }

        return v;
    }

    /**
     * Private method used to find views and to save them in instance variables
     */
    private void findViews(View v){
        imageView = v.findViewById(R.id.swipe_image);
        textView = v.findViewById(R.id.swipe_friend);
    }

    public static ShuffleFragment newInstance(int position){
        Bundle args = new Bundle();
        args.putInt(POSITION_KEY, position);

        ShuffleFragment shuffleFragment = new ShuffleFragment();
        shuffleFragment.setArguments(args);
        return shuffleFragment;
    }

    public int getPosition() {
        return position;
    }

    /**
     * updates imageView with next image to be shown
     */
    public void nextImage(){
        //TODO: set correctly next image to be shown
        imageView.setImageResource(R.drawable.heart);
    }

    /**
     * updates imageView with previous image to be shown
     */
    public void previousImage(){
        //TODO: set correctly previous image to be shown
        imageView.setImageResource(R.drawable.jackets_shuffle);
    }
}
