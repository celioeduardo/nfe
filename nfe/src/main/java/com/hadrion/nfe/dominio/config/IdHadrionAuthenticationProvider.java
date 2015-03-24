package com.hadrion.nfe.dominio.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hadrion.comum.Afirmacao;
import com.hadrion.nfe.aplicacao.AutenticacaoAplicacaoService;

@Component("authenticationProvider")
public class IdHadrionAuthenticationProvider extends Afirmacao 
	implements AuthenticationProvider {
	
	@Autowired
	private AutenticacaoAplicacaoService autenticacaoAplicacaoService;
	
	@Value("${hospede:}")
	private String hospedeIdConfigurado;
	
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
        	
        	if (StringUtils.isEmpty(hospedeId))
        		hospedeId = hospedeIdConfigurado;
        	
        	assertArgumentoNaoVazio(hospedeId, "HospedeId é obrigatório.");
        		
        }
        
        if (autenticar(hospedeId,username,senha)) {
        	List<GrantedAuthority> grantedAuths = new ArrayList<>();
        	return new HospedeIdUsernamePasswordAuthenticationToken(
        			hospedeId, username, senha, grantedAuths);
        } else {
        	throw new AuthenticationCredentialsNotFoundException("Não foi possível autenticar em outro sistema.");
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
