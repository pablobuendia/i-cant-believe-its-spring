package com.pablo.loaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.pablo.domain.Administrative;
import com.pablo.repositories.AdministrativeRepository;
import com.pablo.repositories.BossRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final AdministrativeRepository administrativeRepository;
	private final BossRepository managers;

	@Autowired
	public DatabaseLoader(AdministrativeRepository administrativeRepository,
			BossRepository managerRepository) {

		this.administrativeRepository = administrativeRepository;
		this.managers = managerRepository;
	}

	@Override
	public void run(String... strings) throws Exception {

		try {
			SecurityContext ctx = SecurityContextHolder.createEmptyContext();
			SecurityContextHolder.setContext(ctx);
			ctx.setAuthentication(new UsernamePasswordAuthenticationToken("greg",
					"doesn't matter", AuthorityUtils.createAuthorityList("ROLE_BOSS")));

			Administrative a = this.administrativeRepository.findById(1).get();
			a.setBoss(managers.findByName("greg"));

			this.administrativeRepository.save(a);

		}
		finally {
			SecurityContextHolder.clearContext();
		}

	}
}
