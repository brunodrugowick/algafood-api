package dev.drugowick.algaworks.algafoodapi.jpa;

import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;

public class MergeRestaurantMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		RestaurantRepository restaurantRepository = applicationContext.getBean(RestaurantRepository.class);
		
		Restaurant restaurant1 = new Restaurant();
		restaurant1.setName("Bonelli");
		restaurant1.setDeliveryFee(BigDecimal.TEN);
		System.out.println("Restaurant persisted: " + restaurantRepository.save(restaurant1).getId());
		
		Restaurant restaurant2 = new Restaurant();
		restaurant2.setName("El Mexicano Doido");
		restaurant2.setDeliveryFee(BigDecimal.TEN);
		System.out.println("Restaurant persisted: " + restaurantRepository.save(restaurant2).getId());
	}
}
