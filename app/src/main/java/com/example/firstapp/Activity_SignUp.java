package com.example.firstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Activity_SignUp extends AppCompatActivity {
    EditText txtName, txtEmail, txtPassword;

    Button btnSubmit, btnAlready;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

       txtName = findViewById(R.id.txtName);
       txtEmail = findViewById(R.id.txtEmail);
       txtPassword = findViewById(R.id.txtPassword);
       btnSubmit = findViewById(R.id.btnSubmit);
       btnAlready = findViewById(R.id.btnAlready);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });

        btnAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irLogin();
            }
        });
    }

    private void registrarUsuario() {
        String nombre = txtName.getText().toString().trim();
        String correo = txtEmail.getText().toString().trim();
        String contrasena = txtPassword.getText().toString();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(Activity_SignUp.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else if (contrasena.length() < 6) {
            Toast.makeText(Activity_SignUp.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Registro exitoso
                                actualizarNombreUsuario(nombre);
                                autenticarUsuario();
                                Toast.makeText(Activity_SignUp.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                limpiarFormulario();

                            } else {
                                // Error en el registro
                                Toast.makeText(Activity_SignUp.this, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void actualizarNombreUsuario(String nombre) {
        FirebaseUser user = mAuth.getCurrentUser(); // Obtén el usuario actual

        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nombre)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Perfil actualizado exitosamente
                                String nuevoNombre = user.getDisplayName();
                                if (nuevoNombre != null) {
                                    // Nuevo nombre contiene el nombre actualizado
                                    Log.d("Registro", "Nombre actualizado exitosamente: " + nuevoNombre);

                                    // Puedes realizar acciones adicionales aquí si es necesario
                                }
                            }
                        }
                    });
        }
    }

    private void limpiarFormulario() {
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    private void irLogin() {
        Intent intent = new Intent(Activity_SignUp.this, Activity_Login.class);
        startActivity(intent);
    }

        private void autenticarUsuario() {
            user = mAuth.getCurrentUser(); // Obtén el usuario actual

            if (user != null) {
                user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Activity_SignUp.this, "Se te envió un correo de autenticación", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }


    }