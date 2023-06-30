package com.hakimbooks.hakimbooks.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestData {
    @NonNull
    @Size(max = 50)
    @NotEmpty
    private String fullName="";
    @Size(max=250)
    private String description="";
    @NonNull
    @Email
    @Size(max=50)
    @NotEmpty
    private String email="";
    @NonNull
    @Size(min=8,max=25)
    @NotEmpty
    private String password="";
}
