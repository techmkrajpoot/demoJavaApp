package com.rajpoot.demoJavaApp.service;

import java.util.List;
import java.util.UUID;

import com.rajpoot.demoJavaApp.entity.User;
import com.rajpoot.demoJavaApp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User create(User user) {
		user.setId(UUID.randomUUID().toString());
		return userRepository.save(user);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(String id) {
		return userRepository.findById(id).orElseThrow(() -> notFound(id));
	}

	public User update(String id, User user) {
		if (userRepository.findById(id).isEmpty()) {
			throw notFound(id);
		}
		user.setId(id);
		return userRepository.save(user);
	}

	public void delete(String id) {
		if (userRepository.findById(id).isEmpty()) {
			throw notFound(id);
		}
		userRepository.deleteById(id);
	}

	private ResponseStatusException notFound(String id) {
		return new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + id + " was not found");
	}
}
