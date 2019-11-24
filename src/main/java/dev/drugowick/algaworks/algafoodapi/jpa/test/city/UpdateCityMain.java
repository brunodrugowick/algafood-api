package dev.drugowick.algaworks.algafoodapi.jpa.test.city;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;

public class UpdateCityMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		CityRepository cityRepository = applicationContext.getBean(CityRepository.class);
		ProvinceRepository provinceRepository = applicationContext.getBean(ProvinceRepository.class);
		
		City city = new City();
		city.setId(1L);
		city.setName("Changeit");
		city.setProvince(provinceRepository.get(1L));
		System.out.println("City persisted (updated): " + cityRepository.save(city).getId());
		
	}
}
