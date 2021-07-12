package com.pablo.facades;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pablo.dto.AppointmentData;
import com.pablo.dto.DoctorData;
import com.pablo.services.DoctorService;

@Service
public class DoctorFacadeImpl implements DoctorFacade {

	public DoctorService doctorService;

	public DoctorFacadeImpl(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@Override
	public DoctorData findDoctorById(String doctorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteDoctorById(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DoctorData> findAllDoctors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double calculateSalary(String doctorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAppointment(String doctorId, AppointmentData appointment) {
		// TODO Auto-generated method stub

	}

}
