package com.example.student.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    
    @OneToOne
    @JoinColumn(name = "name_id")
    private Name name;
    
    private Integer standard;
    
    @Column(unique = true, nullable = false)
    private String aadhar;
    
    // Getters and setters
}
