package com.hakimbooks.hakimbooks.repository;

import com.hakimbooks.hakimbooks.model.BookName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookNameRepository extends JpaRepository<BookName,Long> {
    @Query(value = "select n from BookName n where n.bookName=:bookName and n.writerName=:writerName")
    Optional<BookName> findByBookNameAndWriter(@Param("bookName") String bookName,@Param("writerName") String writerName);
   List<BookName> findAllByWriterName(String writerName);
}
