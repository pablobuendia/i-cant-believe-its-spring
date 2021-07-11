package com.pablo.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor extends Person {

	private String specialty;

	private Double salary;

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

}
