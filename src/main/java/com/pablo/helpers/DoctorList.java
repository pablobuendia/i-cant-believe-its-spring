package com.pablo.helpers;

import java.util.List;

import com.pablo.domain.Doctor;

public class DoctorList {
	private List<Doctor> doctors;

	public DoctorList(List<Doctor> doctors) {
		this.setDoctors(doctors);
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

}
