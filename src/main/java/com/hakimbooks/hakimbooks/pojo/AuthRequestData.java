package com.hakimbooks.hakimbooks.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestData {
    @NonNull
    @Email
    @Size(max=50)
    @NotEmpty
    private String email;
    @NonNull
    @Size(min=8,max=25)
    @NotEmpty
    private String password;
}
