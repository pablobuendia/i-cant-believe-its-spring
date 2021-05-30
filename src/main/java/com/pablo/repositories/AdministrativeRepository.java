package com.pablo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.pablo.domain.Administrative;

/**
 * 
 * @author Pablo
 *
 * ?. property navigator to handle null checks. It comes from Spring SpEL expressions.
 *
 */
@Repository
@PreAuthorize("hasRole('ROLE_BOSS')")
public interface AdministrativeRepository
		extends PagingAndSortingRepository<Administrative, Integer> {

	@Override
	@PreAuthorize("#administrative?.boss == null or #administrative?.boss?.name == authentication?.name")
	Administrative save(@Param("administrative") Administrative administrative); // @Param is to link
																					// HTTP operations
																					// with the methods

	@Override
	@PreAuthorize("@administrativeRepository.findById(#id)?.boss?.name == authentication?.name")
	void deleteById(@Param("id") Integer id);

	@Override
	@PreAuthorize("#administrative?.boss?.name == authentication?.name")
	void delete(@Param("administrative") Administrative administrative);

}
