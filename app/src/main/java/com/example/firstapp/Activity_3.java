package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Activity_3 extends AppCompatActivity {
    EditText txtName, txtAge, txtColor;

    CheckBox checkboxRemember;

    Button btnSend;

    SharedPreferences sp;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        sp = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        txtName = findViewById(R.id.txtNombre);
        txtAge = findViewById(R.id.txtAge);
        txtColor = findViewById(R.id.txtColor);

        checkboxRemember=findViewById(R.id.checkboxRemember);

        btnSend = findViewById(R.id.btnSubmit);
        //
        txtName.setText(sp.getString("intName", ""));
        txtAge.setText(""+sp.getInt("intAge",  0));
        txtColor.setText(sp.getString("intColor", "#000000"));
        checkboxRemember.setChecked(sp.getBoolean("intRemember", true));
        btnSend.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                sendInfo();
            }
        });
    }


    private void sendInfo(){
        String name, color;
        int age;
        boolean remember;

        name = txtName.getText().toString();
        color = txtColor.getText().toString();
        age = Integer.parseInt(txtAge.getText().toString());
        remember = checkboxRemember.isChecked();

        //Almacenar informacion en SharedPreferences
        SharedPreferences.Editor editor = sp.edit();
        if(checkboxRemember.isChecked()){
            editor.putString("intName", name);
            editor.putInt("intAge", age);
            editor.putString("intColor", color);
            editor.putBoolean("intRemember", remember);
            editor.apply();
        }else {
            editor.clear();
            editor.apply();
        }



        Intent intent = new Intent(Activity_3.this, Activity_2.class);
        intent.putExtra("intName", name);
        intent.putExtra("intColor", color);
        intent.putExtra("intAge", age);
        intent.putExtra("intRemember", remember);
        startActivity(intent);
    }
}

