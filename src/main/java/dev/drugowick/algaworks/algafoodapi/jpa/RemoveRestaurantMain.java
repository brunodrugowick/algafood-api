package dev.drugowick.algaworks.algafoodapi.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;

public class RemoveRestaurantMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		
		RestaurantRepository restaurantRepository = applicationContext.getBean(RestaurantRepository.class);
		
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		
		restaurantRepository.remove(restaurant);
		
	}
}
