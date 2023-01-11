package com.ratatouille.Schermate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.ratatouille.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Activity_Login extends AppCompatActivity {
    //SYSTEM
    private static final String TAG = "MainActivity";

    //LAYOUT
    Button Button_Login;
    TextView TextView_Testo;
    //FUNCTIONAL

    //OTHER...
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrepareData();

        PrepareLayout();

        Log.d(TAG, "onCreate: Hai creato la schermata");
    }



    private void PrepareData() {

    }

    private void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    private void LinkLayout() {
        //Button_Login    =       findViewById(R.id.button_login);
        //TextView_Testo  =       findViewById(R.id.text_view_testo);
    }

    private void SetDataOnLayout() {
    }

    private void SetActionsOfLayout() {
        //Button_Login.setOnClickListener(View ->onButtonLogin());
    }

    //BUTTONS ACTIONS
    private void onButtonLogin(){

    }



}