package com.example.student.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;
import com.example.student.util.StudentUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerTest {
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper mapper;


	@MockBean
	StudentService studentService;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}
	

	 @Test
	    @WithMockUser(username = "user", roles = {"STUDENT"})
	    public void saveAllTest() throws JsonProcessingException, Exception {
	        List<Student> students = generateDummyStudents(10);
	        when(studentService.saveAll(students)).thenReturn(students);
	        mockMvc.perform(post("/private/student/saveAll")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(mapper.writeValueAsString(students)))
	                .andExpect(status().isOk());
	    }

	 @Test
	    @WithMockUser(username = "user", authorities = {"STUDENT"})
	    public void getStudentByIdTest() throws JsonProcessingException, Exception {
	        Student student = generateDummyStudents(1).get(0);
	        Long studentId = student.getStudentId();
	        System.out.println(student);
	        when(studentService.getStudentById(12L)).thenReturn(student);
	        mockMvc.perform(get("/private/student/get/{studentId}", 12).contentType(MediaType.APPLICATION_JSON))
	               .andExpect(status().isOk())
	               .andExpect(jsonPath("$.name").value(student.getName()));
	    }

	@Test
	@WithMockUser(username = "user", authorities = {"STUDENT"})
	public void updateAddressTest() throws JsonProcessingException, Exception {
		// Generate dummy student data
		Student student = generateDummyStudents(1).get(0);
		// Mock the service call
		when(studentService.updateAddress(12L, student.getAddress())).thenReturn(student);
		// when(studentService.updateAddress(any(Long.class),
		// any(Address.class))).thenReturn(student);

		// Perform the PUT request and check the response
		mockMvc.perform(put("/private/student/address/{studentId}", 12).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(student.getAddress()))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name.fname").value(student.getName().getFname()));
	}

	@Test
	@WithMockUser(username = "user", authorities = {"STUDENT"})
	public void updateAddressTestNull() throws JsonProcessingException, Exception {
		// Generate dummy student data
		Student student = generateDummyStudents(1).get(0);
		// Mock the service call
		when(studentService.updateAddress(13L, student.getAddress())).thenReturn(null);
		// when(studentService.updateAddress(any(Long.class),
		// any(Address.class))).thenReturn(student);

		// Perform the PUT request and check the response
		mockMvc.perform(put("/private/student/address/{studentId}", 13).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(student.getAddress()))).andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "user", authorities = {"MONITOR"})
	public void getStudentsByFirstNameAndLastNameTest() throws Exception {
		// Define query parameters
		Student student = generateDummyStudents(1).get(0);
		when(studentService.getStudentsByFirstNameAndLastName(student.getName().getFname(),
				student.getName().getLname())).thenReturn(List.of(student));
		mockMvc.perform(get("/private/student/get").param("fname", student.getName().getFname())
				.param("lname", student.getName().getLname()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].name.fname").value(student.getName().getFname()))
				.andExpect(jsonPath("$[0].name.lname").value(student.getName().getLname()));
	}

	// stub methods
	private List<Student> generateDummyStudents(int count) {
		return StudentUtil.generateDummyStudents(count);
	}

}