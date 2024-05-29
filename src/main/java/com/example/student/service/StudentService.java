package com.example.student.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.dao.StudentDao;
import com.example.student.entity.Address;
import com.example.student.entity.Name;
import com.example.student.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class StudentService {

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private AddressService addressService;

	@Autowired
	private NameService nameService;

	public List<Student> saveAll(List<Student> students) {
		students.stream().forEach(student -> {
			// Save address if not null and not yet saved
			Address address = student.getAddress();
			if (address != null && address.getAddressId() == null) {
				student.setAddress(addressService.save(address));
			}
			// Save name if not null and not yet saved
			Name name = student.getName();
			if (name != null && name.getNameId() == null) {
				student.setName(nameService.save(name));
			}
		});
		// Save all students (including those with updated addresses/names)
		return studentDao.saveAll(students);
	}

	public List<Student> getAll() {
		return studentDao.findAll();
	}

	public Student save(Student student) {
		// Save address if not null and not yet saved
		Address address = student.getAddress();
		if (address != null && address.getAddressId() == null)
			student.setAddress(addressService.save(address));

		// Save name if not null and not yet saved
		Name name = student.getName();
		if (name != null && name.getNameId() == null)
			student.setName(nameService.save(name));

		return studentDao.save(student);
	}

	public Student updateAddress(Long studentId, Address newAddress) {
		Optional<Student> optionalStudent = studentDao.findById(studentId);
		if (optionalStudent.isPresent()) {
			Student student = optionalStudent.get();
			Address address = student.getAddress();
			if (address != null) {
				if (newAddress.getAddress() != null && !newAddress.getAddress().isBlank()) {
					address.setAddress(newAddress.getAddress());
				}
				if (newAddress.getDistrict() != null && !newAddress.getDistrict().isBlank()) {
					address.setDistrict(newAddress.getDistrict());
				}
				if (newAddress.getPincode() != null && !newAddress.getPincode().isBlank()) {
					address.setPincode(newAddress.getPincode());
				}
				if (newAddress.getState() != null && !newAddress.getState().isBlank()) {
					address.setState(newAddress.getState());
				}
			} else {
				// If address is null, set the new address directly
				student.setAddress(newAddress);
			}
			return studentDao.save(student);
		}
		return null;
	}

	public List<Student> getStudentsByFirstNameAndLastName(String fname, String lname) {
		// return studentDao.findByFirstNameAndLastName(fname, lname);
		return studentDao.findByName_FnameAndName_Lname(fname, lname);
	}

	public Student getStudentById(Long studentId) {
		return studentDao.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

	}
}
