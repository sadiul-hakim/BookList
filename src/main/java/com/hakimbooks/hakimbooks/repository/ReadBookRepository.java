package com.hakimbooks.hakimbooks.repository;

import com.hakimbooks.hakimbooks.model.ReadBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadBookRepository extends JpaRepository<ReadBook,Long> {
}
