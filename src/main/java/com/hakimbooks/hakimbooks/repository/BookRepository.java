package com.hakimbooks.hakimbooks.repository;

import com.hakimbooks.hakimbooks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
