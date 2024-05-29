package com.example.student.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Address;

public interface AddressDao extends JpaRepository<Address, Long> {

}
