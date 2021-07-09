package com.pablo.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "administrative")
@EntityListeners(AuditingEntityListener.class)
public class Administrative {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;

	@Column(nullable = false)
	private int documentType;

	@Column(nullable = false)
	private int documentNumber;

	@ManyToOne
	private Boss boss;

	@Column(length = 100)
	private String description;

	@Column(length = 100)
	private String phoneNumber;

	@Version
	private Long version; // It causes a value to be
							// automatically stored and
							// updated every time a row is
							// inserted and updated.

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Administrative administrative = (Administrative) o;
		return Objects.equals(id, administrative.id)
				&& Objects.equals(firstName, administrative.firstName)
				&& Objects.equals(lastName, administrative.lastName)
				&& Objects.equals(documentNumber, administrative.documentNumber)
				&& Objects.equals(description, administrative.description)
				&& Objects.equals(version, administrative.version)
				&& Objects.equals(boss, administrative.boss);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(int documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Boss getBoss() {
		return boss;
	}

	public void setBoss(Boss boss) {
		this.boss = boss;
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, firstName, lastName, documentNumber, description, version,
				boss);
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + id + ", firstName='" + firstName + '\''
				+ ", lastName='" + lastName + '\'' + ", documentNumber='" + documentNumber
				+ '\'' + ", description='" + description + '\'' + ", version=" + version
				+ ", boss=" + boss + '}';
	}

}
