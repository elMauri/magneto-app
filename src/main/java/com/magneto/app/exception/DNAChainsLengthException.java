package com.magneto.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DNAChainsLengthException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DNAChainsLengthException(String message)  {
		super(message);
	}

}
