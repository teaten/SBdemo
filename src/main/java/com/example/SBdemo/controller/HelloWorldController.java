package com.example.SBdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@Value("${greeting}")
	private String greeting;

	// @RequestMapping(value= "/hello",method=RequestMethod.GET)
	@GetMapping("/hello")
	public String index(@RequestParam(value = "id", required = false, defaultValue = "aaa") String id) {
		return id + greeting + "index1";
	}

}
