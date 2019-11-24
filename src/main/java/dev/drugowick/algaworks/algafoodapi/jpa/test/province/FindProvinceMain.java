package dev.drugowick.algaworks.algafoodapi.jpa.test.province;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;

public class FindProvinceMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		ProvinceRepository provinceRepository = applicationContext.getBean(ProvinceRepository.class);
		List<Province> provinces = provinceRepository.list();
		for (Province province : provinces) {
			System.out.println(province.getName());
		}
	}
}
