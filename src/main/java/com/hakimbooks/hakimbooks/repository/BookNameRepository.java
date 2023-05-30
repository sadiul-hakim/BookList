package com.hakimbooks.hakimbooks.repository;

import com.hakimbooks.hakimbooks.model.BookName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookNameRepository extends JpaRepository<BookName,Long> {
    Optional<BookName> findByBookName(String bookName);
    Optional<List<BookName>> findAllByWriterName(String writerName);
}
