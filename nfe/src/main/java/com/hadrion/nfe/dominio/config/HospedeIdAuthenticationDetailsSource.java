package com.hadrion.nfe.dominio.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class HospedeIdAuthenticationDetailsSource implements
		AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

	@Override
	public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
		return new HospedeIdAuthenticationDetails(context);
	}

}
