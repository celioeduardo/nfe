package com.hadrion.nfe.dominio.config;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

class HospedeIdUsernamePasswordAuthenticationToken extends
		UsernamePasswordAuthenticationToken {

	private String hospedeId;
	
	public HospedeIdUsernamePasswordAuthenticationToken(String hospedeId, Object principal, 
			Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.hospedeId = hospedeId;
	}

	public String getHospedeId() {
		return hospedeId;
	}

	private static final long serialVersionUID = 1L;
}
