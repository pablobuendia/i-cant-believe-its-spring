package com.pablo.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pablo.domain.Doctor;
import com.pablo.helpers.DoctorList;
import com.pablo.services.DoctorService;

@RestController
public class DoctorSearchController {

  @Autowired
  DoctorService docService;

  /**
   * Search for a list of doctors based on the Location and the specialty
   * 
   * @param location
   * @param specialty
   * @return
   */
  @RequestMapping(value = "/doctors", method = RequestMethod.GET, produces = "application/json")
  public DoctorList searchDoctor(
      @RequestParam(value = "location", required = false) String location,
      @RequestParam(value = "specialty", required = false) String specialty) {
    List<Doctor> docList = docService.findByLocationAndSpecialty(location, specialty);
    return new DoctorList(docList);
  }

}
