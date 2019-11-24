package dev.drugowick.algaworks.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.AlgafoodApiApplication;
import dev.drugowick.algaworks.domain.model.Cuisine;

public class FindCuisineMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		CuisineCrud cuisineCrud = applicationContext.getBean(CuisineCrud.class);
		List<Cuisine> cuisines = cuisineCrud.listAll();
		for (Cuisine cuisine : cuisines) {
			System.out.println(cuisine.getName());
		}
	}
}
