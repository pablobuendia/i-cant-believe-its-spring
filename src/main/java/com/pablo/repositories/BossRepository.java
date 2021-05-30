package com.pablo.repositories;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.pablo.domain.Boss;

@RepositoryRestResource(exported = false) // to block it from export. This prevents the repository and
											// its metadata from being served up.
public interface BossRepository extends Repository<Boss, Long> {

	Boss save(Boss manager);

	Boss findByName(String name);

}
