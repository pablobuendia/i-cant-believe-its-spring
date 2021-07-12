package com.pablo.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pablo.domain.Doctor;

@Repository
@Transactional
public class DoctorDAOImpl implements DoctorDAO {

	private DoctorRepository doctorRepository;

	public DoctorDAOImpl(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}

	// @Override
	// public List<Doctor> findByLocationAndSpecialty(String location, String specialty) {
	// return doctorRepository.findByLocationAndSpecialty(location, specialty);
	// }

	@Override
	public List<Doctor> findAllDoctors() {
		return this.doctorRepository.findAll();
	}

	@Override
	public Doctor findDoctorById(Long id) {
		return this.doctorRepository.findById(id);
	}

	@Override
	public void deleteDoctorById(Long id) {
		this.doctorRepository.deleteById(id);
	}

	@Override
	public void save(Doctor doctor) {
		this.save(doctor);
	}

}
