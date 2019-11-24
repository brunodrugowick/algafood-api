package dev.drugowick.algaworks.algafoodapi.jpa.test.city;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;

public class FindCityMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		CityRepository cityRepository = applicationContext.getBean(CityRepository.class);
		List<City> citys = cityRepository.list();
		for (City city : citys) {
			System.out.printf("%s/%s\n", city.getName(), city.getProvince().getAbbreviation());
		}
	}
}
