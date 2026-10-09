package com.rajpoot.demoJavaApp.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageController {

	@GetMapping
	public Map<String, String> getMessage() {
		return Map.of("message", "Hello from the GET APIiiiiiii");
	}

	@PostMapping
	public Map<String, Object> postMessage(@RequestBody Map<String, Object> message) {
		return message;
	}
}
