package com.SwipeUp.accessManagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.SwipeUp.utilities.R;

public class RegisterFragment extends Fragment{

    private ImageView fbButton;
    private ImageView instagramButton;
    private EditText mail;
    private ImageView rightArrow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment_layout,container,false);

        findUIElements(v);

        return v;
    }

    private void findUIElements(View v){
        fbButton = v.findViewById(R.id.fb_button);
        instagramButton = v.findViewById(R.id.instagram_button);
        mail = v.findViewById(R.id.mail_editText);
        rightArrow = v.findViewById(R.id.right_arrow);
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightArrowClicked(v);
            }
        });
    }

    public void rightArrowClicked(View v){
        String email = mail.getText().toString();
        Toast.makeText(v.getContext(),"Go to register to the app, "+email,Toast.LENGTH_SHORT).show();
    }
}
