package com.pablo.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  AdministrativeService administrativesService;

  // creating a get mapping that retrieves all the administratives detail from the database
  @GetMapping("/administrative")
  public List<Administrative> getAllAdministrative() {
    return administrativesService.getAllAdministrative();
  }

  // creating a get mapping that retrieves the detail of a specific administrative
  @GetMapping("/administrative/{administrativeid}")
  public Administrative getAdministrative(@PathVariable("administrativeid") int administrativeid) {
    return administrativesService.getAdministrativeById(administrativeid);
  }

  // creating a delete mapping that deletes a specified administrative
  @DeleteMapping("/administrative/{administrativeid}")
  public void deleteAdministrative(@PathVariable("administrativeid") int administrativeid) {
    administrativesService.delete(administrativeid);
  }

  // creating post mapping that post the administrative detail in the database
  @PostMapping("/administratives")
  public int saveAdministrative(@RequestBody Administrative administratives) {
    administrativesService.saveOrUpdate(administratives);
    return administratives.getId();
  }

  // creating put mapping that updates the administrative detail
  @PutMapping("/administratives")
  public Administrative update(@RequestBody Administrative administratives) {
    administrativesService.saveOrUpdate(administratives);
    return administratives;
  }
}
