package com.hadrion.nfe.dominio.config;

import javax.sql.DataSource;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.ws.soap.SoapMessageFactory;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.hadrion.nfe.port.adapters.ws.WebServiceTemplateFabrica;

@Configuration
@EnableTransactionManagement
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.hadrion")
@EnableJpaRepositories(basePackages = "com.hadrion")
@EntityScan(basePackages = "com.hadrion")
public abstract class Application extends WebMvcConfigurerAdapter {

	@Autowired
	private ConnectionFactory rabbitConnectionFactory;
	
	@Autowired
	private Environment env;
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.local")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.agrix")
	public DataSource agrixDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "agrix")
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(agrixDataSource());
	}

	@Bean
	public RabbitAdmin rabbitAdmin(){
		return new RabbitAdmin(rabbitConnectionFactory);
	}
	
	@Bean
	public FanoutExchange exchange() {
		return new FanoutExchange("hadrionNfe");
	}

	@Bean
	public RabbitTemplate amqTemplate() {
		RabbitTemplate r = new RabbitTemplate(rabbitConnectionFactory);
		r.setExchange("hadrionNfe");
		r.setConnectionFactory(rabbitConnectionFactory);
		return r;
	}

	@Bean
	ObjectMapper jacksonObjectMapper() {
		return new CustomJacksonObjectMapper();
	}

	@Bean
	SerializationConfig serializationConfig() {
		return jacksonObjectMapper().getSerializationConfig();
	}

	@Bean
	WebServiceTemplateFabrica webServiceTemplateFabrica() {
		return new WebServiceTemplateFabrica();
	}

	@Bean
	SoapMessageFactory messageFactory() {
		SoapMessageFactory factory = new SaajSoapMessageFactory();
		factory.setSoapVersion(SoapVersion.SOAP_12);
		return factory;
	}
	
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
		
//		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		

//		String[] beanNames = ctx.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			System.out.println(beanName);
//		}
		
//		System.out.println("PROFILES DEFAULT:" + Arrays.toString(ctx.getEnvironment().getDefaultProfiles()));
//		System.out.println("PROFILES ATIVOS:" + Arrays.toString(ctx.getEnvironment().getActiveProfiles()));

	}
}