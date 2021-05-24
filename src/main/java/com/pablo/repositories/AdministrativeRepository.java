package com.pablo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.pablo.domain.Administrative;

@Repository
public interface AdministrativeRepository
		extends PagingAndSortingRepository<Administrative, Integer> {

}
