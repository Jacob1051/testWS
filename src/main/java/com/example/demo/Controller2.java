package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller2 {
	@GetMapping("/message2")
	public String messageController2() {
		return "Message Controller 2";
	}
}
