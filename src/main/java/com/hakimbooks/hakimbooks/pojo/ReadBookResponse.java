package com.hakimbooks.hakimbooks.pojo;

import com.hakimbooks.hakimbooks.model.BookName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReadBookResponse {
    private long id;
    private UserResponse user;
    private long bookId;
    private BookName bookName;
    private int readPages;
    private Timestamp readDate;
}
