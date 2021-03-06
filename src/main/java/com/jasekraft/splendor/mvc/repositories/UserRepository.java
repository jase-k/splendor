package com.jasekraft.splendor.mvc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.jasekraft.splendor.mvc.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findAll();
	// Find by username
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
