package dev.drugowick.algaworks.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.AlgafoodApiApplication;
import dev.drugowick.algaworks.domain.model.Cuisine;
import dev.drugowick.algaworks.domain.repository.CuisineRepository;

public class FindCuisineMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		CuisineRepository cuisineRepository = applicationContext.getBean(CuisineRepository.class);
		List<Cuisine> cuisines = cuisineRepository.list();
		for (Cuisine cuisine : cuisines) {
			System.out.println(cuisine.getName());
		}
	}
}
