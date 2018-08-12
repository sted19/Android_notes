package com.SwipeUp.shuffleManagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.SwipeUp.utilities.R;
import com.bumptech.glide.Glide;

public class ShuffleFragment extends Fragment{

    private static final String POSITION = "position";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shuffle_viewpager_fragment_item,container,false);

        int position = this.getArguments().getInt(POSITION);

        ImageView image = (ImageView)v.findViewById(R.id.swipe_image);

        switch(position%2){
            case 0:
                Glide
                        .with(v)
                        .load(R.drawable.gigiproietti)
                        .into(image);
                break;
            case 1:
                Glide
                        .with(v)
                        .load(R.drawable.pants)
                        .into(image);
                break;
        }

        return v;
    }

    public static ShuffleFragment newInstance(int position){
        Bundle args = new Bundle();
        args.putInt(POSITION,position);

        ShuffleFragment shuffleFragment = new ShuffleFragment();
        shuffleFragment.setArguments(args);
        return shuffleFragment;
    }
}
