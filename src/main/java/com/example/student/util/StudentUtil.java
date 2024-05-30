package com.example.student.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.student.entity.Address;
import com.example.student.entity.Name;
import com.example.student.entity.Student;

public class StudentUtil {
	private static final Random RANDOM = new Random();

	public static List<Student> generateDummyStudents(int count) {
		List<Student> students = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			students.add(createDummyStudent(i));
		}
		return students;
	}
	private static Student createDummyStudent(int index) {
        Student student = new Student();
        student.setName(createDummyName(index));
        student.setAddress(createDummyAddress(index));
        student.setStandard(RANDOM.nextInt(12)+1); // Standards 1 to 12
        student.setAadhar("Aadhar" + (1000 + index));
        return student;
    }
	private static Name createDummyName(int index) {
        Name name = new Name();
        name.setFname("FirstName" + index);
        name.setLname("LastName" + index);
        name.setMname("MiddleName" + index);
        return name;
    }
	private static Address createDummyAddress(int index) {
        Address address = new Address();
        address.setAddress("AddressLine" + index);
        address.setDistrict("District" + index);
        address.setPincode("123456" + index);
        address.setState("State" + index);
        return address;
    }

}
