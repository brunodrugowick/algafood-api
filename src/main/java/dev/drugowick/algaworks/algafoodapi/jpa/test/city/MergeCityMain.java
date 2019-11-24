package dev.drugowick.algaworks.algafoodapi.jpa.test.city;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;

public class MergeCityMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		CityRepository cityRepository = applicationContext.getBean(CityRepository.class);
		ProvinceRepository provinceRepository = applicationContext.getBean(ProvinceRepository.class);
		
		City city1 = new City();
		city1.setName("São Paulo");
		city1.setProvince(provinceRepository.get(1L));
		System.out.println("City persisted: " + cityRepository.save(city1).getId());
		
		City city2 = new City();
		city2.setName("Capitólio");
		city2.setProvince(provinceRepository.get(2L));
		System.out.println("City persisted: " + cityRepository.save(city2).getId());
	}
}
