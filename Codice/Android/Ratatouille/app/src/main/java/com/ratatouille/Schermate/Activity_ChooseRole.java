package com.ratatouille.Schermate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ratatouille.Controllers.Controller_Login;
import com.ratatouille.R;
public class Activity_ChooseRole extends AppCompatActivity {
    //SYSTEM
    private static final String TAG = "Activity_ChooseRole";

    //LAYOUT
    Button  Button_Amministratore;
    Button  Button_Supervisore;
    Button  Button_Chef;
    Button  Button_Cameriere;

    //FUNCTIONAL

    //OTHER...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        PrepareData();

        PrepareLayout();

    }

    //LAYOUT
    private void PrepareData() {

    }
    private void PrepareLayout() {
        LinkLayout();
        SetDataOnLayout();
        SetActionsOfLayout();
    }

    private void LinkLayout() {
        Button_Amministratore   = findViewById(R.id.button_amministratore);
        Button_Supervisore      = findViewById(R.id.button_supervisore);
        Button_Chef             = findViewById(R.id.button_chef);
        Button_Cameriere        = findViewById(R.id.button_cameriere);
    }
    private void SetDataOnLayout() {

    }

    private void SetActionsOfLayout() {
        Button_Amministratore   .setOnClickListener(view -> startAmministratore());
        Button_Supervisore      .setOnClickListener(view -> startSupervisore());
        Button_Chef             .setOnClickListener(view -> startChef());
        Button_Cameriere        .setOnClickListener(view -> startCameriere());
    }

    private void startAmministratore(){
        Intent intent = new Intent(this, Activity_Amministratore.class);
        startActivity(intent);
    }
    private void startSupervisore(){
        Intent intent = new Intent(this, Activity_Supervisore.class);
        startActivity(intent);
    }
    private void startChef(){
        Intent intent = new Intent(this, Activity_Chef.class);
        startActivity(intent);
    }
    private void startCameriere(){
        Intent intent = new Intent(this, Activity_Cameriere.class);
        startActivity(intent);
    }

}