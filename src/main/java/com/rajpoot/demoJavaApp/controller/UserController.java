package com.rajpoot.demoJavaApp.controller;

import java.net.URI;
import java.util.List;

import com.rajpoot.demoJavaApp.entity.User;
import com.rajpoot.demoJavaApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getUsers() {
		return userService.findAll();
	}

	@GetMapping("/{id}")
	public User getUser(@PathVariable String id) {
		return userService.findById(id);
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User created = userService.create(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(created.getId())
				.toUri();
		return ResponseEntity.created(location).body(created);
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable String id, @RequestBody User user) {
		return userService.update(id, user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
