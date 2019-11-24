package dev.drugowick.algaworks.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.AlgafoodApiApplication;
import dev.drugowick.algaworks.domain.model.Cuisine;

public class RemoveCuisineMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		CuisineCrud cuisineCrud = applicationContext.getBean(CuisineCrud.class);
		
		Cuisine cuisine = new Cuisine();
		cuisine.setId(1L);
		
		cuisineCrud.remove(cuisine);
		
	}
}
