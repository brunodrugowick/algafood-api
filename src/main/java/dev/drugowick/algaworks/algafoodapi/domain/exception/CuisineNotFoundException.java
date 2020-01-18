package dev.drugowick.algaworks.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CuisineNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public CuisineNotFoundException(String message) {
		super(message);
	}

	public CuisineNotFoundException(Long id) {
		this(String.format("There's no Cuisine with the id %d.", id));
	}

}
