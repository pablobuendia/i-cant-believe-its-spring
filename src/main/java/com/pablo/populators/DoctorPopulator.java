package com.pablo.populators;

import org.springframework.stereotype.Component;

import com.pablo.domain.Doctor;
import com.pablo.dto.DoctorData;

@Component
public class DoctorPopulator implements Populator<Doctor, DoctorData> {

	@Override
	public void populate(Doctor source, DoctorData target) {
		target.setId(source.getId().toString());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setSalary(source.getSalary().toString());
	}

}
