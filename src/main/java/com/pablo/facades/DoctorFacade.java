package com.pablo.facades;

import java.time.LocalDateTime;
import java.util.List;

import com.pablo.dto.AppointmentData;
import com.pablo.dto.DoctorData;

public interface DoctorFacade {

	public DoctorData getDoctorById(String doctorId);

	public List<String> getAvailableDoctors(LocalDateTime startDate,
			LocalDateTime endDate);

	public List<String> getAllDoctorId();

	public void updateDoctorById(String doctorId);

	public void deleteDoctorById(String doctorId);

	public void addAppointment(String doctorId, AppointmentData appointment);

	public Double calculateSalary(String doctorId);
}
