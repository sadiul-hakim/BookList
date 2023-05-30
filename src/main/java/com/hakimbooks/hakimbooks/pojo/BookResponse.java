package com.hakimbooks.hakimbooks.pojo;

import com.hakimbooks.hakimbooks.model.BookName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookResponse {
    private long id;
    private BookName bookName;
    private String photo="";
    private List<ReadBookResponse> readBookList;
    private UserResponse user;
}
