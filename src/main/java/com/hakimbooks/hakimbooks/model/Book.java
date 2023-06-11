package com.hakimbooks.hakimbooks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @SequenceGenerator(name = "generator", sequenceName = "book_seq",allocationSize = 1)
    @GeneratedValue(generator = "generator")
    private long id;
    @ManyToOne
    private BookName nameInfo;
    @Column(length = 100)
    private String photo="";
    private int totalPages;
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    private List<ReadBook> readBooks=new ArrayList<>();
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;

}
