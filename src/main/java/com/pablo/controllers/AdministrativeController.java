package com.pablo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pablo.domain.Administrative;
import com.pablo.services.AdministrativeService;

@RestController
public class AdministrativeController {

	private AdministrativeService administrativesService;

	public AdministrativeController(AdministrativeService administrativesService) {
		this.administrativesService = administrativesService;
	}

	@GetMapping("/administrative")
	public List<Administrative> getAllAdministrative() {
		return administrativesService.getAll();
	}

	@GetMapping("/administrative/{administrativeid}")
	public Administrative getAdministrative(
			@PathVariable("administrativeid") int administrativeid) {
		return administrativesService.getById(administrativeid);
	}

	@DeleteMapping("/administrative/{administrativeid}")
	public void deleteAdministrative(
			@PathVariable("administrativeid") int administrativeid) {
		administrativesService.delete(administrativeid);
	}

	@PostMapping("/administratives")
	public int saveAdministrative(@RequestBody Administrative administratives) {
		administrativesService.saveOrUpdate(administratives);
		return administratives.getId();
	}

	@PutMapping("/administratives")
	public Administrative update(@RequestBody Administrative administratives) {
		administrativesService.saveOrUpdate(administratives);
		return administratives;
	}
}
