package dev.drugowick.algaworks.algafoodapi.jpa.test.paymentmethod;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import dev.drugowick.algaworks.algafoodapi.AlgafoodApiApplication;
import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PaymentMethodRepository;

public class MergePaymentMethodMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
				
		PaymentMethodRepository paymentMethodRepository = applicationContext.getBean(PaymentMethodRepository.class);
		
		PaymentMethod paymentMethod1 = new PaymentMethod();
		paymentMethod1.setDescription("Won't pay");
		System.out.println("PaymentMethod persisted: " + paymentMethodRepository.save(paymentMethod1).getId());
		
		PaymentMethod paymentMethod2 = new PaymentMethod();
		paymentMethod2.setDescription("Fuc da police");
		System.out.println("PaymentMethod persisted: " + paymentMethodRepository.save(paymentMethod2).getId());
	}
}
