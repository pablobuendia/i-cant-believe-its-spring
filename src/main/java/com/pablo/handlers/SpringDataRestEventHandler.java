package com.pablo.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.pablo.domain.Administrative;
import com.pablo.domain.Boss;
import com.pablo.repositories.BossRepository;

@Component
@RepositoryEventHandler(Administrative.class)
public class SpringDataRestEventHandler {

	private final BossRepository bossRepository;

	@Autowired
	public SpringDataRestEventHandler(BossRepository managerRepository) {
		this.bossRepository = managerRepository;
	}

	@HandleBeforeCreate
	@HandleBeforeSave
	public void applyUserInformationUsingSecurityContext(Administrative administrative) {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Boss manager = this.bossRepository.findByName(name);
		if (manager == null) {
			Boss boss = new Boss();
			boss.setName(name);
			boss.setRoles(new String[] { "ROLE_BOSS" });
			manager = this.bossRepository.save(boss);
		}
		administrative.setBoss(manager);
	}
}
