package com.hadrion.nfe.dominio.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ws.soap.SoapMessageFactory;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.hadrion.nfe.port.adapters.ws.WebServiceTemplateFabrica;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.hadrion")
public class Application extends SpringBootServletInitializer{
		
	@Bean
	DataSource dataSource() {
		return new SimpleDriverDataSource() {
			{
				setDriverClass(org.h2.Driver.class);
				setUsername("sa");
				setUrl("jdbc:h2:mem");
				setPassword("");
			}
		};
	}
	
	@Bean
	WebServiceTemplateFabrica webServiceTemplateFabrica(){
		return new WebServiceTemplateFabrica();
	}
	
	@Bean
	SoapMessageFactory messageFactory(){
		SoapMessageFactory factory = new SaajSoapMessageFactory();
		factory.setSoapVersion(SoapVersion.SOAP_12);
		return factory;
	}

	public static void main(String[] args) {
		
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

        
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}	
}