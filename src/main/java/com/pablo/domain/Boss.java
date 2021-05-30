package com.pablo.domain;

import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Boss {

	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

	@Column
	private @Id @GeneratedValue Long id;

	@Column
	private String name;

	@Column
	private @JsonIgnore String password;

	@Column
	private String[] roles;

	public void setPassword(String password) {
		this.password = PASSWORD_ENCODER.encode(password);
	}

	public Boss() {
	}

	public Boss(String name, String password, String... roles) {

		this.name = name;
		this.setPassword(password);
		this.roles = roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Boss boss = (Boss) o;
		return Objects.equals(id, boss.id) && Objects.equals(name, boss.name)
				&& Objects.equals(password, boss.password)
				&& Arrays.equals(roles, boss.roles);
	}

	@Override
	public int hashCode() {

		int result = Objects.hash(id, name, password);
		result = 31 * result + Arrays.hashCode(roles);
		return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Boss{" + "id=" + id + ", name='" + name + '\'' + ", roles="
				+ Arrays.toString(roles) + '}';
	}

}
