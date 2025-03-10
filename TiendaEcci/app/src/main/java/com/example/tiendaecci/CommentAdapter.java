package com.example.tiendaecci;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final List<Comment> commentList;
    private final Context context;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);

        holder.commentAuthor.setText(comment.getAuthor());
        holder.commentContent.setText(comment.getCommentText());

        // Configurar botón de eliminar comentario
        holder.btnDeleteComment.setOnClickListener(v -> {
            deleteComment(comment.getId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return commentList != null ? commentList.size() : 0;
    }

    // Método para eliminar un comentario
    private void deleteComment(int commentId, int position) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        apiService.deleteComment(commentId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Eliminar el comentario de la lista y notificar el cambio
                    commentList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Comentario eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al eliminar comentario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("CommentAdapter", "Error al eliminar comentario: " + t.getMessage());
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentAuthor, commentContent;
        ImageButton btnDeleteComment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentAuthor = itemView.findViewById(R.id.commentAuthor);
            commentContent = itemView.findViewById(R.id.commentContent);
            btnDeleteComment = itemView.findViewById(R.id.btnDeleteComment); // Referencia al botón
        }
    }
}
