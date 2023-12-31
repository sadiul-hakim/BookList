package com.hakimbooks.hakimbooks.pojo;

import com.hakimbooks.hakimbooks.model.BookName;
import com.hakimbooks.hakimbooks.model.Category;
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
    private long totalPages;
    private BookName bookName;
    private String photo="";
    private List<ReadBookResponse> readBookList;
    private UserResponse user;
    private Category category;
}
