package com.SwipeUp.accessManagement;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.SwipeUp.utilities.R;
import com.SwipeUp.utilities.fullScreen.FullScreen;

public class AccessActivity extends AppCompatActivity{

    private FullScreen fullScreen;

    private View accediView;
    private View registratiView;
    private TextView accedi;
    private TextView registrati;

    private int color;

    private FragmentManager fm;
    private Fragment login;
    private Fragment register;

    private boolean in_login = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access_activity_layout);

        fm = getSupportFragmentManager();
        login = fm.findFragmentById(R.id.login_fragment_container);
        register = fm.findFragmentById(R.id.login_fragment_container);

        fullScreen = new FullScreen(getWindow().getDecorView());
        fullScreen.setUIFullScreen();
        fullScreen.fullScreenKeeper();

        login = new LoginFragment();
        register = new RegisterFragment();

        color = getResources().getColor(R.color.pressed_button_light_blue);

        getUIElements();
        registratiClicked(null);

    }

    public void getUIElements(){
        accediView = findViewById(R.id.accedi_view);
        registratiView = findViewById(R.id.registrati_view);
        accedi = findViewById(R.id.accedi);
        registrati = findViewById(R.id.registrati);
    }


    public void accediClicked(View view){
        if(!in_login){
            setAccediUI();

            fm
                    .beginTransaction()
                    .replace(R.id.login_fragment_container, login)
                    .commit();


            in_login = true;
        }
    }

    public void registratiClicked(View view){
        if(in_login) {
            setRegistratiUI();

            fm
                    .beginTransaction()
                    .replace(R.id.login_fragment_container, register)
                    .commit();

            in_login = false;
        }
    }

    private void setAccediUI(){
        accediView.setBackgroundColor(color);
        accedi.setTypeface(null,Typeface.BOLD);
        accedi.setTextColor(color);
        registratiView.setBackgroundColor(Color.LTGRAY);
        registrati.setTypeface(null,Typeface.NORMAL);
        registrati.setTextColor(Color.LTGRAY);
    }

    private void setRegistratiUI(){
        registratiView.setBackgroundColor(color);
        registrati.setTypeface(null,Typeface.BOLD);
        registrati.setTextColor(color);
        accediView.setBackgroundColor(Color.LTGRAY);
        accedi.setTypeface(null,Typeface.NORMAL);
        accedi.setTextColor(Color.LTGRAY);
    }
}
