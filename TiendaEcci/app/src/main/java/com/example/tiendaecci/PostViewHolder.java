package com.example.tiendaecci;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private EditText commentInput;
    private ImageButton sendCommentButton;
    private RecyclerView commentsRecyclerView;

    public PostViewHolder(View itemView) {
        super(itemView);

        // Referencias de las vistas
        commentInput = itemView.findViewById(R.id.commentInput);
        sendCommentButton = itemView.findViewById(R.id.sendCommentButton);
        commentsRecyclerView = itemView.findViewById(R.id.commentsRecyclerView);

        // Configurar el botón de enviar comentario
        sendCommentButton.setOnClickListener(v -> {
            String commentText = commentInput.getText().toString().trim();

            if (commentText.isEmpty()) {
                Toast.makeText(itemView.getContext(), "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simular datos del usuario y post_id (reemplaza con los valores reales)
            int postId = getAdapterPosition(); // Esto puede cambiar según tu lógica
            int userId = 1; // Cambiar por el ID del usuario autenticado

            // Crear el objeto de comentario
            Comment newComment = new Comment();
            newComment.setPostId(postId);
            newComment.setUserId(userId);
            newComment.setCommentText(commentText);

            // Llamar al método para enviar el comentario al servidor
            sendCommentToServer(newComment, itemView);
        });
    }

    private void sendCommentToServer(Comment comment, View itemView) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        apiService.createComment(comment).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()) {
                    // Éxito al enviar el comentario
                    Toast.makeText(itemView.getContext(), "Comentario agregado", Toast.LENGTH_SHORT).show();

                    // Actualizar el RecyclerView de comentarios
                    commentInput.setText(""); // Limpiar el campo de texto
                    loadComments();
                } else {
                    Toast.makeText(itemView.getContext(), "Error al agregar comentario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(itemView.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComments() {
        // Método para recargar los comentarios después de agregar uno nuevo
        // Aquí puedes implementar la lógica para actualizar el RecyclerView de comentarios
    }
}
