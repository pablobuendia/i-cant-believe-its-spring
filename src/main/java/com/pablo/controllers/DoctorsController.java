package com.pablo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pablo.dto.DoctorData;
import com.pablo.facades.DoctorFacade;

@Controller
@RequestMapping("/doctors")
public class DoctorsController {

	public DoctorFacade doctorFacade;

	public DoctorsController(DoctorFacade doctorFacade) {
		this.doctorFacade = doctorFacade;
	}

	@GetMapping("/findAllDoctors")
	public String findAllDoctors(Model model) {
		List<DoctorData> doctors = this.doctorFacade.findAllDoctors();
		model.addAttribute("doctors", doctors);
		return "doctors";
	}

	// @GetMapping(value = "/searchDoctor", produces = MediaType.APPLICATION_JSON_VALUE)
	// public DoctorList searchDoctor(
	// @RequestParam(value = "location", required = false) String location,
	// @RequestParam(value = "specialty", required = false) String specialty) {
	// List<Doctor> docList = docService.findByLocationAndSpecialty(location, specialty);
	// return new DoctorList(docList);
	// }

}
