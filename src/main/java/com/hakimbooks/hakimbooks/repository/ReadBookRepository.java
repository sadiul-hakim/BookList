package com.hakimbooks.hakimbooks.repository;

import com.hakimbooks.hakimbooks.model.ReadBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReadBookRepository extends JpaRepository<ReadBook,Long> {
    Optional<List<ReadBook>> findAllByUserId(long userId);
}
