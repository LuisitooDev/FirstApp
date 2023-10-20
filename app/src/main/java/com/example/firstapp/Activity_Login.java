package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class Activity_Login extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private Button btnLogin, btnRegister;
    private FirebaseAuth mAuth;
    private boolean isAccountDisabled = false; // Variable booleana que simula la inhabilitación de la cuenta

    private CheckBox cbxRecordar;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        cbxRecordar = findViewById(R.id.cbxRecordar);

        txtEmail.setText(sp.getString("intEmail", ""));
        txtPassword.setText(sp.getString("intPassword", "#000000"));
        cbxRecordar.setChecked(sp.getBoolean("intRecordar", false));


        mAuth = FirebaseAuth.getInstance();

        // Verificar si el usuario ya ha iniciado sesión
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            irMain();
        } else {
            Toast.makeText(Activity_Login.this, "Se te ha enviado un correo de verificacion", Toast.LENGTH_SHORT).show();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irRegistro();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
                recordar();
            }
        });


    }

    private void iniciarSesion() {
        String correo = txtEmail.getText().toString().trim();
        String contraseña = txtPassword.getText().toString();

        if (correo.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(Activity_Login.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    if (user.isEmailVerified()) {
                                        if (!isAccountDisabled) {
                                            // Inicio de sesión exitoso y correo verificado, y la cuenta no está inhabilitada
                                            Toast.makeText(Activity_Login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                            irMain();
                                        } else {
                                            // Cuenta inhabilitada, mostrar mensaje apropiado
                                            Toast.makeText(Activity_Login.this, "Cuenta inhabilitada. Contacta al soporte.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Inicio de sesión exitoso, pero el correo no está verificado
                                        Toast.makeText(Activity_Login.this, "Por favor, verifica tu correo electrónico", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // No se pudo obtener el usuario actual
                                    Toast.makeText(Activity_Login.this, "Error en el inicio de sesión: No se pudo obtener el usuario actual", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Error en el inicio de sesión
                                Toast.makeText(Activity_Login.this, "Error en el inicio de sesión: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void irMain() {
        Intent intent = new Intent(Activity_Login.this, MainActivity.class);
        startActivity(intent);
    }

    private void irRegistro() {
        Intent intent = new Intent(Activity_Login.this, Activity_SignUp.class);
        startActivity(intent);
    }

    private void recordar() {
        String email, password;
        boolean remember;

        email = txtEmail.getText().toString();
        password = txtPassword.getText().toString();
        remember = cbxRecordar.isChecked();

        //Almacenar informacion en SharedPreferences
        SharedPreferences.Editor editor = sp.edit();
        if (cbxRecordar.isChecked()) {
            editor.putString("intEmail", email);
            editor.putString("intPassword", password);
            editor.putBoolean("intRemember", remember);
            editor.apply();
        } else {
            editor.clear();
            editor.apply();
        }
    }
}




