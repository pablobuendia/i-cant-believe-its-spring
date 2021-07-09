package com.pablo.repositories;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.pablo.domain.Doctor;

public interface DoctorRepository extends Repository<Doctor, Integer> {

	List<Doctor> findAll();

	Doctor save(Doctor manager);

	// List<Doctor> findByLocationAndSpecialty(String location, String specialty);

}
