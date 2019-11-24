package dev.drugowick.algaworks.algafoodapi.jpa.test.restaurant;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;

public class FindRestaurantMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		RestaurantRepository restaurantRepository = applicationContext.getBean(RestaurantRepository.class);
		List<Restaurant> restaurants = restaurantRepository.list();
		for (Restaurant restaurant : restaurants) {
			System.out.printf("%s - %f - %s\n", restaurant.getName(),
					restaurant.getDeliveryFee(),
					restaurant.getCuisine().getName());
		}
	}
}
