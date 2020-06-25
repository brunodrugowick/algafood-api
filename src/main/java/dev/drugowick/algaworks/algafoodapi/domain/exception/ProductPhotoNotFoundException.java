package dev.drugowick.algaworks.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProductPhotoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ProductPhotoNotFoundException(String message) {
		super(message);
	}

	public ProductPhotoNotFoundException(Long restaurantId, Long productId) {
		this(String.format("There's no Photo for the Product id %d at the Restaurant %d", productId, restaurantId));
	}

}
