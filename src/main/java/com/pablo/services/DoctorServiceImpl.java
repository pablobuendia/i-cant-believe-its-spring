package com.pablo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pablo.domain.Doctor;
import com.pablo.repositories.DoctorDAO;

@Service
public class DoctorServiceImpl implements DoctorService {

  @Autowired
  private DoctorDAO doctorDAO;

  @Override
  public List<Doctor> findByLocationAndSpecialty(String location, String specialtyCode) {
    return doctorDAO.findByLocationAndSpecialty(location, specialtyCode);
  }
}
