package com.example.tiendaecci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etLoginEmail, etLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Inicializar vistas
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);

        // Configurar evento para redirigir a RegisterActivity
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Configurar evento para el botón de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginEmail.getText().toString().trim();
                String password = etLoginPassword.getText().toString().trim();

                // Validar los campos antes de realizar la llamada
                if (validateInputs(email, password)) {
                    loginUser(email, password);
                }
            }
        });
    }

    // Validar los campos de entrada
    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Introduce un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "La contraseña es obligatoria", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Método para iniciar sesión
    private void loginUser(String email, String password) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        LoginRequest loginRequest = new LoginRequest(email, password);

        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    // Logs para verificar el contenido
                    Log.d("LoginResponse", "Contenido de loginResponse:");
                    Log.d("LoginResponse", "userId: " + loginResponse.getUserId());
                    Log.d("LoginResponse", "username: " + loginResponse.getUsername());
                    Log.d("LoginResponse", "email: " + loginResponse.getEmail());
                    Log.d("LoginResponse", "token: " + loginResponse.getToken());

                    // Guardar datos en SharedPreferences
                    saveUserData(loginResponse);

                    // Redirigir al usuario a MainActivity
                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LoginActivity", "Error de conexión", t);
                Toast.makeText(LoginActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Guardar datos en SharedPreferences
    private void saveUserData(LoginResponse loginResponse) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Verificar que userId no sea un valor inválido (como 0)
        if (loginResponse.getUserId() > 0) {
            editor.putInt("userId", loginResponse.getUserId());
            Log.d("LoginActivity", "Guardando userId: " + loginResponse.getUserId());
        } else {
            Log.e("LoginActivity", "userId es inválido (<= 0), no se guardará");
        }

        // Guardar los otros datos como cadenas
        if (loginResponse.getUsername() != null) {
            editor.putString("username", loginResponse.getUsername());
            Log.d("LoginActivity", "Guardando username: " + loginResponse.getUsername());
        } else {
            Log.e("LoginActivity", "username es nulo, no se guardará");
        }

        if (loginResponse.getEmail() != null) {
            editor.putString("email", loginResponse.getEmail());
            Log.d("LoginActivity", "Guardando email: " + loginResponse.getEmail());
        } else {
            Log.e("LoginActivity", "email es nulo, no se guardará");
        }

        editor.apply();
        Log.d("LoginActivity", "Datos guardados exitosamente en SharedPreferences");
    }




}
