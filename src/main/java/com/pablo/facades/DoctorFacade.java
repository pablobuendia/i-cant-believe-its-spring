package com.pablo.facades;

import java.util.List;

import com.pablo.dto.AppointmentData;
import com.pablo.dto.DoctorData;

public interface DoctorFacade {

	public DoctorData findDoctorById(String doctorId);

	public void deleteDoctorById(String id);

	public List<DoctorData> findAllDoctors();

	public Double calculateSalary(String doctorId);

	public void addAppointment(String doctorId, AppointmentData appointment);

}
