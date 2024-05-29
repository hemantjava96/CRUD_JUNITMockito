package com.example.student.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student.entity.Name;

@Repository
public interface NameDao extends JpaRepository<Name, Long> {

}
