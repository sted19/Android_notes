package com.SwipeUp.mainMenuManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.SwipeUp.shuffleManagement.ShuffleActivity;
import com.SwipeUp.utilities.R;

public class ManMenuFragment extends Fragment {

    private FrameLayout topHits;
    private FrameLayout news;
    private FrameLayout tShirts;
    private FrameLayout pants;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.man_menu_fragment_layout,container,false);

        findUIElements(v);
        return v;
    }

    public void findUIElements(View v){
        topHits = v.findViewById(R.id.top_hits_layout);
        news = v.findViewById(R.id.news_layout);
        tShirts = v.findViewById(R.id.tshirts_layout);
        pants = v.findViewById(R.id.pants_layout);
        topHits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topHitsPressed();
            }
        });
    }

    public void topHitsPressed(){
        Intent intent = new Intent(getActivity(), ShuffleActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_top);
    }
}
