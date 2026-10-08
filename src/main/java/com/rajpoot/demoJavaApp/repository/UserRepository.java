package com.rajpoot.demoJavaApp.repository;

import java.util.List;
import java.util.Optional;

import com.rajpoot.demoJavaApp.entity.User;

public interface UserRepository {
	User save(User user);

	List<User> findAll();

	Optional<User> findById(String id);

	void deleteById(String id);
}
