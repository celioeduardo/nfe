package com.hadrion.nfe.dominio.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.hadrion.nfe.aplicacao.AutenticacaoAplicacaoService;

@Component("authenticationProvider")
public class IdHadrionAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private AutenticacaoAplicacaoService autenticacaoAplicacaoService;
	
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		
		String username = auth.getName();
        String senha = auth.getCredentials().toString();
        String hospedeId = null;
        
        if (auth.getDetails() instanceof HospedeIdAuthenticationDetails){
        	HospedeIdAuthenticationDetails details = 
        			(HospedeIdAuthenticationDetails) auth.getDetails();
        	hospedeId = details.getHospedeId(); 
        }
        
        if (autenticar(hospedeId,username,senha)) {
        	List<GrantedAuthority> grantedAuths = new ArrayList<>();
        	return new HospedeIdUsernamePasswordAuthenticationToken(
        			hospedeId, username, senha, grantedAuths);
        } else {
        	throw new AuthenticationCredentialsNotFoundException("Unable to auth against third party systems");
        }
	}

	private boolean autenticar(String hospedeId, String username, String senha) {
		return autenticacaoAplicacaoService.autenticar(hospedeId, username, senha);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		 return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}

}
