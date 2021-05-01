package com.pablo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pablo.domain.Administrative;

@Service
public class AdministrativeService {

  @Autowired
  AdministrativeRepository administrativeRepository;

  // getting all administratives record by using the method findaAll() of CrudRepository
  public List<Administrative> getAllAdministrative() {
    List<Administrative> administratives = new ArrayList<>();
    administrativeRepository.findAll().forEach(administratives::add);
    return administratives;
  }

  // getting a specific record by using the method findById() of CrudRepository
  public Administrative getAdministrativeById(int id) {
    Optional<Administrative> result = administrativeRepository.findById(id);
    return result.isPresent() ? result.get() : null;
  }

  // saving a specific record by using the method save() of CrudRepository
  public void saveOrUpdate(Administrative administratives) {
    administrativeRepository.save(administratives);
  }

  // deleting a specific record by using the method deleteById() of CrudRepository
  public void delete(int id) {
    administrativeRepository.deleteById(id);
  }

  // updating a record
  public void update(Administrative administratives) {
    administrativeRepository.save(administratives);
  }
}
