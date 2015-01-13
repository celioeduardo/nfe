package com.hadrion.nfe.dominio.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for email support.
 *
 * @author Oliver Gierke
 * @author Stephane Nicoll
 * @since 1.2.0
 */
//@ConfigurationProperties(prefix = "spring.mail")
@ConfigurationProperties(locations = "classpath:mail.properties", ignoreUnknownFields = true, prefix = "mail")
public class MailProperties {
	/**
	 * SMTP server host.
	 */
	private String host="smtp.gmail.com";

	/**
	 * SMTP server port.
	 */
	private Integer port=587;

	/**
	 * Login user of the SMTP server.
	 */
	private String username="teste@hadrion.com.br";

	/**
	 * Login password of the SMTP server.
	 */
	private String password="testeteste";

	/**
	 * Default MimeMessage encoding.
	 */
	private String defaultEncoding = "UTF-8";

	/**
	 * Additional JavaMail session properties.
	 */
	private Map<String, String> properties = new HashMap<String, String>();

	public MailProperties(){
		properties.put("mail.debug", "true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.ssl.trust","smtp.gmail.com");		
	}
	
	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDefaultEncoding() {
		return this.defaultEncoding;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public Map<String, String> getProperties() {
		return this.properties;
	}

}
