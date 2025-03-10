package com.example.tiendaecci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {

    private static final String TAG = "AddPostActivity";
    private EditText etTitle, etContent;
    private Button btnSubmit;
    private int categoryId;
    private int userId; // ID del usuario obtenido de SharedPreferences



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        Log.d(TAG, "onCreate: Actividad iniciada");

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Recibir el ID de la categoría actual desde el intent
        categoryId = getIntent().getIntExtra("categoryId", -1);
        Log.d(TAG, "onCreate: ID de la categoría recibido: " + categoryId);

        // Obtener el ID del usuario desde SharedPreferences como entero
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            Log.e(TAG, "onCreate: No se encontró el ID del usuario en SharedPreferences");
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d(TAG, "onCreate: ID del usuario obtenido: " + userId);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(AddPostActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear y enviar el post
                createPost(title, content,userId,categoryId);
            }
        });
    }

    private void createPost(String titulo, String contenido, int userId, int categoriaId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        PostRequest postRequest = new PostRequest(titulo, contenido, userId, categoriaId);

        // Verificar datos antes de enviar
        Log.d("AddPostActivity", "Datos enviados: " + postRequest.toString());

        apiService.createPost(postRequest).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("AddPostActivity", "Post creado exitosamente");
                    Toast.makeText(AddPostActivity.this, "Post creado exitosamente", Toast.LENGTH_SHORT).show();

                    // Redirigir a la MainActivity
                    Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Finalizar esta actividad
                } else {
                    try {
                        // Leer el cuerpo de error y convertirlo en texto
                        String errorMessage = response.errorBody().string();
                        Log.e("AddPostActivity", "Error al crear el post: " + errorMessage);
                        Toast.makeText(AddPostActivity.this, "Error al crear el post: " + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e("AddPostActivity", "Error al leer el cuerpo de error", e);
                        Toast.makeText(AddPostActivity.this, "Error desconocido", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.e("AddPostActivity", "Error de conexión: ", t);
                Toast.makeText(AddPostActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
