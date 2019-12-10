package dev.drugowick.algaworks.algafoodapi.jpa.test.province;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class RemoveProvinceMain {

	public static void main(String[] args) {
            ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                    .web(WebApplicationType.NONE)
                    .run(args);


            ProvinceRepository provinceRepository = applicationContext.getBean(ProvinceRepository.class);

            Province province = new Province();
            province.setId(1L);

            provinceRepository.remove(1L);

    }
}
