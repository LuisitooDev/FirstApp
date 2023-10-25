package com.example.firstapp;

import static com.example.firstapp.R.id.btnImplicito;
import static com.example.firstapp.R.id.txtHolaMundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //DECLARACION DE VARIABLES GLOBALES
    TextView holamundo, txtNameUser;
    Button btnExplicito;
    Button btnImplicito, btnLogout;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INICIALIZACION DE VARIABLE Y ENLACE A COMPONENTE
        holamundo = findViewById(R.id.txtHolaMundo);
        btnExplicito = findViewById(R.id.btnExplicito);
        btnImplicito = findViewById(R.id.btnImplicito);
        txtNameUser = findViewById(R.id.txtNameUser);

        mAuth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.btnLogout);
        user = mAuth.getCurrentUser();

        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
            startActivity(intent);
            finish();
        }else {
            txtNameUser.setText(user.getUid());
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
                startActivity(intent);
                finish();
            }
        });

        //EVENTO CLICK
        btnExplicito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_2.class);
                startActivity(intent);
                //finish(); Se utiliza para destruir la actividad en contexto
            }
        });

        btnImplicito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Número de teléfono al que deseas llamar
                String numeroTelefono = "6531114430"; // Reemplaza con el número que desees
                // Crea un intent implícito para realizar una llamada
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + numeroTelefono));



                startActivity(intent);
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