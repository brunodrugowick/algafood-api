package dev.drugowick.algaworks.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EntityBeingUsedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityBeingUsedException(String message) {
		super(message);
	}


}
