package io.rachidassouani.booksocialnetworkapi.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthenticateRequest {
    @NotEmpty(message = "email is required")
    @NotBlank(message = "email is required")
    @Email(message = "email is not well formatted")
    private String email;

    @NotEmpty(message = "password is required")
    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password should be 8 characters minimum")
    private String password;

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