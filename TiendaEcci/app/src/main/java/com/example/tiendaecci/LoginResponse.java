package com.example.tiendaecci;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private int userId;
    private String username;
    private String email;
    private String token;

    // Getters y setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
