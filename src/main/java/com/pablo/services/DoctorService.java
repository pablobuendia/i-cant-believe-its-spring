package com.pablo.services;

import java.util.List;

import com.pablo.dto.DoctorData;

public interface DoctorService {

	// List<Doctor> findByLocationAndSpecialty(String location, String specialtyCode);

	public List<DoctorData> findAllDoctors();

	public DoctorData findDoctorById(String id);

	public void deleteDoctorById(String id);

}
