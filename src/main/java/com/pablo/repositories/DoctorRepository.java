package com.pablo.repositories;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.pablo.domain.Doctor;

public interface DoctorRepository extends Repository<Doctor, Long> {

	List<Doctor> findAll();

	Doctor save(Doctor doctor);

	Doctor findById(Long id);

	void deleteById(Long id);

	// List<Doctor> findByLocationAndSpecialty(String location, String specialty);

}
