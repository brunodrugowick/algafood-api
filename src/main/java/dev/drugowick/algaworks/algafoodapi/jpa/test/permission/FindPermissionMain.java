package dev.drugowick.algaworks.algafoodapi.jpa.test.permission;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PermissionRepository;

public class FindPermissionMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		PermissionRepository permissionRepository = applicationContext.getBean(PermissionRepository.class);
		List<Permission> permissions = permissionRepository.list();
		for (Permission permission : permissions) {
			System.out.printf("%s - %s\n", permission.getName(), permission.getDescription());
		}
	}
}
