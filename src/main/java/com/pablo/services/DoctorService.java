package com.pablo.services;

import java.util.List;

import com.pablo.domain.Doctor;

public interface DoctorService {

	List<Doctor> findByLocationAndSpecialty(String location, String specialtyCode);

	List<Doctor> findAllDoctors();

}
