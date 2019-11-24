package dev.drugowick.algaworks.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.AlgafoodApiApplication;
import dev.drugowick.algaworks.domain.model.Cuisine;

public class MergeCuisineMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		CuisineCrud cuisineCrud = applicationContext.getBean(CuisineCrud.class);
		
		Cuisine cuisine1 = new Cuisine();
		cuisine1.setName("Japanese");
		System.out.println("Cuisine persisted: " + cuisineCrud.save(cuisine1).getId());
		
		Cuisine cuisine2 = new Cuisine();
		cuisine2.setName("Ralapeño");
		System.out.println("Cuisine persisted: " + cuisineCrud.save(cuisine2).getId());
	}
}
