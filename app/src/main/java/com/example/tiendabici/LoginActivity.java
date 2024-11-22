package com.example.tiendabici;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    // Declaración de variables para los elementos del diseño
    private EditText etEmail, etPassword;
    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Asociamos el diseño XML

        // Inicialización de referencias a los elementos del layout
        etEmail = findViewById(R.id.etEmail); // Verifica que el ID coincida en activity_login.xml
        etPassword = findViewById(R.id.etPasword); // Corrección: etPassword, no etPasword
        btnLogin = findViewById(R.id.btnLogin); // Botón de inicio de sesión

        // Configuración del listener del botón
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim(); // Captura el email ingresado
                String password = etPassword.getText().toString().trim(); // Captura la contraseña ingresada

                // Validación de campos vacíos
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Verificación de credenciales (solo para ejemplo, no es seguro para producción)
                    if (email.equals("admin") && password.equals("admin")) {
                        // Si las credenciales son válidas, redirige a HomeActivity
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); // Finaliza la actividad actual para evitar regresar al login
                    } else {
                        // Muestra un mensaje si las credenciales son incorrectas
                        Toast.makeText(LoginActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
