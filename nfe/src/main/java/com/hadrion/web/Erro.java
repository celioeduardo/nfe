package com.hadrion.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Erro implements ErrorController{
	private static final String ERROR_PATH = "/error";

    @RequestMapping(value=ERROR_PATH)
    public void handleError(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	
    	resp.sendRedirect("/req");
    }
	@Override
	public String getErrorPath() {
		return ERROR_PATH;	
	}

	
}
