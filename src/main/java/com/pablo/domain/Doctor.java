package com.pablo.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor extends Person {

	private String specialty;

	private Double salary;

	private List<String> appointments;

	public Doctor(String specialty, Double salary, List<String> appointments) {
		super();
		this.specialty = specialty;
		this.salary = salary;
		this.appointments = appointments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((appointments == null) ? 0 : appointments.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		result = prime * result + ((specialty == null) ? 0 : specialty.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctor other = (Doctor) obj;
		if (appointments == null) {
			if (other.appointments != null)
				return false;
		}
		else if (!appointments.equals(other.appointments))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		}
		else if (!salary.equals(other.salary))
			return false;
		if (specialty == null) {
			if (other.specialty != null)
				return false;
		}
		else if (!specialty.equals(other.specialty))
			return false;
		return true;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public List<String> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<String> appointments) {
		this.appointments = appointments;
	}

}
