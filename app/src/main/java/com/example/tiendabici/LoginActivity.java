package com.example.tiendabici;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Obtener referencias a los elementos del diseño
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPasword);
        btnLogin = findViewById(R.id.btnLogin);

        // Configurar el botón de inicio de sesión
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Verificar que los campos no estén vacíos
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Lógica de autenticación (en este ejemplo, se usa un email y password fijos)
                    if (email.equals("admin") && password.equals("admin")) {
                        // Si las credenciales son correctas, ir a la pantalla principal
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); // Cerrar la actividad de login
                    } else {
                        // Si las credenciales son incorrectas, mostrar un mensaje de error
                        Toast.makeText(LoginActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
