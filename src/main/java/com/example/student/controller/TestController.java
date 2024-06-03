package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;
import com.example.student.util.StudentUtil;

@RestController
@RequestMapping("/public")
public class TestController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public String get() {
		return "tested successfully";
	}
	
	@GetMapping("/insert/dummy")
	public List<Student> insertDummyData() {
		List<Student> students = StudentUtil.generateDummyStudents(50);
		List<Student> savedStudents = studentService.saveAll(students);
		return savedStudents;
	}
}
