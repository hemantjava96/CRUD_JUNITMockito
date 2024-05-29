package com.example.student.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.student.entity.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {
	// Using a JPQL query
    @Query("SELECT s FROM Student s JOIN s.name n WHERE n.fname = :fname AND n.lname = :lname")
    List<Student> findByFirstNameAndLastName(@Param("fname") String fname, @Param("lname") String lname);

    // Or, using a derived query method
    List<Student> findByName_FnameAndName_Lname(String fname, String lname);
}
