package dev.drugowick.algaworks.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProvinceNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ProvinceNotFoundException(String message) {
		super(message);
	}

	public ProvinceNotFoundException(Long id) {
		this(String.format("There's no Province with the id %d", id));
	}

}
