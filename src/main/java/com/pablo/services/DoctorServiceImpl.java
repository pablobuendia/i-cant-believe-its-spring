package com.pablo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pablo.domain.Doctor;
import com.pablo.repositories.DoctorDAO;

@Service
public class DoctorServiceImpl implements DoctorService {

	private DoctorDAO doctorDAO;

	public DoctorServiceImpl(DoctorDAO doctorDAO) {
		this.doctorDAO = doctorDAO;
	}

	// @Override
	// public List<Doctor> findByLocationAndSpecialty(String location,
	// String specialtyCode) {
	// return doctorDAO.findByLocationAndSpecialty(location, specialtyCode);
	// }

	@Override
	public List<Doctor> findAllDoctors() {
		return doctorDAO.findAllDoctors();
	}
}
