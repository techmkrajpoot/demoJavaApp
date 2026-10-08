package com.rajpoot.demoJavaApp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.rajpoot.demoJavaApp.entity.User;
import com.rajpoot.demoJavaApp.repository.UserRepository;
import com.rajpoot.demoJavaApp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class UserServiceTests {
	private UserRepository userRepository;
	private UserService userService;

	@BeforeEach
	void setUp() {
		userRepository = mock(UserRepository.class);
		userService = new UserService(userRepository);
	}

	@Test
	void createAssignsIdAndSavesUser() {
		User input = new User(null, "Alex", "alex@example.com");
		when(userRepository.save(input)).thenAnswer(invocation -> invocation.getArgument(0));

		User created = userService.create(input);

		assertEquals("Alex", created.getName());
		assertEquals("alex@example.com", created.getEmail());
		org.junit.jupiter.api.Assertions.assertNotNull(created.getId());
		verify(userRepository).save(input);
	}

	@Test
	void updateChangesNameAndEmailWithoutChangingId() {
		User existing = new User("user-1", "Alex", "alex@example.com");
		User changes = new User(null, "Sam", "sam@example.com");
		when(userRepository.findById("user-1")).thenReturn(Optional.of(existing));
		when(userRepository.save(changes)).thenAnswer(invocation -> invocation.getArgument(0));

		User updated = userService.update("user-1", changes);

		assertEquals("user-1", updated.getId());
		assertEquals("Sam", updated.getName());
		assertEquals("sam@example.com", updated.getEmail());
		verify(userRepository).save(changes);
	}

	@Test
	void updateRejectsUnknownUser() {
		when(userRepository.findById("missing")).thenReturn(Optional.empty());

		assertThrows(ResponseStatusException.class,
				() -> userService.update("missing", new User(null, "Sam", "sam@example.com")));
	}
}
