package com.example.tiendaecci;

public class PostResponse {
    private String message;
    private int postId;

    // Getters y setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "message='" + message + '\'' +
                ", postId=" + postId +
                '}';
    }
}
