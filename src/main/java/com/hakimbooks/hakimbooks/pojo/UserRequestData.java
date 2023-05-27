package com.hakimbooks.hakimbooks.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestData {
    private long id;
    private String fullName="";
    private String description="";
    private String email="";
    private String password="";
}
