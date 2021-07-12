package com.pablo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pablo.domain.Doctor;
import com.pablo.dto.DoctorData;
import com.pablo.populators.Populator;
import com.pablo.repositories.DoctorDAO;

@Service
public class DoctorServiceImpl implements DoctorService {

	private DoctorDAO doctorDAO;

	private Populator<Doctor, DoctorData> doctorPopulator;

	public DoctorServiceImpl(DoctorDAO doctorDAO,
			Populator<Doctor, DoctorData> doctorPopulator) {
		this.doctorDAO = doctorDAO;
		this.doctorPopulator = doctorPopulator;
	}

	// @Override
	// public List<Doctor> findByLocationAndSpecialty(String location,
	// String specialtyCode) {
	// return doctorDAO.findByLocationAndSpecialty(location, specialtyCode);
	// }

	@Override
	public DoctorData findDoctorById(String id) {
		Doctor doctor = doctorDAO.findDoctorById(Long.parseLong(id));
		return parseDoctor(doctor);
	}

	private DoctorData parseDoctor(Doctor doctor) {
		DoctorData doctorData = new DoctorData();
		doctorPopulator.populate(doctor, doctorData);
		return doctorData;
	}

	@Override
	public List<DoctorData> findAllDoctors() {
		List<Doctor> doctors = doctorDAO.findAllDoctors();
		return parseAllDoctors(doctors);
	}

	private List<DoctorData> parseAllDoctors(List<Doctor> doctors) {
		List<DoctorData> doctorDataList = new ArrayList<DoctorData>();
		for (Doctor doctor : doctors) {
			doctorDataList.add(parseDoctor(doctor));
		}
		return doctorDataList;
	}

	@Override
	public void deleteDoctorById(String id) {
		doctorDAO.deleteDoctorById(Long.parseLong(id));
	}

}
