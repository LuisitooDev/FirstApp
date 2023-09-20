package com.example.firstapp;

import static com.example.firstapp.R.id.txtHolaMundo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //DECLARACION DE VARIABLES GLOBALES
    TextView holamundo;
    Button btnExplicito;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INICIALIZACION DE VARIABLE Y ENLACE A COMPONENTE
        holamundo = findViewById(R.id.txtHolaMundo);
        btnExplicito = findViewById(R.id.btnExplicito);

        //EVENTO CLICK
        btnExplicito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_2.class);
                startActivity(intent);
                //finish(); Se utiliza para destruir la actividad en contexto
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("hola mundo", "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        holamundo.setText("Hola mundo desde la clase java en el metodo onResume");
        Log.d("hola mundo", "onResume");



    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("hola mundo", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("hola mundo", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("hola mundo", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("hola mundo", "onRestart");
    }
}