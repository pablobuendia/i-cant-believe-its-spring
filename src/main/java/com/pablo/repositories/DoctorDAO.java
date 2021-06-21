package com.pablo.repositories;

import java.util.List;

import com.pablo.domain.Doctor;

public interface DoctorDAO {

	public List<Doctor> findByLocationAndSpecialty(String location, String specialty);

	public List<Doctor> findAllDoctors();
}
