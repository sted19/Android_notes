package com.SwipeUp.accessManagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.SwipeUp.utilities.Constants;
import com.SwipeUp.utilities.R;
import com.SwipeUp.utilities.fullScreen.FullScreen;

public class DataCollectionActivity extends AppCompatActivity{

    private FullScreen fullScreen;

    private EditText name_edit;
    private EditText surname_edit;
    private EditText date_edit;
    private EditText password_edit;

    private String email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_collection_activity_layout);

        fullScreen = new FullScreen(getWindow().getDecorView());
        fullScreen.setUIFullScreen();
        fullScreen.fullScreenKeeper();

        findUIElements();

        email = getIntent().getStringExtra(Constants.EMAIL);


    }

    private void findUIElements(){
        name_edit = findViewById(R.id.name_editText);
        surname_edit = findViewById(R.id.surname_editText);
        date_edit = findViewById(R.id.date_editText);
        password_edit = findViewById(R.id.password_editText);
    }
}
