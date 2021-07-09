package com.pablo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String streetName;

	@Column(nullable = false, length = 50)
	private String streetNumber;

	@Column(nullable = false, length = 50)
	private String city;

	@Column(nullable = false, length = 50)
	private String district;

	@Column(nullable = false, length = 50)
	private String province;

	@Column(nullable = false, length = 50)
	private String country;

	@Override
	public String toString() {
		return "Address [id=" + id + ", streetName=" + streetName + ", streetNumber="
				+ streetNumber + ", city=" + city + ", district=" + district
				+ ", province=" + province + ", country=" + country + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
		result = prime * result + ((streetNumber == null) ? 0 : streetNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		}
		else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		}
		else if (!country.equals(other.country))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		}
		else if (!district.equals(other.district))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		}
		else if (!province.equals(other.province))
			return false;
		if (streetName == null) {
			if (other.streetName != null)
				return false;
		}
		else if (!streetName.equals(other.streetName))
			return false;
		if (streetNumber == null) {
			if (other.streetNumber != null)
				return false;
		}
		else if (!streetNumber.equals(other.streetNumber))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
