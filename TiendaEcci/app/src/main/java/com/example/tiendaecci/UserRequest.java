package com.example.tiendaecci;

public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;

    public UserRequest(String name, String email, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Getters y setters si son necesarios
}
