package com.pablo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pablo.domain.Doctor;

/**
 * CrudRepository where T is the domain type that the repository manages. ID is the type
 * of the id of the entity that repository manages.
 * 
 * @author Pablo
 *
 */
public class DoctorRepository implements CrudRepository<Doctor, String> {

	@Override
	public <S extends Doctor> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Doctor> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Doctor> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Doctor> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Doctor> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Doctor entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends Doctor> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

}
