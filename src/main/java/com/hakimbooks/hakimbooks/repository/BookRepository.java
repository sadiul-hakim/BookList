package com.hakimbooks.hakimbooks.repository;

import com.hakimbooks.hakimbooks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByUserId(long userId);
    @Query(value="select * from book where category_id=?",nativeQuery = true)
    List<Book> findAllByCategoryId(@Param("category") long category);
}
