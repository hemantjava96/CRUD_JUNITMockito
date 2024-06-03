package com.example.student.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;
import com.example.student.util.StudentUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

	@MockBean
	StudentService studentService;
	@Autowired
	MockMvc mockMvc;

	@Test
	public void saveAllTest() throws JsonProcessingException, Exception {
		List<Student> students = generateDummyStudents(10);
		when(studentService.saveAll(students)).thenReturn(students);
		mockMvc.perform(post("/student/saveAll").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(students))).andExpect(status().isOk());

	}

	@Test
	public void getStudentByIdTest() throws JsonProcessingException, Exception {
		Student student = generateDummyStudents(1).get(0);
		Long studentId = student.getStudentId();
		System.out.println(student);
		when(studentService.getStudentById(12L)).thenReturn(student);
		mockMvc.perform(get("/student/get/{studentId}", 12).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name.fname").value(student.getName().getFname()));
	}

	@Test
	public void updateAddressTest() throws JsonProcessingException, Exception {
		// Generate dummy student data
		Student student = generateDummyStudents(1).get(0);
		// Mock the service call
		when(studentService.updateAddress(12L, student.getAddress())).thenReturn(student);
		// when(studentService.updateAddress(any(Long.class),
		// any(Address.class))).thenReturn(student);

		// Perform the PUT request and check the response
		mockMvc.perform(put("/student/address/{studentId}", 12).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(student.getAddress()))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name.fname").value(student.getName().getFname()));
	}

	@Test
	public void updateAddressTestNull() throws JsonProcessingException, Exception {
		// Generate dummy student data
		Student student = generateDummyStudents(1).get(0);
		// Mock the service call
		when(studentService.updateAddress(13L, student.getAddress())).thenReturn(null);
		// when(studentService.updateAddress(any(Long.class),
		// any(Address.class))).thenReturn(student);

		// Perform the PUT request and check the response
		mockMvc.perform(put("/student/address/{studentId}", 13).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(student.getAddress()))).andExpect(status().isNotFound());
	}

	@Test
	public void getStudentsByFirstNameAndLastNameTest() throws Exception {
		// Define query parameters
		Student student = generateDummyStudents(1).get(0);
		when(studentService.getStudentsByFirstNameAndLastName(student.getName().getFname(),
				student.getName().getLname())).thenReturn(List.of(student));
		mockMvc.perform(get("/student/get").param("fname", student.getName().getFname())
				.param("lname", student.getName().getLname()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].name.fname").value(student.getName().getFname()))
				.andExpect(jsonPath("$[0].name.lname").value(student.getName().getLname()));
	}

	// stub methods
	private List<Student> generateDummyStudents(int count) {
		return StudentUtil.generateDummyStudents(count);
	}

}