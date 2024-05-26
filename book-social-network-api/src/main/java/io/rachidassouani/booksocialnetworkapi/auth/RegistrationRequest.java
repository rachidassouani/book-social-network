package io.rachidassouani.booksocialnetworkapi.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {

    @NotEmpty(message = "firstName is required")
    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotEmpty(message = "lastName is required")
    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotEmpty(message = "email is required")
    @NotBlank(message = "email is required")
    @Email(message = "email is not well formatted")
    private String email;

    @NotEmpty(message = "password is required")
    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password should be 8 characters minimum")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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
