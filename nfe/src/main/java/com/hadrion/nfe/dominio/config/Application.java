package com.hadrion.nfe.dominio.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.hadrion")
public class Application {
	
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

	public static void main(String[] args) {
		
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        
	}
}