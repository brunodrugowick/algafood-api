package dev.drugowick.algaworks.algafoodapi.jpa.test.paymentmethod;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PaymentMethodRepository;

public class RemovePaymentMethodMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		
		PaymentMethodRepository paymentMethodRepository = applicationContext.getBean(PaymentMethodRepository.class);
		
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setId(1L);
		
		paymentMethodRepository.remove(paymentMethod);
		
	}
}
