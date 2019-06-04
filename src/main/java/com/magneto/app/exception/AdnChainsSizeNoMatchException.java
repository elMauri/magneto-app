package com.magneto.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AdnChainsSizeNoMatchException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AdnChainsSizeNoMatchException(String message)  {
		super(message);
	}

}
