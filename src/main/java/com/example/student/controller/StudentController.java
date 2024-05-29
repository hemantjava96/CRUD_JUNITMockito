package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.entity.Address;
import com.example.student.entity.Student;
import com.example.student.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	
	@PostMapping(path = "/saveAll")
	public List<Student> saveAll(@RequestBody List<Student> students) {
		return studentService.saveAll(students);
	}
	
	@PutMapping(path = "/address/{studentId}")
	public ResponseEntity<Student> updateAddress(@PathVariable Long studentId, @RequestBody Address newAddress) {
        Student updatedStudent = studentService.updateAddress(studentId, newAddress);
        if (updatedStudent != null) {
            return ResponseEntity.ok(updatedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@GetMapping("/get")
    public List<Student> getStudentsByFirstNameAndLastName(@RequestParam String fname, @RequestParam String lname) {
        return studentService.getStudentsByFirstNameAndLastName(fname, lname);
    }
	
	@GetMapping("/get/{studentId}")
    public Student getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }
	
}
