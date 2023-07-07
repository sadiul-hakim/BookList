package com.hakimbooks.hakimbooks.repository;

import com.hakimbooks.hakimbooks.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
