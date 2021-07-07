package com.pablo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pablo.domain.Administrative;
import com.pablo.repositories.AdministrativeRepository;

@Service
public class AdministrativeService {

	private AdministrativeRepository administrativeRepository;

	public AdministrativeService(AdministrativeRepository administrativeRepository) {
		this.administrativeRepository = administrativeRepository;
	}

	public List<Administrative> getAll() {
		List<Administrative> administratives = new ArrayList<>();
		administrativeRepository.findAll().forEach(administratives::add);
		return administratives;
	}

	public Administrative getById(int id) {
		Optional<Administrative> result = administrativeRepository.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	public void saveOrUpdate(Administrative administratives) {
		administrativeRepository.save(administratives);
	}

	public void delete(int id) {
		administrativeRepository.deleteById(id);
	}

	public void update(Administrative administratives) {
		administrativeRepository.save(administratives);
	}
}
