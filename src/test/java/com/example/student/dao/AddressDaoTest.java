package com.example.student.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.student.entity.Address;
import com.example.student.util.StudentUtil;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use the test database configuration
@ActiveProfiles("test") // Activate the test profile
public class AddressDaoTest {
	

	@Autowired
	private AddressDao addressDao;

	@Test
	public void testSave() {
		Address address = StudentUtil.generateDummyStudents(1).get(0).getAddress();
		System.out.println(address);
		
		addressDao.save(address);
		Long addressId=address.getAddressId();
		
		// Retrieve the student from the database
		Address foundAddress = addressDao.findById(addressId).orElse(null);
	    
	    // Verify that the student was saved and retrieved correctly
	    assertThat(foundAddress).isNotNull();
	    assertThat(foundAddress.getAddressId()).isEqualTo(addressId);
	}

}
