package com.hadrion.nfe.dominio.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
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
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessageFactory;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

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
	SoapMessageFactory messageFactory(){
		SoapMessageFactory factory = new SaajSoapMessageFactory();
		factory.setSoapVersion(SoapVersion.SOAP_12);
		return factory;
	}
	
	@Bean
	WebServiceTemplate webServiceTemplate(){
		WebServiceTemplate template = new WebServiceTemplate(messageFactory());
		template.setMessageSender(webServiceMessageSender());
		return template;
	}
	
	@Bean
	WebServiceMessageSender webServiceMessageSender(){
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender(httpClient());
		return messageSender;
	}
	
	@Bean
	HttpClient httpClient(){
		SSLContext sslcontext;
		try {
			sslcontext = SSLContexts.custom()
					.loadTrustMaterial(null, new TrustStrategy() {
						@Override
						public boolean isTrusted(X509Certificate[] chain, String authType)
								throws CertificateException {
							return true;
						}
					})
			        .loadKeyMaterial(pkcs12(),"12345678".toCharArray())
			        .build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        
        return HttpClients.custom()
        		.addInterceptorFirst(new ContentLengthHeaderRemover())
                .setSSLSocketFactory(sslsf)
                .build();
	}
	
	private static class ContentLengthHeaderRemover implements HttpRequestInterceptor{

        @Override
        public void process(HttpRequest request, HttpContext context) 
                throws HttpException, IOException {

            // fighting org.apache.http.protocol.RequestContent's 
            // ProtocolException("Content-Length header already present");
            request.removeHeaders(HTTP.CONTENT_LEN);
        }
    }
	
	protected KeyStore pkcs12() throws Exception{
		KeyStore trustStore = KeyStore.getInstance("PKCS12");
		File file = FileUtils.getFile("src","test","resources","assinatura","certificado.pfx");
        FileInputStream instream = new FileInputStream(file);
        try {
            trustStore.load(instream, "12345678".toCharArray());
        } finally {
            instream.close();
        }
        return trustStore;
	}

	public static void main(String[] args) {
		
//		String idTimeZone = "America/Sao_Paulo";
//		TimeZone tz = TimeZone.getTimeZone(idTimeZone);  
//		TimeZone.setDefault(tz);
		
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