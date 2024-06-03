package com.example.student.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.example.student.entity.Student;
import com.example.student.util.StudentUtil;

import jakarta.annotation.PostConstruct;

@DataJpaTest(showSql = true)
@ActiveProfiles("test")
public class StudentDaoTest {

	@Autowired
	private StudentDao studentDao;
	
	
	@Test
	public void testSave() {
		Student student = StudentUtil.generateDummyStudents(1).get(0);
		System.out.println(student);
		
		studentDao.save(student);
		Long studentId=student.getStudentId();
		
		// Retrieve the student from the database
	    Student foundStudent = studentDao.findById(studentId).orElse(null);
	    
	    // Verify that the student was saved and retrieved correctly
	    assertThat(foundStudent).isNotNull();
	    assertThat(foundStudent.getStudentId()).isEqualTo(studentId);
	}

}
