package com.pablo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pablo.domain.Administrative;

@Repository
public interface AdministrativeRepository extends JpaRepository<Administrative, Integer> {

}
