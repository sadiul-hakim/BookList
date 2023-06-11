package com.hakimbooks.hakimbooks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookName {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "book_seq",allocationSize = 1)
    @GeneratedValue(generator = "generator")
    private long id;
    private String bookName;
    private String writerName;

    public BookName(String bookName, String writerName) {
        this.bookName = bookName;
        this.writerName = writerName;
    }
}
