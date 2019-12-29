package dev.drugowick.algaworks.algafoodapi.domain.exception;

public class EntityBeingUsedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityBeingUsedException(String message) {
		super(message);
	}

}
