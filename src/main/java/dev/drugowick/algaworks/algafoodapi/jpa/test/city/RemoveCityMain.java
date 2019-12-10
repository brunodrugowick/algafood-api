package dev.drugowick.algaworks.algafoodapi.jpa.test.city;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class RemoveCityMain {

	public static void main(String[] args) {
            ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                    .web(WebApplicationType.NONE)
                    .run(args);


            CityRepository cityRepository = applicationContext.getBean(CityRepository.class);

            City city = new City();
            city.setId(1L);

            cityRepository.remove(1L);

    }
}
