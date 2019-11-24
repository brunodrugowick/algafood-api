package dev.drugowick.algaworks.algafoodapi.jpa.test.permission;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PermissionRepository;

public class MergePermissionMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		PermissionRepository permissionRepository = applicationContext.getBean(PermissionRepository.class);
		
		Permission permission1 = new Permission();
		permission1.setName("DO_WHATEVER");
		permission1.setDescription("This modafo*ker has no limits!");
		System.out.println("Permission persisted: " + permissionRepository.save(permission1).getId());
		
		Permission permission2 = new Permission();
		permission2.setName("OBSERVER");
		permission2.setDescription("Just watching... and judging.");
		System.out.println("Permission persisted: " + permissionRepository.save(permission2).getId());
	}
}
