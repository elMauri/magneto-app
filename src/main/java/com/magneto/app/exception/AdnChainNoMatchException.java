package com.magneto.app.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AdnChainNoMatchException  extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AdnChainNoMatchException(String message) {
		super(message);
	}
}
