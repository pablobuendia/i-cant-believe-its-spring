package com.pablo.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pablo.domain.Doctor;
import com.pablo.helpers.DoctorList;
import com.pablo.services.DoctorService;

@RestController
@RequestMapping("/doctors")
public class DoctorsController {

  @Autowired
  DoctorService docService;

  /**
   * Search for a list of doctors based on the Location and the specialty
   * 
   * @param location
   * @param specialty
   * @return
   */
  @GetMapping(value = "/searchDoctor", produces = MediaType.APPLICATION_JSON_VALUE)
  public DoctorList searchDoctor(
      @RequestParam(value = "location", required = false) String location,
      @RequestParam(value = "specialty", required = false) String specialty) {
    List<Doctor> docList = docService.findByLocationAndSpecialty(location, specialty);
    return new DoctorList(docList);
  }

}
