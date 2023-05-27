package com.hakimbooks.hakimbooks.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponse {
    private long id;
    private String fullName="";
    private String description="";
    private String email="";
    private String photo="";
    private String startedAt;
    private String role="";
}
