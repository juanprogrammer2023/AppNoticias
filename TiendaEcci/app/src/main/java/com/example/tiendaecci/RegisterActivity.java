package com.example.tiendaecci;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etEmail, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Configurar el botón para regresar
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finaliza esta actividad y regresa a la anterior
                finish();
            }
        });

        // Enlaces a los campos del layout
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        // Configurar el botón para enviar el formulario
        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // Validar datos antes de enviar
                if (validateInputs(username, email, password, confirmPassword)) {
                    registerUser(username, email, password, confirmPassword);
                }
            }
        });
    }

    // Método para validar los campos de entrada
    private boolean validateInputs(String username, String email, String password, String confirmPassword) {
        if (username.isEmpty()) {
            Toast.makeText(this, "El nombre de usuario es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Introduce un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty() || password.length() < 10) {
            Toast.makeText(this, "La contraseña debe tener al menos 10 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Método para registrar al usuario
    private void registerUser(String username, String email, String password, String confirmPassword) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        UserRequest userRequest = new UserRequest(username, email, password, confirmPassword);

        apiService.registerUser(userRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    finish(); // Regresa a la actividad anterior
                } else {
                    Toast.makeText(RegisterActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("RegisterActivity", "Error de conexión", t);
                Toast.makeText(RegisterActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
