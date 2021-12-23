package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller1 {
	@GetMapping("/message1")
	public String messageController1() {
		return "Message Controller 1";
	}
}