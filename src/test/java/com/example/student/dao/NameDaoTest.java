package com.example.student.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.student.entity.Name;
import com.example.student.util.StudentUtil;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use the test database configuration
@ActiveProfiles("test") // Activate the test profile
public class NameDaoTest {
	

	@Autowired
	private NameDao nameDao;

	@Test
	public void testSave() {
		Name name = StudentUtil.generateDummyStudents(1).get(0).getName();
		System.out.println(name);
		
		nameDao.save(name);
		Long nameId=name.getNameId();
		
		// Retrieve the name from the database
		Name foundName = nameDao.findById(nameId).orElse(null);
	    
	    // Verify that the name was saved and retrieved correctly
	    assertThat(foundName).isNotNull();
	    assertThat(foundName.getNameId()).isEqualTo(nameId);
	}

}
