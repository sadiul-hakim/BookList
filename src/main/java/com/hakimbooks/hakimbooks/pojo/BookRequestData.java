package com.hakimbooks.hakimbooks.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookRequestData {
    private String bookName;
    private String writerName;
    private int totalPages;
    private long userId;
    private long categoryId;
}
