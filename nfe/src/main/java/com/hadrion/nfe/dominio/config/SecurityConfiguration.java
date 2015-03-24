package com.hadrion.nfe.dominio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
////	@Override
////	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////		//auth.userDetailsService(userDetailsService);
////		
////	}
//	
	@Autowired
	@Qualifier("authenticationProvider")
	AuthenticationProvider authenticationProvider;
	
	@Value("${autenticacao.modo:}")
	private String modoAutenticacao;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		if ("desabilitada".equals(modoAutenticacao))
			return;
		else if ("memoria".equals(modoAutenticacao))
			auth
	        .inMemoryAuthentication()
	            .withUser("user").password("123").roles("USER").and()
				.withUser("hadrion").password("123").roles("USER").and()
				.withUser("admin").password("123").roles("USER").and()
				.withUser("martinfowler").password("123").roles("USER");
		else
			auth.authenticationProvider(authenticationProvider);
    }
	
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
        	.antMatchers("/resources/**","/static/**","/webjars/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if ("desabilitada".equals(modoAutenticacao))
			http
			.securityContext()
				.disable()
			.csrf()
				.disable();
		else {
			http
				.csrf().disable()
				.authorizeRequests()
					.anyRequest().authenticated()
					.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
					.authenticationDetailsSource(new HospedeIdAuthenticationDetailsSource())
					.and()
				.logout()                                    
	                .permitAll()
	                .and()
				.httpBasic()
					.authenticationDetailsSource(new HospedeIdAuthenticationDetailsSource());
		} 
//		http
//			.authorizeRequests()
//				.antMatchers("/**").authenticated()
//				.and()
//			.formLogin()
//				.authenticationDetailsSource(new HospedeIdAuthenticationDetailsSource())
//				//.loginPage("/login")
//				.permitAll();
		
//		http
//			.securityContext()
//				.disable()
//			.csrf()
//				.disable();
		
	}
	
	

}
