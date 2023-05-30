package com.hakimbooks.hakimbooks.repository;

import com.hakimbooks.hakimbooks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByUserId(long userId);
}
