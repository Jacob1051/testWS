package com.example.demo.spring2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping("/message2")
	public String message2() {
		return "Message 2";
	}
}
