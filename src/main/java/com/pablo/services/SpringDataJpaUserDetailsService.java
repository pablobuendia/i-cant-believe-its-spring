package com.pablo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pablo.domain.Boss;
import com.pablo.repositories.BossRepository;

@Component
public class SpringDataJpaUserDetailsService implements UserDetailsService {

	private final BossRepository repository;

	@Autowired
	public SpringDataJpaUserDetailsService(BossRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Boss boss = this.repository.findByName(username);
		return new User(boss.getName(), boss.getPassword(),
				AuthorityUtils.createAuthorityList(boss.getRoles()));

	}

}
