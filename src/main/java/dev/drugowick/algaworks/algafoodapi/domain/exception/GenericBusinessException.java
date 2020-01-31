package dev.drugowick.algaworks.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GenericBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GenericBusinessException(String message) {
		super(message);
	}

	public GenericBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

}
