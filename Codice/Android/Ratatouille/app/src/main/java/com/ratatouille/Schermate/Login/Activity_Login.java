package com.ratatouille.Schermate.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratatouille.Managers.Manager_LoginFragments;
import com.ratatouille.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Activity_Login extends AppCompatActivity {
    //SYSTEM
    private static final String TAG = "MainActivity";

    //LAYOUT
    View        Fragment_View;
    Button      Button_Login;

    //FUNCTIONAL
    private Manager_LoginFragments Manager_Login;

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

        Fragment_View = findViewById(R.id.fragment_container_view_login);
        ArrayList<View> Views = new ArrayList<>();

        Views.add(Fragment_View);
        Manager_Login = new Manager_LoginFragments(this,
                findViewById(R.id.linear_layout_container_fragment),
                getSupportFragmentManager(),
                R.id.fragment_container_view_login,
                Views);
        Manager_Login.showPage(0,0);
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