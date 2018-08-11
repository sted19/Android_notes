package com.SwipeUp.swipeUpManagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.SwipeUp.utilities.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SwipeUpFragment extends Fragment {

    private Spinner sizeSpinner;
    private Spinner colorSpinner;
    private ImageView downArrow;
    private ViewPager viewPager;
    private SwipeUpMenuCustomAdapter adapter;

    private ArrayList<String> sizeArray = new ArrayList<>(
            Arrays.asList("S", "M", "L", "XL"));
    private ArrayList<String> colorArray = new ArrayList<>(
            Arrays.asList("Rosa", "Blu", "Fosforescente"));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.swipeup_fragment_layout, container, false);


        findElements(v);
        setUpSpinners(v);
        setupViewPager(v);

        return v;
    }


    private void setupViewPager(View v){
        viewPager = (ViewPager)v.findViewById(R.id.swipeUp_menu_viewPager);
        adapter = new SwipeUpMenuCustomAdapter(getContext());
        viewPager.setAdapter(adapter);
    }

    private void findElements(View v){
        downArrow = (ImageView)v.findViewById(R.id.downArrow);
        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downArrowPressed();
            }
        });
        sizeSpinner = v.findViewById(R.id.taglia_spinner);
        colorSpinner = v.findViewById(R.id.color_spinner);
    }

    private void setUpSpinners(View v){
        ArrayAdapter<String> adpSize=new ArrayAdapter<String>(v.getContext(), R.layout.simple_spinner_dropdown_item, sizeArray);
        sizeSpinner.setAdapter(adpSize);

        ArrayAdapter<String> adpColor=new ArrayAdapter<String>(v.getContext(), R.layout.simple_spinner_dropdown_item, colorArray);
        colorSpinner.setAdapter(adpColor);
    }

    public void downArrowPressed(){
        getActivity().finish();
    }

}
