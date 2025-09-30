package com.ecommerce.libreriasaintpatrick.dto.auth;

public class RegisterDto {
    private String email;
    private String password;

    // Constructor vacío
    public RegisterDto() {}

    // Constructor completo
    public RegisterDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

