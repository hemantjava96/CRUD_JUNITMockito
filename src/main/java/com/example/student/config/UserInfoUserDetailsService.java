package com.example.student.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.student.dao.StudentDao;
import com.example.student.entity.Student;
import com.example.student.service.StudentService;

public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private StudentDao studentDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Long studentId;
		try {
			studentId = Long.parseLong(username);
		} catch (NumberFormatException e) {
			throw new UsernameNotFoundException("Invalid user ID: " + username);
		}
		System.out.println("UserInfoUserDetailsService.loadUserByUsername() : " + studentId + "," + username);
		Optional<Student> student = studentDao.findById(studentId);
		return student.map(UserInfoUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
	}

}
