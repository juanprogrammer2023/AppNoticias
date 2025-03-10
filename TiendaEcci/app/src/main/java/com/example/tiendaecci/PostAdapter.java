package com.example.tiendaecci;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final List<Post> postList;
    private final Context context;

    public PostAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.postTitle.setText(post.getTitulo());
        holder.postAuthor.setText(post.getUsuarioNombre());
        holder.postContent.setText(post.getContenido());

        if (holder.commentsRecyclerView.getLayoutManager() == null) {
            holder.commentsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        holder.commentsRecyclerView.setAdapter(new CommentAdapter(Collections.emptyList(), context));

        loadComments(post.getId(), holder.commentsRecyclerView);

        // Configurar botón de enviar comentario
        holder.sendCommentButton.setOnClickListener(v -> {
            String commentText = holder.commentInput.getText().toString().trim();

            if (commentText.isEmpty()) {
                Toast.makeText(context, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener el ID del usuario desde SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("userId", -1);

            if (userId == -1) {
                Toast.makeText(context, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear el comentario y enviarlo
            Comment newComment = new Comment();
            newComment.setPostId(post.getId());
            newComment.setUserId(userId);
            newComment.setCommentText(commentText);

            sendComment(newComment, holder.commentInput, holder.commentsRecyclerView);
        });
    }

    private void sendComment(Comment comment, EditText commentInput, RecyclerView recyclerView) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        apiService.createComment(comment).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Comentario agregado", Toast.LENGTH_SHORT).show();
                    commentInput.setText(""); // Limpiar el campo de texto
                    loadComments(comment.getPostId(), recyclerView); // Recargar comentarios
                } else {
                    Toast.makeText(context, "Error al agregar comentario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0;
    }

    // Método para cargar comentarios de un post específico
    private void loadComments(int postId, RecyclerView recyclerView) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        apiService.getCommentsByPostId(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Comment> comments = response.body();
                    recyclerView.setAdapter(new CommentAdapter(comments, context));
                } else {
                    Log.d("PostAdapter", "No hay comentarios para el post ID: " + postId);
                    recyclerView.setAdapter(new CommentAdapter(Collections.emptyList(), context)); // Adaptador vacío
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("PostAdapter", "Error al cargar comentarios: " + t.getMessage());
                recyclerView.setAdapter(new CommentAdapter(Collections.emptyList(), context)); // Adaptador vacío
            }
        });
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView postTitle, postAuthor, postContent;
        ImageButton likeButton, commentButton, sendCommentButton;
        RecyclerView commentsRecyclerView;
        EditText commentInput;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            // Referencias a los elementos del layout
            postTitle = itemView.findViewById(R.id.postTitle);
            postAuthor = itemView.findViewById(R.id.postAuthor);
            postContent = itemView.findViewById(R.id.postContent);
            likeButton = itemView.findViewById(R.id.likeButton);
            commentButton = itemView.findViewById(R.id.commentButton);
            commentsRecyclerView = itemView.findViewById(R.id.commentsRecyclerView);
            commentInput = itemView.findViewById(R.id.commentInput);
            sendCommentButton = itemView.findViewById(R.id.sendCommentButton);
        }
    }
}
