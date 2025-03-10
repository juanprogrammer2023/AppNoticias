package com.example.tiendaecci;

import com.google.gson.annotations.SerializedName;

public class Comment {
    private int id;

    @SerializedName("post_id")
    private int postId;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("comment_text")
    private String commentText;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("username")
    private String author;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
