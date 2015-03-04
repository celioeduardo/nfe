package com.hadrion.nfe.dominio.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebMvcSecurity
public class SecurityConfiguration {//extends WebSecurityConfigurerAdapter {
	
////	@Override
////	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////		//auth.userDetailsService(userDetailsService);
////		
////	}
//	
//	@Autowired
//	@Qualifier("authenticationProvider")
//	AuthenticationProvider authenticationProvider;
//	
//	@Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		//auth.authenticationProvider(authenticationProvider);
//    }
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.securityContext().disable();
////			.authorizeRequests()
////				.antMatchers("/**").permitAll()
////				.anyRequest().permitAll();
//		
////		http
////			.formLogin()
////			.authenticationDetailsSource(new HospedeIdAuthenticationDetailsSource())
////			.and()
////				.authorizeRequests()
////                .antMatchers("/**").authenticated();
//	}

}
