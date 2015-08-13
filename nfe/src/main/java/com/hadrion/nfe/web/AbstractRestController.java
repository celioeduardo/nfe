package com.hadrion.nfe.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AbstractRestController {

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Recurso não encontrado")
    public class RecursoNaoEncontradoException extends RuntimeException {
		private static final long serialVersionUID = 1L;
    }
	
}
