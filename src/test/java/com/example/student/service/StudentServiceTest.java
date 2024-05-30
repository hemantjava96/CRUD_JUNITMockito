package com.example.student.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.student.dao.StudentDao;
import com.example.student.entity.Address;
import com.example.student.entity.Name;
import com.example.student.entity.Student;
import com.example.student.util.StudentUtil;

@SpringBootTest
public class StudentServiceTest {

	@Mock
	private StudentDao studentDao;

	@Mock
	private AddressService addressService;

	@Mock
	private NameService nameService;

	@InjectMocks
	private StudentService studentService;

	@Test
	public void saveAllTest() {
		List<Student> students = StudentUtil.generateDummyStudents(5);

		// Mock behavior of AddressService to return the same address that is passed to it
		when(addressService.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Mock behavior of NameService
		when(nameService.save(any(Name.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Mock behavior of StudentDao
		when(studentDao.saveAll(students)).thenReturn(students);

		// Call the method under test
		List<Student> savedStudents = studentService.saveAll(students);

		// Verify interactions and assertions
		verify(addressService, times(5)).save(any(Address.class)); // Verify save method is called twice for address (once for each student)
		verify(nameService, times(5)).save(any(Name.class)); // Verify save method is called twice for name (once for each student)
		verify(studentDao, times(1)).saveAll(students); // Verify saveAll method is called once for students
		assertEquals(students.size(), savedStudents.size());
		// Additional assertions if needed
	}

	@Test
	public void getAllTest() {
		List<Student> students = StudentUtil.generateDummyStudents(5);

		// Mock behavior of StudentDao
		when(studentDao.findAll()).thenReturn(students);
		assertEquals(studentService.getAll().size(), students.size());
		// Additional assertions if needed
	}

	@Test
	public void saveTest() {
		Student student = StudentUtil.generateDummyStudents(1).get(0);

		// Mock behavior of AddressService to return the same address that is passed to
		// it
		when(addressService.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Mock behavior of NameService
		when(nameService.save(any(Name.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Mock behavior of StudentDao
		when(studentDao.save(student)).thenReturn(student);

		assertEquals(studentService.save(student).getName().getFname(), student.getName().getFname());
		// Additional assertions if needed
	}

	@Test
	public void getStudentsByFirstNameAndLastNameTest() {
		List<Student> students = StudentUtil.generateDummyStudents(3);

		// Mock behavior of StudentDao
		when(studentDao.findByName_FnameAndName_Lname("john", "Don")).thenReturn(students);

		assertEquals(studentService.getStudentsByFirstNameAndLastName("john", "Don").size(), students.size());
		// Additional assertions if needed
	}

	@Test
	public void getStudentByIdTest() {
		Student student = StudentUtil.generateDummyStudents(1).get(0);

		// Mock behavior of StudentDao
		when(studentDao.findById(1L)).thenReturn(Optional.of(student));
		assertEquals(studentService.getStudentById(1L).getAadhar(), student.getAadhar());
		// Additional assertions if needed
	}

	@Test
	public void updateAddressTest() {
		// Mock data
		Long studentId = 1L;

		Address existingAddress = new Address();
		existingAddress.setAddressId(1L);
		existingAddress.setAddress("Old Address");
		existingAddress.setDistrict("Old District");
		existingAddress.setPincode("123456");
		existingAddress.setState("Old State");

		Student student = new Student();
		student.setStudentId(studentId);
		student.setAddress(existingAddress);

		Address newAddress = new Address();
		newAddress.setAddress("New Address");
		newAddress.setDistrict("New District");
		newAddress.setPincode("654321");
		newAddress.setState("New State");

		// Mock behavior
		when(studentDao.findById(studentId)).thenReturn(Optional.of(student));
		when(studentDao.save(any(Student.class))).thenReturn(student);

		// Call the method under test
		Student updatedStudent = studentService.updateAddress(studentId, newAddress);

		// Verify interactions
		verify(studentDao, times(1)).findById(studentId);
		verify(studentDao, times(1)).save(any(Student.class));

		// Verify that the address was updated correctly
		assertThat(updatedStudent.getAddress().getAddress()).isEqualTo(newAddress.getAddress());
		assertThat(updatedStudent.getAddress().getDistrict()).isEqualTo(newAddress.getDistrict());
		assertThat(updatedStudent.getAddress().getPincode()).isEqualTo(newAddress.getPincode());
		assertThat(updatedStudent.getAddress().getState()).isEqualTo(newAddress.getState());
	}
	
	@Test
	public void updateAddressTest1() {
		// Mock data
		Long studentId = 1L;

		Address existingAddress = null;

		Student student = new Student();
		student.setStudentId(studentId);
		student.setAddress(existingAddress);

		Address newAddress = new Address();
		newAddress.setAddress("New Address");
		newAddress.setDistrict("New District");
		newAddress.setPincode("654321");
		newAddress.setState("New State");

		// Mock behavior
		when(studentDao.findById(studentId)).thenReturn(Optional.of(student));
		when(studentDao.save(any(Student.class))).thenReturn(student);

		// Call the method under test
		Student updatedStudent = studentService.updateAddress(studentId, newAddress);

		// Verify interactions
		verify(studentDao, times(1)).findById(studentId);
		verify(studentDao, times(1)).save(any(Student.class));

		// Verify that the address was updated correctly
		assertThat(updatedStudent.getAddress().getAddress()).isEqualTo(newAddress.getAddress());
		assertThat(updatedStudent.getAddress().getDistrict()).isEqualTo(newAddress.getDistrict());
		assertThat(updatedStudent.getAddress().getPincode()).isEqualTo(newAddress.getPincode());
		assertThat(updatedStudent.getAddress().getState()).isEqualTo(newAddress.getState());
	}
	
	@Test
	public void updateAddressTest2() {

		// Mock behavior
		when(studentDao.findById(any(Long.class))).thenReturn(Optional.empty());
		

		// Call the method under test
		Student updatedStudent = studentService.updateAddress(1L, null);

		// Verify interactions
		verify(studentDao, times(1)).findById(1L);
		
		// Verify that the address was updated correctly
		assertEquals(updatedStudent, null);
	}

}
