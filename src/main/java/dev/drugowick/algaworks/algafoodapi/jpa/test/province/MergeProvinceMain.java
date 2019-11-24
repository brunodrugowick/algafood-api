package dev.drugowick.algaworks.algafoodapi.jpa.test.province;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;

public class MergeProvinceMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		ProvinceRepository provinceRepository = applicationContext.getBean(ProvinceRepository.class);
		
		Province province1 = new Province();
		province1.setName("Rio de Janeiro");
		province1.setAbbreviation("RJ");
		System.out.println("Province persisted: " + provinceRepository.save(province1).getId());
		
		Province province2 = new Province();
		province2.setName("Goiás");
		province2.setAbbreviation("GO");
		System.out.println("Province persisted: " + provinceRepository.save(province2).getId());
	}
}
