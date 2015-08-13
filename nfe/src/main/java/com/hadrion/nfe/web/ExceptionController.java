package com.hadrion.nfe.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Erro manipularTodasExceptions(HttpServletRequest request, 
			HttpServletResponse response, HttpSession session, Exception ex){
		ex.printStackTrace();
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return new Erro(ex);
	}
}
