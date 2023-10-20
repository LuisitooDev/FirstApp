package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_2 extends AppCompatActivity {
    TextView txtNombre, txtEdad, txtColor;

    ConstraintLayout layoutColor;

    CheckBox checkboxRecordar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        txtNombre = findViewById(R.id.txtNombre);
        txtEdad = findViewById(R.id.txtEdad);
        txtColor = findViewById(R.id.txtColor);
        layoutColor = findViewById(R.id.layoutColor);
        checkboxRecordar = findViewById(R.id.checkboxRecordar);

        Intent intent = getIntent();

        txtNombre.setText(intent.getStringExtra("intName"));
        txtEdad.setText(String.valueOf(intent.getIntExtra("intAge", 0))); // Convierte int a String
        txtColor.setText(intent.getStringExtra("intColor"));
        checkboxRecordar.setChecked(intent.getBooleanExtra("intRemember", false));

        if (intent.getStringExtra("intColor").contains("#")){
            try {
                layoutColor.setBackgroundColor((Color.parseColor((intent.getStringExtra("intColor")))));
            }catch(Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else{                 layoutColor.setBackgroundColor((Color.parseColor((intent.getStringExtra("intColor")))));
                }


        String name = intent.getStringExtra("intName");
        int age = intent.getIntExtra("intAge", 0);

        Toast.makeText(this, "name" + name + "tu edad es" + age, Toast.LENGTH_SHORT).show();

    }
}