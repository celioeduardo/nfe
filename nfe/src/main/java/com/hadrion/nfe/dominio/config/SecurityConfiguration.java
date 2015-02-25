package com.hadrion.nfe.dominio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		//auth.userDetailsService(userDetailsService);
//		
//	}
	
	@Autowired
	@Qualifier("authenticationProvider")
	AuthenticationProvider authenticationProvider;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//            .inMemoryAuthentication()
//                .withUser("user").password("1234").roles("USER");
		auth.authenticationProvider(authenticationProvider);
		
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
			.authenticationDetailsSource(new HospedeIdAuthenticationDetailsSource())
			.and()
				.authorizeRequests()
                .antMatchers("/**").authenticated();
//		http
//			.authorizeRequests()
//				.antMatchers("/filial/alterar_modo_operacao").fullyAuthenticated()
//				.anyRequest().permitAll();
//		http.httpBasic();
//		http.csrf().disable();
	}

}
