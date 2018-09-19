package com.example.SBdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SBdemo.model.Student;

@RestController
public class HelloWorldController {
	
	@Value("${greeting}")
	private String greeting;
	@Autowired
	private Student stu;
	
	 // @RequestMapping(value= "/hello",method=RequestMethod.GET)
	 @GetMapping("/hello")
	    public String index(@RequestParam(value="id",required=false,defaultValue="aaa") String id) {
	        return id+greeting+ stu.toString()+"index1";
	    }
	  
	 @RequestMapping(value= "/hi/{id}",method=RequestMethod.GET)
	    public String index2(@PathVariable("id") String id) {
	        return id+greeting+ stu.toString()+"index2";
	    }
}
