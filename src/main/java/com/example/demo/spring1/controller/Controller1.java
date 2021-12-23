package com.example.demo.spring1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller1 {
	
	@GetMapping("/message1")
	public String message1() {
		return "Message 1";
	}
}
