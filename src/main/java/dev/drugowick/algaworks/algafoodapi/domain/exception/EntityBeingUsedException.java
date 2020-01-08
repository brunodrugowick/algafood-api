package dev.drugowick.algaworks.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityBeingUsedException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;

	public EntityBeingUsedException(HttpStatus status, String reason) {
		super(status, reason);
	}

	public EntityBeingUsedException(String message) {
		this(HttpStatus.CONFLICT, message);
	}


}
